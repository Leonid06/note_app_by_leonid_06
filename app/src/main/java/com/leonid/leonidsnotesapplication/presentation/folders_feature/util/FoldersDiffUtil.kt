package com.leonid.leonidsnotesapplication.presentation.folders_feature.util

import androidx.recyclerview.widget.DiffUtil
import com.leonid.leonidsnotesapplication.domain.model.Folder

class FoldersDiffUtil(
    private val oldList : List<Folder>,
    private val newList : List<Folder>
) : DiffUtil.Callback(){
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}