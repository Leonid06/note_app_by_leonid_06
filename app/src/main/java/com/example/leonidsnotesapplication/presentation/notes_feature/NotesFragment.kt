package com.example.leonidsnotesapplication.presentation.notes_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.util.DeleteDialogFragment
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NoteCardAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesFragment : Fragment()  ,
    NoteCardAdapter.NoteClickListener ,
    DeleteDialogFragment.OnNegativeButtonClickListener,
    SearchView.OnQueryTextListener{

    private val adapter : NoteCardAdapter by lazy { NoteCardAdapter(this) }
    private val vm : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.notesRecyclerView)
        val addNoteButton = view.findViewById<FloatingActionButton>(R.id.go_to_add_note_fragment_button)
        val notesSearchView = view.findViewById<SearchView>(R.id.notesSearchView)


        vm.notesLiveData.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        vm.updateNotes()

        addNoteButton.setOnClickListener{
            findNavController().navigate(R.id.action_notesFragment_to_singleNoteFragment)
        }

        recyclerView.adapter = adapter
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.itemAnimator = NotesItemAnimator()

        notesSearchView.isSubmitButtonEnabled  = true
        notesSearchView.setOnQueryTextListener(this)

    }

    override fun onClickedNote(note: Note) {
        val  bundle = Bundle()

        bundle.putParcelable("note" , note)
        Navigation.findNavController(requireView()).navigate(
            R.id.action_notesFragment_to_singleNoteFragment ,
            bundle)
    }

    override fun onDeleteButtonClick(note : Note) {
        DeleteDialogFragment(note, this).show(
            childFragmentManager,
            DeleteDialogFragment.TAG)
    }

    override fun onClick(note: Note) {
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
}