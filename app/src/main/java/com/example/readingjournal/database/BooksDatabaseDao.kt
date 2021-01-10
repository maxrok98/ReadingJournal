package com.example.readingjournal.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.readingjournal.models.Book

@Dao
interface BooksDatabaseDao {

    @Insert
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Query("DELETE FROM books_table")
    suspend fun clear()

    @Query("DELETE FROM books_table WHERE Id = :id")
    fun delete(id: Long)

    @Query("SELECT * from books_table WHERE Id = :key")
    fun get(key: Long): Book?

    @Query("SELECT * FROM books_table WHERE Author = :author")
    fun getByAuthor(author: String?) : List<Book>?

    @Query("SELECT * FROM books_table ORDER BY Id DESC")
    fun getAllBooks(): List<Book>?

    @Query("Select author from books_table group by author")
    fun getAllAuthors(): List<String>?
}