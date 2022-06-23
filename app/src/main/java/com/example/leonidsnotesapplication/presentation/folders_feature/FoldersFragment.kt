package com.example.leonidsnotesapplication.presentation.folders_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FragmentFoldersBinding
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.folders_feature.util.FoldersAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoldersFragment : Fragment(), FoldersAdapter.FolderClickListener {

    private var _binding : FragmentFoldersBinding? = null

    private val binding get() = _binding!!

    private val vm : FoldersViewModel by viewModels()

    private val adapter : FoldersAdapter by lazy { FoldersAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoldersBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){

        super.onViewCreated(view, savedInstanceState)


        binding.addFolderButton.setOnClickListener {
            FolderAddDialog().show(childFragmentManager, null)
//            vm.addFolder(Folder("folder 1"))
//            findNavController().navigate(R.id.action_foldersFragment_to_folderAddDialog)
        }

        binding.foldersRecyclerView.adapter = adapter
        binding.foldersRecyclerView.isNestedScrollingEnabled = false
        binding.foldersRecyclerView.layoutManager = LinearLayoutManager(view.context)
        vm.foldersLiveData.observe(viewLifecycleOwner){
            adapter.setData(it)
        }
    }

    override fun onClickedFolder(folder : Folder) {
        val action = FoldersFragmentDirections.actionFoldersFragmentToNotesFragment(folder)
        findNavController().navigate(action)
    }
}