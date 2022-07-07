package com.example.leonidsnotesapplication.presentation.folders_feature

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
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
        fun onTitleTextChanged(title : String, folder: Folder)
    }

    class ViewHolder(private val binding: FolderCardViewBinding,  private val listener : FolderClickListener) :
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.apply {
                root.setOnClickListener{
                    listener.onClickedFolder(folder!!)
                }
            }
        }

        fun bind(folder : Folder){
            binding.folder = folder
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding : FolderCardViewBinding =
            FolderCardViewBinding.inflate(inflater)

        return ViewHolder(binding, listener)
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