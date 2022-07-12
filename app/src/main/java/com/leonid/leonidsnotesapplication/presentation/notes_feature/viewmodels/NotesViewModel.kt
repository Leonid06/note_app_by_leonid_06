package com.leonid.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.*
import com.leonid.leonidsnotesapplication.domain.model.Folder
import com.leonid.leonidsnotesapplication.domain.model.Note
import com.leonid.leonidsnotesapplication.domain.repository.FoldersRepository
import com.leonid.leonidsnotesapplication.domain.repository.NoteRepository
import com.leonid.leonidsnotesapplication.presentation.notes_feature.util.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel  @Inject constructor (
    private val noteRepository: NoteRepository,
    private val foldersRepository : FoldersRepository
) : ViewModel() {

    private val _notesLiveData  = MutableLiveData<ArrayList<Note>>()
    val notesLiveData : LiveData<ArrayList<Note>> = _notesLiveData

    var currentJob : Job  = Job()
    
    fun searchNotes(query : String?, folder: Folder, option: SortOption){
        currentJob.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO) {
           noteRepository.searchNotesByFolder(query, folder, option).cancellable().collect{
               _notesLiveData.postValue(it as ArrayList<Note>)
           }
        }
    }

    fun updateNotesByFolder(option : SortOption, folder : Folder){
        currentJob.cancel()
        currentJob = viewModelScope.launch(Dispatchers.IO){
            noteRepository.getNotesByFolder(folder, option).cancellable().collect{
                _notesLiveData.postValue(it as ArrayList<Note>)
            }
        }
    }

    fun addNote(note : Note, isNew : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note, isNew)
        }
    }

    fun deleteNote(note : Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
        }
    }

    fun updateFolderTitle(title : String, folder : Folder){
        viewModelScope.launch(Dispatchers.IO){
            foldersRepository.updateFolderTitle(folder, title)
        }
    }


}