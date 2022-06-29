package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.leonidsnotesapplication.domain.model.Note

class DeleteDialogFragment(
    private val note : Note,
    private val listener : OnNegativeButtonClickListener
        ): DialogFragment() {

    interface OnNegativeButtonClickListener {
        fun onDeleteOptionClicked(note : Note)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Delete confirmation")
            .setPositiveButton("confirm"){
                    _,_ -> listener.onDeleteOptionClicked(note)
            }
            .setNegativeButton("cancel") { _,_ -> }
            .create()
    
    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }
}