package com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leonidsnotesapplication.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NoteSharedViewModel @Inject constructor(

): ViewModel() {

    private val _selectedNote : MutableLiveData<Note> = MutableLiveData<Note>()

    val selectedNote : LiveData<Note> get()= _selectedNote

    private val _deleteNote : MutableLiveData<Note> = MutableLiveData<Note>()

    val deleteNote: LiveData<Note> get()= _deleteNote

    fun selectNote(note : Note){
        _selectedNote.value = note
    }
    fun selectDeleteNote(note: Note){
        _deleteNote.value = note
    }
}