package com.example.leonidsnotesapplication.presentation.notes_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FragmentNotesBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NoteCardAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import com.example.leonidsnotesapplication.presentation.single_note_feature.SingleNoteFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInRightAnimator


@AndroidEntryPoint
class NotesFragment : Fragment()  ,
    NoteCardAdapter.NoteClickListener ,
    DeleteDialogFragment.OnNegativeButtonClickListener,
    SearchView.OnQueryTextListener{

    private var _binding : FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private val adapter : NoteCardAdapter by lazy { NoteCardAdapter(this) }
    private val vm : NotesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.goToAddNoteFragmentButton.setOnClickListener{
            val note = Note("","","",false, SingleNoteFragment.getDate())
            val action = NotesFragmentDirections.actionNotesFragmentToSingleNoteFragment(note)
            Navigation.findNavController(requireView()).navigate(action)
        }

        binding.goToFoldersFragmentButton.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_foldersFragment)
        }

        vm.notesLiveData.observe(viewLifecycleOwner){
            adapter.setData(it)
        }

        binding.notesRecyclerView.adapter = adapter
        binding.notesRecyclerView.isNestedScrollingEnabled = false
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(view.context)

        binding.notesSearchView.isSubmitButtonEnabled  = true
        binding.notesSearchView.setOnQueryTextListener(this)

    }

    override fun onClickedNote(note: Note) {
        val  action = NotesFragmentDirections.actionNotesFragmentToSingleNoteFragment(note)
        Navigation.findNavController(requireView()).navigate(action)
    }

    override fun onDeleteButtonClick(note : Note) {
        DeleteDialogFragment(note, this).show(
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
}