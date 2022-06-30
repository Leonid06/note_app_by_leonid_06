package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.databinding.FragmentHomeBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.extensions.getDate
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(),
NoteCardAdapter.NoteTouchListener,
SearchView.OnQueryTextListener{

    private var _binding : FragmentHomeBinding? = null
    val binding get() = _binding!!

    private val adapter by lazy {
        NoteCardAdapter(this as NoteCardAdapter.NoteTouchListener)
    }

    private val vm : HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vm.updateNotes()
        binding.vm = vm
        binding.adapter = adapter

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            homeRecyclerView.isNestedScrollingEnabled = false
            homeRecyclerView.layoutManager = LinearLayoutManager(view.context)
            homeRecyclerView.itemAnimator= NotesItemAnimator()
        }
        binding.homeSearchView.isSubmitButtonEnabled  = true
        binding.homeSearchView.setOnQueryTextListener(this as SearchView.OnQueryTextListener)

        binding.addNoteButton.setOnClickListener{
            val action = HomeFragmentDirections.actionHomeFragmentToSingleNoteFragment(Note(
                "","","",false, activity?.getDate(), folderId = -1
            ), true)

            findNavController().navigate(action)
        }

        binding.foldersButton.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFoldersFragment())
        }
    }

    override fun onNoteSwipedLeft(note: Note): Boolean {
        return false
    }

    override fun onNoteClicked(note: Note) {
        val action = HomeFragmentDirections.actionHomeFragmentToSingleNoteFragment(note, true)
        findNavController().navigate(action)
    }

    override fun onDeleteButtonClick(note: Note) {
        val action = HomeFragmentDirections.actionHomeFragmentToDeleteDialogFragment(note)
        findNavController().navigate(action)
    }

    override fun onStarCheckBoxClick(note: Note) {
        vm.addNote(note)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        vm.searchNotes(query!!)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        vm.searchNotes(query!!)
        return true
    }
}