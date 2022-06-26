package com.example.leonidsnotesapplication.presentation.notes_feature


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.databinding.NoteCardViewBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.callbacks.NotesListCallback
import com.example.leonidsnotesapplication.presentation.notes_feature.callbacks.OnTouchListener
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesDiffUtil


class NoteCardAdapter(
    private val onTouchListener : NoteTouchListener
) :
    RecyclerView.Adapter<NoteCardAdapter.ViewHolder>() {


    interface NoteTouchListener {
        fun onNoteSwipedLeft(note : Note) : Boolean
        fun onNoteClicked(note : Note)
        fun onDeleteButtonClick(note : Note)
        fun onStarCheckBoxClick(note : Note)
    }

    private val notes = ArrayList<Note>()
    private val notesListCallback =  NotesListCallback(this)

    class ViewHolder(private val binding: NoteCardViewBinding ,
                     private val onTouchListener: NoteTouchListener
                     ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var note: Note


        init {
            binding.root.setOnTouchListener(object : OnTouchListener(binding.root.context){
                override fun onSwipeLeft(): Boolean {
                    onTouchListener.onNoteSwipedLeft(note)
                    return true
                }

                override fun onClick(): Boolean {
                    onTouchListener.onNoteClicked(note)
                    return true
                }
            })
            binding.ibDelete.setOnClickListener {
                onTouchListener.onDeleteButtonClick(note)
            }
            binding.cbStar.setOnClickListener {
                note.isStarred = binding.cbStar.isChecked
                onTouchListener.onStarCheckBoxClick(note)
            }
        }

        fun bind(note: Note) {
            this.note = note
            binding.tvNoteTitle.text = note.title
            binding.tvNoteDatetime.text  = note.datetime
            binding.tvNoteSubtitle.text = note.subtitle
            binding.cbStar.isChecked = note.isStarred
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NoteCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding ,onTouchListener)
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

