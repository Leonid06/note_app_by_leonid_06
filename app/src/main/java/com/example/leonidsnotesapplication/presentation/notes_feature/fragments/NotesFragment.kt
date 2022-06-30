package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FragmentNotesBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.extensions.getDate
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NotesViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class NotesFragment : Fragment()  ,
    NoteCardAdapter.NoteTouchListener,
    SearchView.OnQueryTextListener{

    private var _binding : FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val adapter : NoteCardAdapter by lazy {
        NoteCardAdapter(this as NoteCardAdapter.NoteTouchListener)
    }

    private val vm : NotesViewModel by activityViewModels()

    private val args : NotesFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.updateCurrentFolder(args.folder)

        binding.tvFolderTitle.doOnTextChanged { text, _, _, _ ->
            vm.updateFolderTitle(text.toString())
        }
        binding.goToAddNoteFragmentButton.setOnClickListener{
            val note = Note("","","",false, activity?.getDate(), folderId = vm.currentFolderLiveData.value!!.id)
            val action = NotesFragmentDirections.actionNotesFragmentToSingleNoteFragment(note,
                isNew = true,
                isDefaultFolder = false
            )
            findNavController().navigate(action)
        }

        binding.goToFoldersFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_foldersFragment)
        }

        binding.apply {
            viewModel = vm
            lifecycleOwner = viewLifecycleOwner
            notesRecyclerView.isNestedScrollingEnabled = false
            notesRecyclerView.layoutManager = LinearLayoutManager(view.context)
            notesRecyclerView.itemAnimator= NotesItemAnimator()
        }

        binding.adapter = adapter

        binding.notesSearchView.isSubmitButtonEnabled  = true
        binding.notesSearchView.setOnQueryTextListener(this as SearchView.OnQueryTextListener)



    }

    override fun onNoteClicked(note: Note) {
        val  action = NotesFragmentDirections.actionNotesFragmentToSingleNoteFragment(note, false, false)
        findNavController().navigate(action)
    }

    override fun onDeleteButtonClick(note : Note) {
        val action = NotesFragmentDirections.actionNotesFragmentToNoteDeleteDialogFragment(note)
        findNavController().navigate(action)
    }

    override fun onStarCheckBoxClick(note: Note) {
       vm.addNote(note, false)
    }

    private fun searchDatabase(query: String?){
        vm.searchNotes("%$query%")
        vm.notesSearchLiveData.observe(this){
            adapter.setData(it)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchDatabase(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        searchDatabase(query)
        return true
    }

    override fun onNoteSwipedLeft(note: Note): Boolean {
//        vm.deleteNote(note)
//        Toast.makeText(context, "Note swiped left", Toast.LENGTH_LONG)
          return true
    }
}