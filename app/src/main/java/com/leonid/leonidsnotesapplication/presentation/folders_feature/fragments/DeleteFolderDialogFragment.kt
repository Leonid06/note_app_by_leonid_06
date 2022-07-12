package com.leonid.leonidsnotesapplication.presentation.folders_feature.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.leonid.leonidsnotesapplication.databinding.DeleteFolderDialogBinding
import com.leonid.leonidsnotesapplication.presentation.folders_feature.viewmodels.FolderSharedViewModel
import com.leonid.leonidsnotesapplication.presentation.folders_feature.viewmodels.FoldersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteFolderDialogFragment : DialogFragment() {

    private val vm : FoldersViewModel by viewModels()
    private val folderSharedViewModel : FolderSharedViewModel by activityViewModels()

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

        binding.confirmButton.setOnClickListener{
            vm.deleteFolder(folderSharedViewModel.selectedFolder.value!!)
            findNavController().navigate(DeleteFolderDialogFragmentDirections.actionDeleteFolderDialogFragmentToFoldersFragment())
        }
    }
}