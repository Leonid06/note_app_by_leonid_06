package com.example.leonidsnotesapplication.presentation.notes_feature.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FragmentHomeBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.extensions.getDate
import com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels.FolderSharedViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import com.example.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.HomeViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),
NoteCardAdapter.NoteTouchListener,PopupMenu.OnMenuItemClickListener,
SearchView.OnQueryTextListener{

    private var _binding : FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val adapter by lazy {
        NoteCardAdapter(this as NoteCardAdapter.NoteTouchListener)
    }

    private val vm : HomeViewModel by viewModels()

    private val noteSharedViewModel : NoteSharedViewModel by activityViewModels()

    private val currentOption by lazy { noteSharedViewModel.sortOption }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.adapter = adapter
        binding.viewModel = vm

        vm.sortNotes(currentOption.value!!)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            homeRecyclerView.isNestedScrollingEnabled = false
            homeRecyclerView.layoutManager = LinearLayoutManager(view.context)
            homeRecyclerView.itemAnimator= NotesItemAnimator()
        }
        binding.homeSearchView.isSubmitButtonEnabled  = true
        binding.homeSearchView.setOnQueryTextListener(this as SearchView.OnQueryTextListener)

        binding.addNoteButton.setOnClickListener{
            val note = Note(
                "","","",false, activity?.getDate(), folderId = -1
            )
            noteSharedViewModel.selectNote(note)
            val action = HomeFragmentDirections.actionHomeFragmentToSingleNoteFragment(
                isNew = true
            )

            findNavController().navigate(action)
        }

        binding.ibSort.setOnClickListener{
            showSortMenu()
        }

        binding.foldersButton.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFoldersFragment())
        }
    }

    private fun showSortMenu() {
        val menu = PopupMenu(context!!, binding.ibSort)
        val inflater = menu.menuInflater
        inflater.inflate(R.menu.sort_menu,  menu.menu)
        menu.setOnMenuItemClickListener(this as PopupMenu.OnMenuItemClickListener)
        menu.show()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
            vm.searchNotes("%$query%", currentOption.value!!)


        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
            vm.searchNotes("%$query%", currentOption.value!!)
        return true
    }

    override fun onNoteClicked(note : Note) {
        noteSharedViewModel.selectNote(note)
        val action = HomeFragmentDirections.actionHomeFragmentToSingleNoteFragment(false)
        findNavController().navigate(action)
    }

    override fun onDeleteButtonClick(note : Note) { 
        noteSharedViewModel.selectDeleteNote(note)
        val action = HomeFragmentDirections.actionHomeFragmentToNoteDeleteDialogFragment()
        findNavController().navigate(action)
    }

    override fun onStarCheckBoxClick(note: Note) {
        binding.homeRecyclerView.smoothScrollToPosition(0)
        vm.updateNoteChecked(note)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.byDate -> {
                noteSharedViewModel.selectSortOption(SortOption.ByDate)
                onQueryTextChange(binding.homeSearchView.query.toString())
                true
            }
            R.id.byTitle -> {
                noteSharedViewModel.selectSortOption(SortOption.ByTitle)
                onQueryTextChange(binding.homeSearchView.query.toString())
                true
            }
            else -> false
        }
    }

}