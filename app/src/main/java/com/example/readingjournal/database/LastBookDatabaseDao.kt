package com.example.readingjournal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.readingjournal.models.LastPublishedBook

@Dao
interface LastBookDatabaseDao {
    @Query("select * from last_book_table Limit 1")
    fun getLastBook(): LiveData<LastPublishedBook>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLastBook(book: LastPublishedBook)
}