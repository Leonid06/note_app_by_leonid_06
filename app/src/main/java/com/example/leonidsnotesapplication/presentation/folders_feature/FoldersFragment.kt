package com.example.leonidsnotesapplication.presentation.folders_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FragmentFoldersBinding
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.folders_feature.callbacks.SwipeCallback
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoldersFragment : Fragment(), FoldersAdapter.FolderClickListener {

    private lateinit var binding : FragmentFoldersBinding

    private val vm : FoldersViewModel by activityViewModels()

    private val adapter by lazy {FoldersAdapter(this as FoldersAdapter.FolderClickListener)  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFoldersBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        super.onViewCreated(view, savedInstanceState)

        vm.updateAllFolders()


        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
        }
        binding.adapter = adapter

        binding.addFolderButton.setOnClickListener {
            FolderAddDialog().show(childFragmentManager, null)
        }

        binding.foldersRecyclerView.itemAnimator = NotesItemAnimator()
        binding.foldersRecyclerView.isNestedScrollingEnabled = false
        binding.foldersRecyclerView.layoutManager = LinearLayoutManager(view.context)

    }

    override fun onClickedFolder(folder : Folder) {
        val action = FoldersFragmentDirections.actionFoldersFragmentToNotesFragment(folder)
        findNavController().navigate(action)
    }

    override fun onTitleTextChanged(title: String, folder: Folder) {
        vm.updateFolderTitle(folder, title)
    }

    override fun setUpOnItemSwiped(swipe: SwipeCallback) {
        val itemTouchHelper = ItemTouchHelper(swipe)
        itemTouchHelper.attachToRecyclerView(binding.foldersRecyclerView)
    }

    override fun onDeleteSwiped(folder: Folder) {
        vm.deleteFolder(folder)
    }
}