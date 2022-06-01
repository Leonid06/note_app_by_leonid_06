package com.example.leonidsnotesapplication.presentation.single_note_feature

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingleNoteFragment : Fragment() {

    private val vm  : NotesViewModel  by viewModels()

    private lateinit var clickedNote : Note

    private var isEdit  : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addNoteButton = view.findViewById<FloatingActionButton>(R.id.add_note_button)
        val noteTitleEditText = view.findViewById<EditText>(R.id.etNoteTitle)
        val noteContentEditText = view.findViewById<EditText>(R.id.etNoteContent)

        if(arguments != null){
            isEdit = true
            clickedNote = arguments?.getParcelable("note")!!

            noteTitleEditText.setText(clickedNote.title)
            noteContentEditText.setText(clickedNote.content)

        }
        addNoteButton.setOnClickListener{

            if(isEdit){


                val note =  Note(
                    noteTitleEditText.text.toString(),
                    noteContentEditText.text.toString() ,
                    clickedNote.id)
                vm.addNote(note)
            }else{
                clickedNote = Note(
                    noteTitleEditText.text.toString(),
                    noteContentEditText.text.toString()
                )
                vm.addNote(clickedNote)
            }
            vm.updateNotes()
            findNavController().navigateUp()
        }
    }
}