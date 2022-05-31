package com.example.leonidsnotesapplication.presentation.notes_feature.util


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.NotesViewModel



class NoteCardAdapter(private val vm : NotesViewModel) :
    RecyclerView.Adapter<NoteCardAdapter.ViewHolder>() {


    private var notes : List<Note> = emptyList()


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val titleView : TextView = view.findViewById(R.id.tvNoteTitle)
        val deleteButton  : ImageButton = view.findViewById(R.id.ibDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_card_view, parent,  false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.titleView.text = notes.elementAt(position).title
        viewHolder.itemView.setOnClickListener{
            val navController = Navigation.findNavController(viewHolder.itemView)

            val bundle = Bundle()
            val clickedNote = vm.getNoteByPosition(position)

            bundle.putParcelable("note" , clickedNote)

            navController.navigate(R.id.action_notesFragment_to_singleNoteFragment , bundle)
        }
        viewHolder.deleteButton.setOnClickListener {
            vm.deleteNote(position)
        }
    }

    fun setData(notes : List<Note>){
        this.notes = notes
    }
    override fun getItemCount(): Int =  notes.size

}