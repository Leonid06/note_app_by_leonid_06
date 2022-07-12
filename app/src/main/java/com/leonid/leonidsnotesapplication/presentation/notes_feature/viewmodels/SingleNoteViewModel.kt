package com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.domain.model.Note
import com.leonid.leonidsnotesapplication.domain.repository.FoldersRepository
import com.leonid.leonidsnotesapplication.domain.repository.NoteRepository
import com.leonid.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleNoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val foldersRepository: FoldersRepository,
) : ViewModel(){

    private val _foldersLiveData : MutableLiveData<ArrayList<Folder>> = MutableLiveData()

    val foldersLiveData : LiveData<ArrayList<Folder>> = _foldersLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            foldersRepository.getAllFolders(SortOption.ByDate).cancellable().collect{
                _foldersLiveData.postValue(it as ArrayList<Folder>)
            }
        }
    }

    fun changeNoteFolder(note : Note, folder : Folder){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.changeNoteFolder(note, folder)
        }
    }

    fun addNote(note : Note, isNew : Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note, isNew)
        }
    }


}