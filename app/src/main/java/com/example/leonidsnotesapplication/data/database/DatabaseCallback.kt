package com.example.leonidsnotesapplication.data.database

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class DatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        val values = ContentValues()
        values.put("title" , "default folder")
        values.put("id", -1)
        db.insert("Folder", OnConflictStrategy.REPLACE, values)
        super.onCreate(db)
    }
}