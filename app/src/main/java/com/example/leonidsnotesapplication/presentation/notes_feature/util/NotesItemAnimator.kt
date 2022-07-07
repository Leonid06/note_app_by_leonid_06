package com.example.leonidsnotesapplication.presentation.notes_feature.util

import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.example.leonidsnotesapplication.R

class NotesItemAnimator : DefaultItemAnimator() {

    init {
        supportsChangeAnimations = false
    }

    override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean {
        val animation = AnimationUtils.loadAnimation(holder?.itemView?.context, R.anim.slide_out)
        holder?.itemView?.startAnimation(animation)
        return super.animateRemove(holder)
    }

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        val animation = AnimationUtils.loadAnimation(holder?.itemView?.context, R.anim.slide_in)
        holder?.itemView?.startAnimation(animation)
        return  super.animateAdd(holder)
    }
}