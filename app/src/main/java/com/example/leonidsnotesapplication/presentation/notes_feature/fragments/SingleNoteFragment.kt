package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.leonidsnotesapplication.databinding.FragmentSingleNoteBinding
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.extensions.showKeyboard
import com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels.FolderSharedViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NotesViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.SingleNoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingleNoteFragment : Fragment() {

    private val vm : SingleNoteViewModel by viewModels()

    private val noteSharedViewModel : NoteSharedViewModel  by activityViewModels()

    private var _binding : FragmentSingleNoteBinding? = null

    private val binding  get()= _binding!!

    private val args by navArgs<SingleNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedNote = noteSharedViewModel.selectedNote.value!!

        activity?.showKeyboard(binding.etNoteContent)

        binding.etNoteContent.setText(selectedNote.content)
        binding.addNoteButton.setOnClickListener{

            val content = binding.etNoteContent.text.toString()
            val isStarred = selectedNote.isStarred
            val title : String
            val subtitle : String


            if(content.contains("\n")){
                title = content.split("\n")[0]
                subtitle = content.split("\n")[1]
            }else{
                title = content
                subtitle = ""
            }

            val note = createNote(title, subtitle,  content,isStarred, selectedNote.datetime!!, selectedNote.id, folderId = selectedNote.folderId)

            if(content.isNotEmpty()){
                vm.addNote(note, args.isNew)
                findNavController().navigateUp()
            }
        }

    }

    private fun createNote(
        title: String,
        subtitle: String,
        content: String,
        isStarred: Boolean,
        date: String,
        id: Int,
        folderId : Int
    ): Note {
        return Note(
            title,
            subtitle,
            content,
            isStarred,
            date,
            id,
            folderId
        )
    }
}
