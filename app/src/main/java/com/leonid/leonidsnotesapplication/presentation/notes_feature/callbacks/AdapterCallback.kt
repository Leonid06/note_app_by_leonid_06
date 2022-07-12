package com.leonid.leonidsnotesapplication.presentation.notes_feature.callbacks

import androidx.recyclerview.widget.ListUpdateCallback
import com.leonid.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter

class AdapterCallback(private val adapter: NoteCardAdapter) : ListUpdateCallback {

    override fun onInserted(position: Int, count: Int) {
        adapter.notifyItemRangeInserted(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        adapter.notifyItemRangeRemoved(position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        adapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        adapter.notifyItemRangeChanged(position, count)
    }
}