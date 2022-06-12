package com.example.leonidsnotesapplication.presentation.notes_feature.util
import androidx.recyclerview.widget.DiffUtil
import com.example.leonidsnotesapplication.domain.model.Note

class NotesDiffUtil(
    private val oldList : List<Note>,
    private val newList : List<Note>
    ) : DiffUtil.Callback() {
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
                oldList[oldItemPosition].content != newList[newItemPosition].content -> {
                    false
                }
                oldList[oldItemPosition].title != newList[newItemPosition].title -> {
                    false
                }
                oldList[oldItemPosition].subtitle != newList[newItemPosition].subtitle -> {
                    false
                }
                oldList[oldItemPosition].datetime != newList[newItemPosition].datetime -> {
                    false
                }
                else -> true
            }
        }

    }

}