package com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.*
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel  @Inject constructor (
    private val noteRepository: NoteRepository,
    private val foldersRepository : FoldersRepository
) : ViewModel() {

    private val _notesLiveData  = MutableLiveData<List<Note>>()
    val notesLiveData : LiveData<List<Note>> = _notesLiveData

//    init {
//        viewModelScope.launch(Dispatchers.IO){
//            noteRepository.getAllNotes().collect{
//                _notesLiveData.postValue(it)
//            }
//        }
//    }
    fun searchNotes(query : String?, folder: Folder){
        viewModelScope.launch(Dispatchers.IO) {
           noteRepository.searchNotesByFolder(query, folder).collect{
               _notesLiveData.postValue(it)
           }
        }
    }

    fun updateNotesByFolder(folder : Folder){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.getNotesByFolder(folder).collect{
                _notesLiveData.postValue(it)
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