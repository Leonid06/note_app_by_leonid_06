package com.example.leonidsnotesapplication.presentation.single_note_feature

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.leonidsnotesapplication.presentation.MainActivity
import com.example.leonidsnotesapplication.presentation.extensions.showKeyboard
import com.example.leonidsnotesapplication.presentation.notes_feature.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@AndroidEntryPoint
class SingleNoteFragment : Fragment() {

    private val vm  : NotesViewModel  by viewModels()

    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_single_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addNoteButton = view.findViewById<FloatingActionButton>(R.id.add_note_button)
        val noteContentEditText = view.findViewById<EditText>(R.id.etNoteContent)

        activity?.showKeyboard(noteContentEditText)

        if(arguments != null){
            isEdit = true
            noteContentEditText.setText(arguments?.getParcelable<Note>("note")!!.content)
        }
        addNoteButton.setOnClickListener{

            val content = noteContentEditText.text.toString()
            val title : String
            val subtitle : String
            val date = getDate()


            if(content.contains("\n")){
                title = content.split("\n")[0]
                subtitle = content.split("\n")[1]
            }else{
                title = content
                subtitle = ""
            }

            if(isEdit){

                val clickedNote : Note = arguments?.getParcelable("note")!!

                createNote(title, subtitle,  content, date, clickedNote.id)

            }else{
                 createNote(title, subtitle, content, date)
            }

            findNavController().navigateUp()

        }

    }

    private fun createNote(title : String,subtitle : String, content : String, date : String, id : Int){
        val note = Note(
            title,
            subtitle,
            content,
            date,
            id
        )
        Toast.makeText(requireContext(), "Note added", Toast.LENGTH_SHORT).show()

        vm.addNote(note)
    }

    private fun createNote(title : String, subtitle: String, content : String, date : String){
        val note = Note(
            title,
            subtitle,
            content,
            date
        )
        Toast.makeText(requireContext(), "Note added", Toast.LENGTH_SHORT).show()

        vm.addNote(note)
    }


    companion object{
        fun getDate() : String {
            val localDate = LocalDate.now()
            val dateFormatter : DateTimeFormatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.MEDIUM)
                .withZone(ZoneId.systemDefault())
            return localDate.format(dateFormatter)
        }

    }
}
