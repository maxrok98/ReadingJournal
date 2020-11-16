package com.example.readingjournal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.readingjournal.models.Book
import com.example.readingjournal.models.Notation

@Dao
interface NotationsDatabaseDao {
    @Insert
    fun insert(notation: Notation)

    @Update
    suspend fun update(notation: Notation)

    @Query("DELETE FROM notation_table")
    suspend fun clear()

    @Query("DELETE FROM notation_table where bookId = :key")
    suspend fun clearFromBook(key: Long)

    @Query("SELECT * from notation_table WHERE Id = :key")
    suspend fun get(key: Long): Notation?

    @Query("SELECT * from notation_table WHERE bookId = :key")
    fun getAllFromBook(key: Long): LiveData<List<Notation>>

    @Query("SELECT * from notation_table WHERE bookId = :key LIMIT 1")
    fun getOneFromBook(key: Long): Notation?

    @Query("SELECT * FROM notation_table ORDER BY Id DESC")
    fun getAllNotation(): LiveData<List<Notation>>
}