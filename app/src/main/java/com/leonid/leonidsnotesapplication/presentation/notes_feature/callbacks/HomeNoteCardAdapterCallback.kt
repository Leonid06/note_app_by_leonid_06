package com.leonid.leonidsnotesapplication.presentation.notes_feature.callbacks


import android.widget.Adapter
import androidx.constraintlayout.helper.widget.Carousel
import androidx.recyclerview.widget.ListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.leonid.leonidsnotesapplication.presentation.notes_feature.adapters.HomeNoteCardAdapter
import com.leonid.leonidsnotesapplication.presentation.notes_feature.adapters.NoteCardAdapter

class HomeNoteCardAdapterCallback(private val adapter: HomeNoteCardAdapter) : ListUpdateCallback {

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