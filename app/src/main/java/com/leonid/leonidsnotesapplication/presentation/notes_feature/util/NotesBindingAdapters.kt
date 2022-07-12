package com.leonid.leonidsnotesapplication.presentation.notes_feature.util

import android.util.Log
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.domain.model.Note
import com.leonid.leonidsnotesapplication.presentation.notes_feature.adapters.FolderEditAdapter
import com.leonid.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter


@BindingAdapter("setNoteAdapter")
fun setNoteAdapter(recyclerView: RecyclerView, adapter: NoteCardAdapter){
    adapter.let {
        recyclerView.adapter = it
        recyclerView.smoothScrollToPosition(0)
    }
}

@BindingAdapter("setEditFolderAdapter")
fun setEditFolderAdapter(recyclerView: RecyclerView, adapter: FolderEditAdapter){
    adapter.let {
        recyclerView.adapter = it
        recyclerView.smoothScrollToPosition(0)
    }
}

@BindingAdapter("submitEditFolderList")
fun submitEditFolderList(recyclerView: RecyclerView, data : ArrayList<Folder>?){
    val adapter = recyclerView.adapter as FolderEditAdapter
    adapter.setData((data ?: arrayListOf()))
}

@BindingAdapter("submitNoteList")
fun submitNoteList(recyclerView: RecyclerView, data : ArrayList<Note>?){
    val adapter = recyclerView.adapter as NoteCardAdapter
    adapter.setData((data ?: arrayListOf()))
    Log.d("Debug", "Submitted ${data?.size.toString()} items to recyclerview")
}


@BindingAdapter("setFolderVisibility")
fun setFolderImageVisibility(view: ImageView, state : Boolean){
    view.isVisible = state
}