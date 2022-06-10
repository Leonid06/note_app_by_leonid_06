package com.example.leonidsnotesapplication.presentation.notes_feature.util


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.domain.model.Note
import java.util.*
import kotlin.collections.ArrayList


class NoteCardAdapter( private val listener: NoteClickListener) :
    RecyclerView.Adapter<NoteCardAdapter.ViewHolder>() {

    interface NoteClickListener{
        fun onClickedNote(note : Note)
        fun onDeleteButtonClick(note : Note)
    }

    private val notes = ArrayList<Note>()
    private var lastPosition = -1

    class ViewHolder(view : View ,  private val listener  : NoteClickListener) : RecyclerView.ViewHolder(view) , View.OnClickListener {
        private val titleView: TextView = view.findViewById(R.id.tvNoteTitle)
        private val subtitleView : TextView = view.findViewById(R.id.tvNoteSubtitle)
        private val datetimeView : TextView = view.findViewById(R.id.tvNoteDatetime)
        private val deleteButton: ImageButton = view.findViewById(R.id.ibDelete)
        private lateinit var note: Note


        init {
            view.setOnClickListener(this)
            deleteButton.setOnClickListener {
                listener.onDeleteButtonClick(note)
            }
        }

        fun bind(note: Note) {
            this.note = note
            titleView.text = note.title
            datetimeView.text  = note.datetime
            subtitleView.text = note.subtitle
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

        if(holder.adapterPosition > lastPosition){
            val animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.slide_in)
            holder.itemView.startAnimation(animation)
            lastPosition = holder.adapterPosition
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        val animation = AnimationUtils.loadAnimation(holder.itemView.context,R.anim.slide_out)
        holder.itemView.startAnimation(animation)
    }



    fun setData(notes : ArrayList<Note>){
        this.notes.clear()
        this.notes.addAll(notes.reversed())
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =  notes.size


}

