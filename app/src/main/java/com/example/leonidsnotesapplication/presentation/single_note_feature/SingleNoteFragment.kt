package com.example.leonidsnotesapplication.presentation.single_note_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@AndroidEntryPoint
class SingleNoteFragment : Fragment() {

    private val vm  : NotesViewModel  by viewModels()

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

            noteTitleEditText.setText(arguments?.getParcelable<Note>("note")!!.title)
            noteContentEditText.setText(arguments?.getParcelable<Note>("note")!!.content)

        }
        addNoteButton.setOnClickListener{


            val date : String = getDate()
            val title = noteTitleEditText.text.toString()
            val content = noteContentEditText.text.toString()

            if(isEdit){

                val clickedNote : Note = arguments?.getParcelable("note")!!

                createNote(title,  content, date, clickedNote.id)

            }else{
                 createNote(title, content, date)
            }
            vm.updateNotes()
            findNavController().navigateUp()

        }

    }

    private fun createNote(title : String, content : String, date : String, id : Int){
        val note = Note(
            title,
            content,
            date,
            id
        )
        Toast.makeText(requireContext(), "Note added", Toast.LENGTH_SHORT).show()

        vm.addNote(note)
    }

    private fun createNote(title : String, content : String, date : String){
        val note = Note(
            title,
            content,
            date
        )
        Toast.makeText(requireContext(), "Note added", Toast.LENGTH_SHORT).show()

        vm.addNote(note)
    }

    companion object{
        fun getDate() : String {
            val localDateTime = LocalDateTime.now()
            val dateFormatter : DateTimeFormatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withZone(ZoneId.systemDefault())
            return localDateTime.format(dateFormatter)
        }
    }
}
