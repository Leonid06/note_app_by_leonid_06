package com.example.leonidsnotesapplication.presentation.notes_feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun searchNotes(query : String?){
        viewModelScope.launch(Dispatchers.IO) {
            notesSearchLiveDataMutable.postValue(noteRepository.searchNotes(query))
        }
    }

    fun addNote(note : Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note)
            updateNotes()
        }
    }

    fun deleteNote(note : Note){

        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.deleteNote(note)
            updateNotes()
        }
    }


   fun updateNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            notesLiveDataMutable.postValue(getAllNotes())
        }
    }

    private fun  getAllNotes(): ArrayList<Note> {
        return noteRepository.getAllNotes()
    }


}