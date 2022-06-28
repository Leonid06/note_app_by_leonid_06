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


        init {
            binding.root.setOnTouchListener(object : OnTouchListener(binding.root.context){
                override fun onSwipeLeft(): Boolean {
                    onTouchListener.onNoteSwipedLeft(binding.note!!)
                    return true
                }

                override fun onClick(): Boolean {
                    onTouchListener.onNoteClicked(binding.note!!)
                    return true
                }
            })
            binding.ibDelete.setOnClickListener {
                onTouchListener.onDeleteButtonClick(binding.note!!)
            }
            binding.cbStar.setOnClickListener {
                binding.note!!.isStarred = binding.cbStar.isChecked
                onTouchListener.onStarCheckBoxClick(binding.note!!)
            }
        }

        fun bind(note: Note) {
            binding.note = note
            binding.executePendingBindings()
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

