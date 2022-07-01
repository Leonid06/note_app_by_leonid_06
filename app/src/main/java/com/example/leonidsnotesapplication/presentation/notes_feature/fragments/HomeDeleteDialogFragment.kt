package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.HomeViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel

class HomeDeleteDialogFragment : DialogFragment() {
    private val vm : HomeViewModel by activityViewModels()
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