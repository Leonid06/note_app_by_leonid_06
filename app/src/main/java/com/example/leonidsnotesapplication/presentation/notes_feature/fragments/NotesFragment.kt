package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FragmentNotesBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.extensions.getDate
import com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels.FolderSharedViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NotesViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import com.example.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class NotesFragment : Fragment()  ,
    NoteCardAdapter.NoteTouchListener,
    SearchView.OnQueryTextListener,PopupMenu.OnMenuItemClickListener
{

    private var _binding : FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val adapter : NoteCardAdapter by lazy {
        NoteCardAdapter(this as NoteCardAdapter.NoteTouchListener)
    }

    private val vm : NotesViewModel by viewModels()

    private val sharedNoteViewModel: NoteSharedViewModel by activityViewModels()

    private val sharedFolderViewModel :FolderSharedViewModel by activityViewModels()

    private val sortOption by lazy { sharedNoteViewModel.sortOption }


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        vm.updateNotesByFolder(sortOption.value!!,sharedFolderViewModel.selectedFolder.value!!)

        binding.tvFolderTitle.doOnTextChanged { text, _, _, _ ->
            vm.updateFolderTitle(text.toString(), sharedFolderViewModel.selectedFolder.value!!)
        }

        binding.ibDeleteFolder.setOnClickListener {
            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToDeleteFolderDialogFragment())
        }

        binding.goToAddNoteFragmentButton.setOnClickListener{
            val note = Note("","","",false, activity?.getDate(),
                folderId = sharedFolderViewModel.selectedFolder.value!!.id)

            sharedNoteViewModel.selectNote(note)

            findNavController().navigate(NotesFragmentDirections.actionNotesFragmentToSingleNoteFragment(true))
        }

        binding.goToFoldersFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_foldersFragment)
        }

        binding.ibSort.setOnClickListener{
            showSortMenu()
        }

        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
            notesRecyclerView.isNestedScrollingEnabled = false
            notesRecyclerView.layoutManager = LinearLayoutManager(view.context)
            notesRecyclerView.itemAnimator= NotesItemAnimator()
        }

        binding.sharedFolderViewModel = sharedFolderViewModel

        binding.adapter = adapter

        binding.notesSearchView.isSubmitButtonEnabled  = true
        binding.notesSearchView.setOnQueryTextListener(this as SearchView.OnQueryTextListener)



    }

    override fun onNoteClicked(note: Note) {
        sharedNoteViewModel.selectNote(note)
        val  action = NotesFragmentDirections.actionNotesFragmentToSingleNoteFragment(false)
        findNavController().navigate(action)
    }

    override fun onDeleteButtonClick(note : Note) {
        sharedNoteViewModel.selectDeleteNote(note)
        val action = NotesFragmentDirections.actionNotesFragmentToNoteDeleteDialogFragment()
        findNavController().navigate(action)
    }

    override fun onStarCheckBoxClick(note: Note) {
       binding.notesRecyclerView.smoothScrollToPosition(0)
       vm.addNote(note, false)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        sharedFolderViewModel.selectedFolder.value?.let { vm.searchNotes("%$query%", it, sortOption.value!!) }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        sharedFolderViewModel.selectedFolder.value?.let { vm.searchNotes("%$query%", it, sortOption.value!!) }
        return true
    }

    private fun showSortMenu() {
        val menu = PopupMenu(context!!, binding.ibSort)
        val inflater = menu.menuInflater
        inflater.inflate(R.menu.sort_menu,  menu.menu)
        menu.setOnMenuItemClickListener(this as PopupMenu.OnMenuItemClickListener)
        menu.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.byTitle -> {
                sharedNoteViewModel.selectSortOption(SortOption.ByTitle)
                onQueryTextChange(binding.notesSearchView.query.toString())
                true
            }
            R.id.byDate -> {
                sharedNoteViewModel.selectSortOption(SortOption.ByDate)
                onQueryTextChange(binding.notesSearchView.query.toString())
                true
            }
            else -> false
        }
    }
}