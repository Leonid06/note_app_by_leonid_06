package com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FolderSharedViewModel @Inject constructor(

): ViewModel() {

    private val _selectedFolder: MutableLiveData<Folder> = MutableLiveData<Folder>()
    val selectedFolder: LiveData<Folder> get() = _selectedFolder

    private val _option : MutableLiveData<SortOption> = MutableLiveData<SortOption>()
    val option : LiveData<SortOption> get()= _option

    fun selectOption(option: SortOption){
        _option.value = option
    }

    fun selectFolder(folder: Folder) {
        _selectedFolder.value = folder
    }
}