package com.example.leonidsnotesapplication.presentation.notes_feature.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leonidsnotesapplication.databinding.FragmentHomeBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.extensions.getDate
import com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels.FolderSharedViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesItemAnimator
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.HomeViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel
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

    private val folderSharedViewModel : FolderSharedViewModel by activityViewModels()

    private val noteSharedViewModel : NoteSharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        folderSharedViewModel.toggleDefaultMode(true)
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
            val note = Note(
                "","","",false, activity?.getDate(), folderId = -1
            )
            noteSharedViewModel.selectNote(note)
            val action = HomeFragmentDirections.actionHomeFragmentToSingleNoteFragment(true)

            findNavController().navigate(action)
        }

        binding.foldersButton.setOnClickListener{
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFoldersFragment())
        }
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        vm.searchNotes(query!!)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        vm.searchNotes(query!!)
        return true
    }

    override fun onNoteSwipedLeft(note: Note): Boolean {
        return false
    }

    override fun onNoteClicked(note : Note) {
        noteSharedViewModel.selectNote(note)
        val action = HomeFragmentDirections.actionHomeFragmentToSingleNoteFragment(false)
        findNavController().navigate(action)
    }

    override fun onDeleteButtonClick(note : Note) {
        noteSharedViewModel.selectDeleteNote(note)
        val action = HomeFragmentDirections.actionHomeFragmentToHomeDeleteDialogFragment()
        findNavController().navigate(action)
    }

    override fun onStarCheckBoxClick(note: Note) {
        vm.updateNoteChecked(note)
    }

}