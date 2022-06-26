package com.example.leonidsnotesapplication.presentation.notes_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FragmentNotesBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.single_note_feature.SingleNoteFragment
import dagger.hilt.android.AndroidEntryPoint



@AndroidEntryPoint
class NotesFragment : Fragment()  ,
    NoteCardAdapter.NoteTouchListener,
    DeleteDialogFragment.OnNegativeButtonClickListener,
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

        vm.setFolder(args.folder)

        binding.goToAddNoteFragmentButton.setOnClickListener{
            val note = Note("","","",false, SingleNoteFragment.getDate(), folderId = vm.currentFolderLiveData.value!!.id)
            val action = NotesFragmentDirections.actionNotesFragmentToSingleNoteFragment(note)
            findNavController().navigate(action)
        }

        binding.goToFoldersFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_foldersFragment)
        }

        vm.notesLiveData.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        binding.tvFolderTitle.text = args.folder.title

        binding.notesRecyclerView.adapter = adapter
        binding.notesRecyclerView.isNestedScrollingEnabled = false
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.notesRecyclerView.itemAnimator= null

        binding.notesSearchView.isSubmitButtonEnabled  = true
        binding.notesSearchView.setOnQueryTextListener(this as SearchView.OnQueryTextListener)

    }

    override fun onNoteClicked(note: Note) {
        val  action = NotesFragmentDirections.actionNotesFragmentToSingleNoteFragment(note)
        findNavController().navigate(action)
    }

    override fun onDeleteButtonClick(note : Note) {
        DeleteDialogFragment(note, this as DeleteDialogFragment.OnNegativeButtonClickListener).show(
            childFragmentManager,
            DeleteDialogFragment.TAG)
    }

    override fun onStarCheckBoxClick(note: Note) {
       vm.addNote(note)
    }

    override fun onDeleteOptionClicked(note: Note) {
        vm.deleteNote(note)
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
        Toast.makeText(context, "Note swiped left", Toast.LENGTH_LONG)
        return true
    }
}