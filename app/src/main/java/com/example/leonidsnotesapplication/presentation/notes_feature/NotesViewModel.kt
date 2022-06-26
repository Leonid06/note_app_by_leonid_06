package com.example.leonidsnotesapplication.presentation.notes_feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel  @Inject constructor (
    private val noteRepository: NoteRepository
) : ViewModel() {


    private val notesLiveDataMutable  = MutableLiveData<ArrayList<Note>>()
    val notesLiveData : LiveData<ArrayList<Note>> = notesLiveDataMutable

    private val notesSearchLiveDataMutable  = MutableLiveData<ArrayList<Note>>()
    val notesSearchLiveData : LiveData<ArrayList<Note>> = notesSearchLiveDataMutable

    private val currentFolderLiveDataMutable = MutableLiveData<Folder>()
    val currentFolderLiveData : LiveData<Folder> = currentFolderLiveDataMutable

    fun setFolder(folder : Folder){
        viewModelScope.launch(Dispatchers.IO) {
            currentFolderLiveDataMutable.postValue(folder)
            updateNotes(folder)
        }
    }

    fun searchNotes(query : String?){
        viewModelScope.launch(Dispatchers.IO) {
            notesSearchLiveDataMutable.postValue(noteRepository.searchNotesByFolder(query,currentFolderLiveData.value!!))
        }
    }

    fun addNote(note : Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
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
            notesLiveDataMutable.postValue(getNotesByFolder(folder))
        }
    }

//    private fun  getAllNotes(): ArrayList<Note> {
//        return noteRepository.getAllNotes()
//    }

    private fun getNotesByFolder(folder : Folder) : ArrayList<Note> {
        return noteRepository.getNotesByFolder(folder)
    }


}