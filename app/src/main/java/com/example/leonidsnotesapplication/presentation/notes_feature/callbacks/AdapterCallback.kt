package com.example.leonidsnotesapplication.presentation.notes_feature.callbacks

import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.HomeNoteAdapter
import com.example.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter

class AdapterCallback<T : RecyclerView.Adapter<*>>(private val adapter: T) : ListUpdateCallback {

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