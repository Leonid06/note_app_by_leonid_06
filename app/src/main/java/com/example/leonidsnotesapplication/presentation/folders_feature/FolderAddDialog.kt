package com.example.leonidsnotesapplication.presentation.folders_feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.databinding.CreateFolderDialogBinding
import com.example.leonidsnotesapplication.domain.model.Folder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FolderAddDialog() : DialogFragment() {

    private val vm : FoldersViewModel by activityViewModels()

    private var _binding : CreateFolderDialogBinding ? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateFolderDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.cancelButton.setOnClickListener{
            dismiss()
        }

        binding.createButton.setOnClickListener{
            vm.addFolder(Folder(binding.etTitleInput.text.toString()))
            dismiss()
        }
    }
}