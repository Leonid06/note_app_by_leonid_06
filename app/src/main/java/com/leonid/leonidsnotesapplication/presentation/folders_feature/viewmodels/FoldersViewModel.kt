package com.leonid.leonidsnotesapplication.presentation.folders_feature.viewmodels

import androidx.lifecycle.*
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.domain.repository.FoldersRepository
import com.leonid.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoldersViewModel @Inject constructor(
    private val foldersRepository: FoldersRepository
) : ViewModel(){



    private var _foldersLiveData : MutableLiveData<ArrayList<Folder>> = MutableLiveData<ArrayList<Folder>>()

    val foldersLiveData : LiveData<ArrayList<Folder>> =  _foldersLiveData

    private var currentJob  : Job = Job()


   fun sortAllFolders(option: SortOption){
        currentJob.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO){
            foldersRepository.getAllFolders(option).cancellable().collect{
                _foldersLiveData.postValue(it as ArrayList<Folder>)
            }
        }
    }

    fun searchAllFolders(query: String, option: SortOption){
        currentJob.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO){
            foldersRepository.searchAllFolders(query, option).cancellable().collect{
                _foldersLiveData.postValue(it as ArrayList<Folder>)
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