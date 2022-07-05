package com.example.leonidsnotesapplication.presentation.notes_feature.viewmodels

import androidx.lifecycle.*
import com.example.leonidsnotesapplication.domain.model.NoteViewData
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.repository.FoldersRepository
import com.example.leonidsnotesapplication.domain.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val noteRepository : NoteRepository
) : ViewModel() {


    private var _notesLiveData : LiveData<List<Note>> = noteRepository.getAllNotes().asLiveData()
    val notesLiveData  get()= _notesLiveData



    fun searchNotes(query : String){
        viewModelScope.launch(Dispatchers.IO){
            _notesLiveData = noteRepository.searchAllNotes(query).asLiveData()
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