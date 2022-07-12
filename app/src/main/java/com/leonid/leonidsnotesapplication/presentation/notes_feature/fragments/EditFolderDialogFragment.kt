package com.leonid.leonidsnotesapplication.presentation.notes_feature.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.leonid.leonidsnotesapplication.databinding.EditFolderDialogBinding
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.presentation.notes_feature.adapters.FolderEditAdapter
import com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels.NoteSharedViewModel
import com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels.SingleNoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditFolderDialogFragment : DialogFragment(),
FolderEditAdapter.FolderClickListener {

    private val vm : SingleNoteViewModel by viewModels()

    private val sharedNoteViewModel : NoteSharedViewModel by activityViewModels()

    private val adapter by lazy{ FolderEditAdapter(this as FolderEditAdapter.FolderClickListener)}

    private var _binding : EditFolderDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditFolderDialogBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            editFolderRecyclerView.isNestedScrollingEnabled = false
            editFolderRecyclerView.layoutManager = LinearLayoutManager(view.context)
        }
        binding.adapter = adapter
        binding.viewModel = vm
    }

    override fun onClickedFolder(folder: Folder) {
        vm.changeNoteFolder(sharedNoteViewModel.selectedNote.value!!, folder)
        dismiss()
    }
}