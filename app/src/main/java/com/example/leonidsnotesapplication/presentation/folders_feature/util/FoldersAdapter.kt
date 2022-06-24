package com.example.leonidsnotesapplication.presentation.folders_feature.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.FolderCardViewBinding
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.notes_feature.util.NotesDiffUtil

class FoldersAdapter(private val listener :FolderClickListener) : RecyclerView.Adapter<FoldersAdapter.ViewHolder>() {

    private val folders = ArrayList<Folder>()

    interface FolderClickListener {
        fun onClickedFolder(folder : Folder)
    }
    class ViewHolder(view : View,  private val listener : FolderClickListener) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val binding = FolderCardViewBinding.bind(view)

        private lateinit var folder : Folder

        init {
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            listener.onClickedFolder(folder)
        }

        fun bind(folder : Folder){
            this.folder = folder
            binding.tvFolderTitle.text = folder.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.folder_card_view, parent, false)

        return ViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(folders[position])
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