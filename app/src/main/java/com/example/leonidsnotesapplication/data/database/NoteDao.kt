package com.example.leonidsnotesapplication.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.leonidsnotesapplication.domain.model.Folder
import com.example.leonidsnotesapplication.domain.model.Note
import com.example.leonidsnotesapplication.domain.model.relations.FolderWithNotes
import dagger.Provides

@Dao
interface NoteDao {

    @Delete
    fun deleteFolder(folder: Folder)

    @Query("UPDATE Folder SET title = :title WHERE id = :id")
    fun updateFolderTitle(id : Int, title : String)

    @Query("SELECT * FROM Folder WHERE id = :id")
    fun getFolderWithNotesByFolderId(id : Int) : FolderWithNotes

    @Query("SELECT * FROM Note WHERE folderId = :id ORDER BY isStarred")
    fun getNotesByFolderId(id : Int) : List<Note>

    @Query("SELECT * FROM note WHERE content LIKE :query ORDER BY isStarred")
    fun searchAllNotes(query : String?) : List<Note>

    @Query("SELECT * FROM note WHERE folderId = :id AND content LIKE :query ORDER BY isStarred" )
    fun searchNotesByFolderId(query : String?, id : Int) : List<Note>

    @Query("SELECT * FROM Note ORDER BY isStarred")
    fun getAllNotes() : List<Note>

    @Query("SELECT * FROM  Folder WHERE id = :id")
    fun getFolderWithNotes(id : Int) : List<FolderWithNotes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolder(folder : Folder)

    @Query("SELECT * FROM Folder")
    fun getAllFolders() : List<Folder>

    @Query("UPDATE folder SET noteCount = noteCount + 1 WHERE id = :id")
    fun updateOnInsertNote(id : Int)

    @Query("UPDATE folder SET noteCount = noteCount - 1 WHERE id = :id")
    fun updateOnDeleteNote(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : Note)

    @Delete
    suspend fun deleteNote(note : Note)
}