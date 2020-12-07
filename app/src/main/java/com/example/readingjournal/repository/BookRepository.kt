package com.example.readingjournal.repository

import androidx.lifecycle.LiveData
import com.example.readingjournal.database.BooksDatabase
import com.example.readingjournal.models.LastPublishedBook
import com.example.readingjournal.network.BookApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepository(private val database: BooksDatabase) {
    val LastBook: LiveData<LastPublishedBook> = database.lastBookDatabaseDao.getLastBook()

    suspend fun refreshBook(){
        withContext(Dispatchers.IO){
            val lastBook = BookApi.retrofitService.getLastScienceBook()
            database.lastBookDatabaseDao.insertLastBook(lastBook.asLastBookDataBaseModel())
        }
    }
}