package com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.example.leonidsnotesapplication.domain.model.NoteViewData
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val noteRepository : NoteRepository
) : ViewModel() {


    private val _notesLiveData  = MutableLiveData<List<Note>>()
    val notesLiveData : LiveData<List<Note>> = _notesLiveData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            noteRepository.getAllNotes().collect{
                Log.d("livedata", "Collected: $it")
                _notesLiveData.postValue(it)
            }
        }
    }

    fun searchNotes(query : String){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.searchAllNotes(query).collect{
                _notesLiveData.postValue(it)
            }
        }
    }

    fun deleteNote(note : Note){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.deleteNote(note)
        }
    }

    fun updateNoteChecked(note : Note){
        viewModelScope.launch(Dispatchers.IO){
            noteRepository.updateNoteChecked(note.id, note.isStarred)
        }
    }


//    fun convertDataToNote(data : NoteViewData) : Note{
//        val job = viewModelScope.launch(Dispatchers.IO){
//            noteRepository.getNoteById(data.id)
//            updateNotes()
//        }
//
//        return
//    }

//    private fun reformatToViewData(notes : ArrayList<Note>) : ArrayList<NoteViewData>{
//        val viewData = arrayListOf<NoteViewData>()
//        notes.forEach { note ->
//            val item : NoteViewData = if(note.folderId == -1){
//                NoteViewData(
//                    note.id,note.title!!, note.subtitle!!,note.isStarred,"", true
//                )
//            }else{
//                NoteViewData(note.id, note.title!!, note.subtitle!!, note.isStarred, "", false)
//            }
//
//            viewData.add(item)
//        }
//        return  viewData
//    }
}