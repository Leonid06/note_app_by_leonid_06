package com.example.leonidsnotesapplication.presentation.folders_feature.viewmodels

import androidx.lifecycle.*
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoldersViewModel @Inject constructor(
    private val foldersRepository: FoldersRepository
) : ViewModel(){



    private var _foldersLiveData : MutableLiveData<List<Folder>> = MutableLiveData<List<Folder>>()

    val foldersLiveData : LiveData<List<Folder>> =  _foldersLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            foldersRepository.getAllFolders().collect{
                _foldersLiveData.postValue(it)
            }
        }

    }


    fun updateFolderTitle(folder : Folder, title : String){
        viewModelScope.launch(Dispatchers.IO){
            foldersRepository.updateFolderTitle(folder, title)
        }
    }

    fun deleteFolder(folder : Folder){
        viewModelScope.launch(Dispatchers.IO) {
            foldersRepository.deleteFolder(folder)
        }
    }

    fun addFolder(folder : Folder){
        viewModelScope.launch(Dispatchers.IO) {
            foldersRepository.addFolder(folder)
        }
    }

}