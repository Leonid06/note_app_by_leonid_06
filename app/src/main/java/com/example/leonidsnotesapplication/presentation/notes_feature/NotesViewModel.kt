package com.example.leonidsnotesapplication.presentation.notes_feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.usecase.AddNoteUseCase
import com.example.leonidsnotesapplication.domain.usecase.DeleteNoteUseCase
import com.example.leonidsnotesapplication.domain.usecase.GetAllNotesUseCase
import com.example.leonidsnotesapplication.domain.usecase.SearchNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel  @Inject constructor (
    private val addNoteUseCase: AddNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val searchNoteUseCase: SearchNoteUseCase
) : ViewModel() {

    private val notesLiveDataMutable  = MutableLiveData<ArrayList<Note>>()
    val notesLiveData : LiveData<ArrayList<Note>> = notesLiveDataMutable

    private val notesSearchLiveDataMutable  = MutableLiveData<ArrayList<Note>>()
    val notesSearchLiveData : LiveData<ArrayList<Note>> = notesSearchLiveDataMutable


    fun searchNotes(query : String?){
        viewModelScope.launch(Dispatchers.IO) {
            notesSearchLiveDataMutable.postValue(searchNoteUseCase.execute(query))
        }
    }

    fun addNote(note : Note){
        viewModelScope.launch(Dispatchers.IO) {
            addNoteUseCase.execute(note)
            updateNotes()
        }
    }

    fun deleteNote(note : Note){

        viewModelScope.launch(Dispatchers.IO) {
            deleteNoteUseCase.execute(note)
            updateNotes()
        }
    }


   fun updateNotes(){
        viewModelScope.launch(Dispatchers.IO) {
            notesLiveDataMutable.postValue(getAllNotes())
        }
    }

    private fun  getAllNotes(): ArrayList<Note> {
        return getAllNotesUseCase.execute()
    }


}