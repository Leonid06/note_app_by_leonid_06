package com.example.leonidsnotesapplication.presentation.folders_feature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EdgeEffect
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.domain.model.Folder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FolderAddDialog : DialogFragment() {

    private val vm : FoldersViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_folder_dialog,container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val cancelButton = view.findViewById<Button>(R.id.cancelButton)
        val createButton = view.findViewById<Button>(R.id.createButton)
        val addFolderView = view.findViewById<EditText>(R.id.etTitleInput)

        cancelButton.setOnClickListener{
            findNavController().navigateUp()
        }

        createButton.setOnClickListener{
            vm.addFolder(Folder(addFolderView.text.toString()))
            findNavController().navigateUp()
        }
    }
}