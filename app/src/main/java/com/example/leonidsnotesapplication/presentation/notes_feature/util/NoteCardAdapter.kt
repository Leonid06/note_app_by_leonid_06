package com.example.leonidsnotesapplication.presentation.notes_feature.util


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.NoteCardViewBinding
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

    class ViewHolder(private val binding: NoteCardViewBinding ,  private val listener  : NoteClickListener) : RecyclerView.ViewHolder(binding.root) , View.OnClickListener {

        private lateinit var note: Note


        init {
            binding.root.setOnClickListener(this)
            binding.ibDelete.setOnClickListener {
                listener.onDeleteButtonClick(note)
            }
            binding.cbStar.setOnClickListener {
                note.isStarred = binding.cbStar.isChecked
                listener.onStarCheckBoxClick(note)
            }
        }

        fun bind(note: Note) {
            this.note = note
            binding.tvNoteTitle.text = note.title
            binding.tvNoteDatetime.text  = note.datetime
            binding.tvNoteSubtitle.text = note.subtitle
            binding.cbStar.isChecked = note.isStarred
        }

        override fun onClick(p0: View?) {
            listener.onClickedNote(note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NoteCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding , listener)
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

    override fun getItemCount() =  notes.size


}

