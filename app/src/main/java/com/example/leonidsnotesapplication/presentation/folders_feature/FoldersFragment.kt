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
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.folders_feature.util.FoldersAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class FoldersFragment : Fragment(), FoldersAdapter.FolderClickListener {

    private val vm : FoldersViewModel by viewModels()
    private val adapter : FoldersAdapter by lazy { FoldersAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_folders, container, false)

        val foldersRecyclerView = view.findViewById<RecyclerView>(R.id.foldersRecyclerView)
        val addFolderButton = view.findViewById<FloatingActionButton>(R.id.add_folder_button)


        vm.updateAllFolders()

        addFolderButton.setOnClickListener {
            findNavController().navigate(R.id.action_foldersFragment_to_createFolderFragment)
        }

        foldersRecyclerView.adapter = adapter
        foldersRecyclerView.isNestedScrollingEnabled = false
        foldersRecyclerView.layoutManager = LinearLayoutManager(view.context)
        foldersRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        vm.foldersLiveData.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        return view
    }

    override fun onClickedFolder(folder : Folder) {
        val bundle = Bundle()
        bundle.putParcelable("folder", folder)

        findNavController().navigate(R.id.action_foldersFragment_to_createFolderFragment, bundle)
    }
}