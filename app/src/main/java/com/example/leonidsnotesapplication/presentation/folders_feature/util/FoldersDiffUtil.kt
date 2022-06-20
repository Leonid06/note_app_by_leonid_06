package com.example.leonidsnotesapplication.presentation.folders_feature.util

import androidx.recyclerview.widget.DiffUtil
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note

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
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        if(oldList.size == newList.size){
            return false
        }else{
            return when {
                oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                    false
                }
                oldList[oldItemPosition].title != newList[newItemPosition].title -> {
                    false
                }

                else -> true
            }
        }

    }

}