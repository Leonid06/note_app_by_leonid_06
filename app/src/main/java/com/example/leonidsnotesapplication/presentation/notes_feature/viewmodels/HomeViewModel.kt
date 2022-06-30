package com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels

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
class HomeViewModel @Inject constructor (
    private val noteRepository : NoteRepository
) : ViewModel() {


    private val _notesLiveData : MutableLiveData<ArrayList<Note>> = MutableLiveData<ArrayList<Note>>()
    val notesLiveData : LiveData<ArrayList<Note>> = _notesLiveData

    fun updateNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            _notesLiveData.postValue(noteRepository.getAllNotes())
        }
    }

    fun searchNotes(query : String){
        viewModelScope.launch(Dispatchers.IO){
            if(query == ""){
                _notesLiveData.postValue(noteRepository.getAllNotes())
            }else{
                _notesLiveData.postValue(noteRepository.searchAllNotes(query))
            }
        }
    }

    fun addNote(note : Note){
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.insertNote(note, false)
        }
    }
}