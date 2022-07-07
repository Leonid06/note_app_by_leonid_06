package com.example.leonidsnotesapplication.presentation.folders_feature.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.databinding.FragmentFoldersBinding
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.folders_feature.FoldersAdapter
import com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels.FoldersViewModel
import com.example.leonidsnotesapplication.presentation.folders_feature.callbacks.SwipeCallback
import com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels.FolderSharedViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoldersFragment : Fragment(), FoldersAdapter.FolderClickListener {

    private var _binding : FragmentFoldersBinding? = null
    private val binding  get() = _binding!!

    private val vm : FoldersViewModel by activityViewModels()

    private val folderSharedViewModel : FolderSharedViewModel by activityViewModels()

    private val adapter by lazy { FoldersAdapter(this as FoldersAdapter.FolderClickListener)  }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoldersBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
        }
        binding.adapter = adapter

        binding.addFolderButton.setOnClickListener {
            findNavController().navigate(FoldersFragmentDirections.actionFoldersFragmentToFolderAddDialog())
        }

        binding.homeButton.setOnClickListener {
            findNavController().navigate(FoldersFragmentDirections.actionFoldersFragmentToHomeFragment())
        }

        binding.foldersRecyclerView.itemAnimator = NotesItemAnimator()
        binding.foldersRecyclerView.isNestedScrollingEnabled = false
        binding.foldersRecyclerView.layoutManager = LinearLayoutManager(view.context)

    }

    override fun onClickedFolder(folder : Folder) {
        folderSharedViewModel.selectFolder(folder)
        val action = FoldersFragmentDirections.actionFoldersFragmentToNotesFragment()
        findNavController().navigate(action)
    }

    override fun onTitleTextChanged(title: String, folder: Folder) {
        vm.updateFolderTitle(folder, title)
    }

}