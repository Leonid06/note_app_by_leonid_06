package com.leonid.leonidsnotesapplication.presentation.folders_feature.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.presentation.folders_feature.FoldersAdapter


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
    data : ArrayList<Folder>?,
){
    val adapter = recyclerView.adapter as FoldersAdapter
    adapter.setData((data ?: arrayListOf()))
}


