package com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leonidsnotesapplication.domain.model.Folder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderSharedViewModel @Inject constructor(

): ViewModel() {

    private val _selectedFolder: MutableLiveData<Folder> = MutableLiveData<Folder>()
    val selectedFolder: LiveData<Folder> get() = _selectedFolder

    fun selectFolder(folder: Folder) {
        _selectedFolder.value = folder
    }
}