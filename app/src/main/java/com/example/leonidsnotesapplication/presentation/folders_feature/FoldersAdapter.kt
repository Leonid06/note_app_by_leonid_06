package com.example.leonidsnotesapplication.presentation.folders_feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FolderCardViewBinding
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.folders_feature.callbacks.SwipeCallback
import com.example.leonidsnotesapplication.presentation.folders_feature.util.FoldersDiffUtil

class FoldersAdapter(private val listener : FolderClickListener) : RecyclerView.Adapter<FoldersAdapter.ViewHolder>() {

    private val folders = ArrayList<Folder>()

    interface FolderClickListener {
        fun onClickedFolder(folder : Folder)
        fun setUpOnItemSwiped(swipe: SwipeCallback)
        fun onDeleteSwiped(folder : Folder)
    }

    init {
        setUpOnSwiped()
    }

    class ViewHolder(private val binding: FolderCardViewBinding,  private val listener : FolderClickListener) :
        RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        private lateinit var folder : Folder

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onClickedFolder(folder)
        }

        fun bind(folder : Folder){
            binding.folder = folder
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding : FolderCardViewBinding =
            DataBindingUtil.inflate(inflater, R.layout.folder_card_view, parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(folders[position])
    }

    private fun setUpOnSwiped() {
        val swipe = object : SwipeCallback(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listener.onDeleteSwiped(folders[viewHolder.bindingAdapterPosition])
            }
        }
        listener.setUpOnItemSwiped(swipe)
    }

    fun setData(folders : ArrayList<Folder>){
        folders.reverse()

        val diffUtil = FoldersDiffUtil(this.folders, folders)
        val diffResult = DiffUtil.calculateDiff(diffUtil)

        this.folders.clear()
        this.folders.addAll(folders)

        diffResult.dispatchUpdatesTo(this)

        folders.reverse()
    }

    override fun getItemCount() = folders.size

}