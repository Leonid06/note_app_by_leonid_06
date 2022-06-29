package com.example.leonidsnotesapplication.presentation.notes_feature.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter


@BindingAdapter("setNoteAdapter")
fun setNoteAdapter(recyclerView: RecyclerView, adapter: NoteCardAdapter){
    adapter.let {
        recyclerView.adapter = it
    }
}
@BindingAdapter("submitNoteList")
fun submitNoteList(recyclerView: RecyclerView, data : ArrayList<Note>?){
    val adapter = recyclerView.adapter as NoteCardAdapter
    adapter.setData((data ?: arrayListOf()))
}