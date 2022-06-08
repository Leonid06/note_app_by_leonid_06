package com.example.leonidsnotesapplication.presentation.notes_feature.util

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentTransaction
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.domain.model.Note

class DeleteDialogFragment(
    private val note : Note,
    private val listener : OnNegativeButtonClickListener
        ): DialogFragment() {

    interface OnNegativeButtonClickListener {
        fun onClick(note : Note)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Delete confirmation")
            .setPositiveButton("confirm"){
                    _,_ -> listener.onClick(note)
            }
            .setNegativeButton("cancel") { _,_ -> }

            .create()
    
    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }
}