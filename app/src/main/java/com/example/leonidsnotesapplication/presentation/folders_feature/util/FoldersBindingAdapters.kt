package com.example.leonidsnotesapplication.presentation.folders_feature.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.folders_feature.FoldersAdapter


@BindingAdapter("setFolderAdapter")
fun setFolderAdapter(
    recyclerView: RecyclerView,
    adapter : FoldersAdapter
) {
    adapter.let {
        recyclerView.adapter = it
    }
}

@BindingAdapter("submitFolderList")
fun submitFolderList(
    recyclerView: RecyclerView,
    data : List<Folder>?,
){
    val adapter = recyclerView.adapter as FoldersAdapter
    adapter.setData((data as ArrayList<Folder>? ?: arrayListOf()))
}


