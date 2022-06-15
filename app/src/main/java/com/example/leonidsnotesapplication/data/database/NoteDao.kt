package com.example.leonidsnotesapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.model.relations.FolderWithNotes
import dagger.Provides

@Dao
interface NoteDao {

    @Query("SELECT * FROM note WHERE title LIKE :query OR subtitle LIKE :query ORDER BY isStarred")
    fun selectNotes(query : String?) : List<Note>

    @Query("SELECT * FROM Note ORDER BY isStarred")
    fun getAllNotes() : List<Note>

    @Query("SELECT * FROM folder WHERE id = :id")
    fun getFolderWithNotes(id : Int) : List<FolderWithNotes>

    @Query("SELECT * FROM folder")
    fun getAllFolders() : List<Folder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)
}