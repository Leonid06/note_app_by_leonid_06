package com.leonid.leonidsnotesapplication.presentation.notes_feature.adapters


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.leonid.leonidsnotesapplication.databinding.HomeNoteCardViewBinding
import com.leonid.leonidsnotesapplication.databinding.NoteCardViewBinding
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.domain.model.Note
import com.leonid.leonidsnotesapplication.presentation.notes_feature.callbacks.HomeNoteCardAdapterCallback
import com.leonid.leonidsnotesapplication.presentation.notes_feature.callbacks.NoteCardAdapterCallback
import com.leonid.leonidsnotesapplication.presentation.notes_feature.callbacks.OnTouchListener
import com.leonid.leonidsnotesapplication.presentation.notes_feature.util.NotesDiffUtil


class HomeNoteCardAdapter(
    private val onTouchListener : NoteTouchListener
) : RecyclerView.Adapter<HomeNoteCardAdapter.ViewHolder>() {


    interface NoteTouchListener {
        fun onNoteClicked(note : Note)
        fun onDeleteButtonClick(note : Note)
        fun onStarCheckBoxClick(note : Note)
    }

    private val notes = ArrayList<Note>()
    private val folders = ArrayList<Folder>()

    private val notesListCallback =  HomeNoteCardAdapterCallback(this)

    class ViewHolder(private val binding: HomeNoteCardViewBinding,
                     private val onTouchListener: NoteTouchListener
    ) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnTouchListener(object : OnTouchListener(binding.root.context){

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

        fun bind(note: Note, folder : Folder) {
            binding.note = note
            binding.folder = folder
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeNoteCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding ,onTouchListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]
        val folder = folders.find {
            it.id == note.folderId
        } ?: Folder("")
        holder.bind(note, folder)
    }


    fun setData(notes : ArrayList<Note>, folders : ArrayList<Folder>){
        notes.reverse()

        val diffUtil = NotesDiffUtil(this.notes, notes)
        val diffResult = DiffUtil.calculateDiff(diffUtil)


        this.folders.clear()
        this.folders.addAll(folders)

        this.notes.clear()
        this.notes.addAll(notes)

        diffResult.dispatchUpdatesTo(notesListCallback)

        notes.reverse()
    }

    override fun getItemCount() =  notes.size

}
