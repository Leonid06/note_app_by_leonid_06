package com.example.leonidsnotesapplication.presentation.folders_feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import com.example.leonidsnotesapplication.domain.usecase.folders_feature.AddFolderUseCase
import com.example.leonidsnotesapplication.domain.usecase.folders_feature.GetAllFoldersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoldersViewModel @Inject constructor(
    private val foldersRepository: FoldersRepository
) : ViewModel() {

    private val foldersMutableLiveData : MutableLiveData<ArrayList<Folder>> =
        MutableLiveData<ArrayList<Folder>>()

    val foldersLiveData : LiveData<ArrayList<Folder>> =  foldersMutableLiveData

    private fun getAllFolders() : ArrayList<Folder> {
        return foldersRepository.getAllFolders() as ArrayList<Folder>
    }

    fun addFolder(folder : Folder){
        viewModelScope.launch(Dispatchers.IO) {
            foldersRepository.addFolder(folder)
        }
        updateAllFolders()

    }

    private fun updateAllFolders(){
        viewModelScope.launch(Dispatchers.IO){
            foldersMutableLiveData.postValue(getAllFolders())
        }
    }

}