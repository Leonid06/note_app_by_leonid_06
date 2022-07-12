package com.leonid.leonidsnotesapplication.presentation.notes_feature.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.leonid.leonidsnotesapplication.databinding.DeleteFolderDialogBinding
import com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel
import com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels.NotesViewModel
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