package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NotesViewModel

class DeleteDialogFragment : DialogFragment() {

    private val vm : NotesViewModel by activityViewModels()
    private val args : DeleteDialogFragmentArgs by navArgs()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Delete confirmation")
            .setPositiveButton("confirm"){
                    _,_ -> vm.deleteNote(args.note)
            }
            .setNegativeButton("cancel") { _,_ -> }
            .create()
}