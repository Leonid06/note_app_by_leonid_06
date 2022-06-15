package com.example.leonidsnotesapplication.presentation.folders_feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.leonidsnotesapplication.R
import com.example.leonidsnotesapplication.presentation.notes_feature.NotesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FoldersFragment : Fragment() {

   val vm : FoldersViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_folders, container, false)
    }
}