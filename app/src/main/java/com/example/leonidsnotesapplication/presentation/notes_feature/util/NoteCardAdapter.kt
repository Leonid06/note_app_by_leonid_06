package com.example.leonidsnotesapplication.presentation.notes_feature.util


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.domain.model.Note


class NoteCardAdapter( private val listener: NoteClickListener) :
    RecyclerView.Adapter<NoteCardAdapter.ViewHolder>() {

    interface NoteClickListener{
        fun onClickedNote(note : Note)
        fun onDeleteButtonClick(note : Note)
        fun onStarCheckBoxClick(note : Note)
    }

    private val notes = ArrayList<Note>()
    private val notesListCallback =  NotesListCallback(this)

    class ViewHolder(view : View ,  private val listener  : NoteClickListener) : RecyclerView.ViewHolder(view) , View.OnClickListener {
        private val titleView: TextView = view.findViewById(R.id.tvNoteTitle)
        private val subtitleView : TextView = view.findViewById(R.id.tvNoteSubtitle)
        private val datetimeView : TextView = view.findViewById(R.id.tvNoteDatetime)
        private val deleteButton: ImageButton = view.findViewById(R.id.ibDelete)
        private val starCheckBox : CheckBox = view.findViewById(R.id.cbStar)

        private lateinit var note: Note


        init {
            view.setOnClickListener(this)
            deleteButton.setOnClickListener {
                listener.onDeleteButtonClick(note)
            }
            starCheckBox.setOnClickListener {
                note.isStarred = starCheckBox.isChecked
                listener.onStarCheckBoxClick(note)
            }
        }

        fun bind(note: Note) {
            this.note = note
            titleView.text = note.title
            datetimeView.text  = note.datetime
            subtitleView.text = note.subtitle
            starCheckBox.isChecked = note.isStarred
        }

        override fun onClick(p0: View?) {
            listener.onClickedNote(note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.note_card_view, parent,  false)

        return ViewHolder(view , listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position])
    }


    fun setData(notes : ArrayList<Note>){
        notes.reverse()
        val diffUtil = NotesDiffUtil(this.notes, notes)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        this.notes.clear()
        this.notes.addAll(notes)

        diffResult.dispatchUpdatesTo(notesListCallback)

        notes.reverse()
    }

    override fun getItemCount(): Int =  notes.size


}

