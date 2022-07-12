package com.leonid.leonidsnotesapplication.presentation.notes_feature.util
import androidx.recyclerview.widget.DiffUtil
import com.leonid.leonidsnotesapplication.domain.model.Note

class NotesDiffUtil(
    private val oldList : ArrayList<Note>,
    private val newList : ArrayList<Note>
    ) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]

        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}