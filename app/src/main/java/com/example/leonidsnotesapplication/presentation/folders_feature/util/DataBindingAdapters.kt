package com.example.leonidsnotesapplication.presentation.folders_feature.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.folders_feature.FoldersAdapter


@BindingAdapter("setAdapter")
fun setAdapter(
    recyclerView: RecyclerView,
    adapter : FoldersAdapter
) {
    adapter.let {
        recyclerView.adapter = it
    }
}

@BindingAdapter("submitList")
fun submitList(
    recyclerView: RecyclerView,
    data : ArrayList<Folder>?,
){
    val adapter = recyclerView.adapter as FoldersAdapter
    adapter.setData((data ?: arrayListOf()))
}


