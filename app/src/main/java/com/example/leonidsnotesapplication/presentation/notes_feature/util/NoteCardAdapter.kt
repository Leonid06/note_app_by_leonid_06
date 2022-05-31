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



class NoteCardAdapter( private val listener: NoteClicklistener) :
    RecyclerView.Adapter<NoteCardAdapter.ViewHolder>() {

    interface NoteClicklistener{
        fun onClickedNote(note : Note)
//        fun onDeleteButtonClickListener(note : Note)
    }

    private val notes = ArrayList<Note>()


    class ViewHolder(view : View ,  private val listener  : NoteClicklistener) : RecyclerView.ViewHolder(view) , View.OnClickListener {
        val titleView: TextView = view.findViewById(R.id.tvNoteTitle)
        val deleteButton: ImageButton = view.findViewById(R.id.ibDelete)
        private lateinit var note: Note

        init {
            view.setOnClickListener(this)
        }

        fun bind(note: Note) {
            this.note = note
            titleView.text = note.title
        }

        override fun onClick(p0: View?) {
            listener.onClickedNote(note)
            listener.onDeleteButtonClickListener(note)
            deleteButton.setOnClickListener {
                listener.onDeleteButtonClickListener(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_card_view, parent,  false)

        return ViewHolder(view , listener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(notes[position])

    fun setData(notes : ArrayList<Note>){
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int =  notes.size

}

private fun ImageButton.setOnClickListener(onDeleteButtonClickListener: Unit) {

}
