package com.example.leonidsnotesapplication.presentation.folders_feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoldersViewModel @Inject constructor(
    private val foldersRepository: FoldersRepository
) : ViewModel(){


    init {
        updateAllFolders()
    }

    private val _foldersLiveData : MutableLiveData<ArrayList<Folder>> =
        MutableLiveData<ArrayList<Folder>>()

    val foldersLiveData : LiveData<ArrayList<Folder>> =  _foldersLiveData

    private fun getAllFolders() : ArrayList<Folder> {
        return foldersRepository.getAllFolders() as ArrayList<Folder>
    }

    fun deleteFolder(folder : Folder){
        viewModelScope.launch(Dispatchers.IO) {
            foldersRepository.deleteFolder(folder)
            updateAllFolders()
        }
    }

    fun addFolder(folder : Folder){
        viewModelScope.launch(Dispatchers.IO) {
            foldersRepository.addFolder(folder)
            updateAllFolders()
        }
    }

    fun updateAllFolders(){
        viewModelScope.launch(Dispatchers.IO){
            _foldersLiveData.postValue(getAllFolders())
        }
    }

}