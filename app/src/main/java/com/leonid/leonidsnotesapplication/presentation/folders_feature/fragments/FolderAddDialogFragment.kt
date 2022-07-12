package com.leonid.leonidsnotesapplication.presentation.folders_feature.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.leonid.leonidsnotesapplication.databinding.CreateFolderDialogBinding
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.presentation.folders_feature.viewmodels.FoldersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FolderAddDialogFragment : DialogFragment() {

    private val vm : FoldersViewModel by viewModels()

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
            if(binding.etTitleInput.text.toString() != ""){
                vm.addFolder(Folder(binding.etTitleInput.text.toString()))
                dismiss()
            }
        }
    }
}