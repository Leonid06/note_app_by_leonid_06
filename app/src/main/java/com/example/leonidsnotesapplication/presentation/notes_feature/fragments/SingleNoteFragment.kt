package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.leonidsnotesapplication.databinding.FragmentSingleNoteBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.extensions.getDate
import com.example.leonidsnotesapplication.presentation.extensions.showKeyboard
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingleNoteFragment : Fragment() {

    private val vm : NotesViewModel by activityViewModels()

    private val args : SingleNoteFragmentArgs by navArgs()

    private var _binding : FragmentSingleNoteBinding? = null

    private val binding  get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSingleNoteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.showKeyboard(binding.etNoteContent)

        Log.d("Debugging",vm.toString())

        binding.etNoteContent.setText(args.note.content)
        binding.addNoteButton.setOnClickListener{

            val content = binding.etNoteContent.text.toString()
            val title : String
            val subtitle : String

            val folderId : Int = if(!args.isDefaultFolder){
                vm.currentFolderLiveData.value!!.id
            }else{
                -1
            }

            if(content.contains("\n")){
                title = content.split("\n")[0]
                subtitle = content.split("\n")[1]
            }else{
                title = content
                subtitle = ""
            }

            val clickedNote : Note = args.note
            val isStarred = clickedNote.isStarred

            val note = createNote(title, subtitle,  content,isStarred, clickedNote.datetime!!, clickedNote.id, folderId = folderId)

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
