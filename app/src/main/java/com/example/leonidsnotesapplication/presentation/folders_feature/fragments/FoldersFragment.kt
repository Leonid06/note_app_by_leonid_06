package com.example.leonidsnotesapplication.presentation.folders_feature.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FragmentFoldersBinding
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.folders_feature.FoldersAdapter
import com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels.FoldersViewModel
import com.example.leonidsnotesapplication.presentation.folders_feature.callbacks.SwipeCallback
import com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels.FolderSharedViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import com.example.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoldersFragment : Fragment(),
    FoldersAdapter.FolderClickListener,
    SearchView.OnQueryTextListener,
    PopupMenu.OnMenuItemClickListener {

    private var _binding : FragmentFoldersBinding? = null
    private val binding  get() = _binding!!

    private val vm : FoldersViewModel by viewModels()

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

        binding.ibSort.setOnClickListener {
            showSortMenu()
        }

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

    private fun showSortMenu() {
        val menu = PopupMenu(context!!, binding.ibSort)
        val inflater = menu.menuInflater
        inflater.inflate(R.menu.sort_menu,  menu.menu)
        menu.setOnMenuItemClickListener(this as PopupMenu.OnMenuItemClickListener)
        menu.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        vm.searchAllFolders("%$query%", folderSharedViewModel.option.value!!)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        vm.searchAllFolders("%$query%", folderSharedViewModel.option.value!!)
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.byTitle -> {
                folderSharedViewModel.selectOption(SortOption.ByTitle)
                onQueryTextChange(binding.folderSearchView.query.toString())
                true
            }
            R.id.byDate -> {
                folderSharedViewModel.selectOption(SortOption.ByDate)
                onQueryTextChange(binding.folderSearchView.query.toString())
                true
            }
            else -> false
        }
    }

}