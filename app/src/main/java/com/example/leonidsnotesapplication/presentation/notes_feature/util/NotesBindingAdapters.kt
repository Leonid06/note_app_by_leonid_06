package com.example.leonidsnotesapplication.presentation.notes_feature.util

import android.opengl.Visibility
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.model.NoteViewData
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter


@BindingAdapter("setNoteAdapter")
fun setNoteAdapter(recyclerView: RecyclerView, adapter: NoteCardAdapter){
    adapter.let {
        recyclerView.adapter = it
    }
}

//@BindingAdapter("setHomeAdapter")
//fun setNoteAdapter(recyclerView: RecyclerView, adapter: HomeNoteAdapter){
//    adapter.let {
//        recyclerView.adapter = it
//    }
//}
//@BindingAdapter("submitNoteViewData")
//fun submitNoteViewData(recyclerView: RecyclerView, data : ArrayList<NoteViewData>?){
//    val adapter =  recyclerView.adapter as HomeNoteAdapter
//    adapter.setData((data ?: arrayListOf()))
//}
@BindingAdapter("submitNoteList")
fun submitNoteList(recyclerView: RecyclerView, data : List<Note>?){
    val adapter = recyclerView.adapter as NoteCardAdapter
    adapter.setData((data as ArrayList<Note>? ?: arrayListOf()))
}

@BindingAdapter("setFolderVisibility")
fun setFolderImageVisibility(view: ImageView, state : Boolean){
    view.isVisible = state
}