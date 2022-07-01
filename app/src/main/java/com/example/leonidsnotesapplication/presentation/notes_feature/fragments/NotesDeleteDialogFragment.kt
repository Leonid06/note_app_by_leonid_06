package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint


class NotesDeleteDialogFragment : DialogFragment() {

    private val vm : NotesViewModel by activityViewModels()
    private val noteSharedViewModel : NoteSharedViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Delete confirmation")
            .setPositiveButton("confirm"){
                    _,_ -> vm.deleteNote(noteSharedViewModel.deleteNote.value!!)
            }
            .setNegativeButton("cancel") { _,_ -> }
            .create()
}