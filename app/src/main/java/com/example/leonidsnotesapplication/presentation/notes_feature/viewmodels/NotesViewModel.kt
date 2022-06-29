package com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel  @Inject constructor (
    private val noteRepository: NoteRepository,
    private val foldersRepository : FoldersRepository
) : ViewModel() {


    private val _notesLiveData  = MutableLiveData<ArrayList<Note>>()
    val notesLiveData : LiveData<ArrayList<Note>> = _notesLiveData

    private val _notesSearchLiveData  = MutableLiveData<ArrayList<Note>>()
    val notesSearchLiveData : LiveData<ArrayList<Note>> = _notesSearchLiveData

    private val _currentFolderLiveData = MutableLiveData<Folder>()
    val currentFolderLiveData : LiveData<Folder> = _currentFolderLiveData

    fun setFolder(folder : Folder){
        viewModelScope.launch(Dispatchers.IO) {
            _currentFolderLiveData.postValue(folder)
            updateNotes(folder)
        }
    }

    fun searchNotes(query : String?){
        viewModelScope.launch(Dispatchers.IO) {
            _notesSearchLiveData.postValue(noteRepository.searchNotesByFolder(query,currentFolderLiveData.value!!))
        }
    }

    fun addNote(note : Note, isNew : Boolean){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note, isNew)
            currentFolderLiveData.value?.let { updateNotes(it) }
        }
    }

    fun deleteNote(note : Note){

        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
            currentFolderLiveData.value?.let { updateNotes(it) }
        }
    }


    private fun updateNotes(folder: Folder) {
        viewModelScope.launch(Dispatchers.IO){
            _notesLiveData.postValue(getNotesByFolder(folder))
        }
    }

    fun updateFolderTitle(title : String){
        viewModelScope.launch(Dispatchers.IO){
            foldersRepository.updateFolderTitle(currentFolderLiveData.value!!, title)
        }
    }

//    private fun  getAllNotes(): ArrayList<Note> {
//        return noteRepository.getAllNotes()
//    }

    private fun getNotesByFolder(folder : Folder) : ArrayList<Note> {
        return noteRepository.getNotesByFolder(folder)
    }


}