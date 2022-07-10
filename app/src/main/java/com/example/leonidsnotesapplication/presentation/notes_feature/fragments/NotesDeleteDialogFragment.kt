package com.example.leonidsnotesapplication.presentation.notes_feature.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.example.leonidsnotesapplication.databinding.DeleteFolderDialogBinding
import com.example.leonidsnotesapplication.databinding.EditFolderDialogBinding
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel
import com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotesDeleteDialogFragment : DialogFragment() {

    private val vm : NotesViewModel by viewModels()
    private val noteSharedViewModel : NoteSharedViewModel by activityViewModels()

    private var _binding : DeleteFolderDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DeleteFolderDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.cancelButton.setOnClickListener{
            dismiss()
        }
        binding.confirmButton.setOnClickListener {
            val note = noteSharedViewModel.deleteNote.value!!
            vm.deleteNote(note)
            dismiss()
        }
    }
}