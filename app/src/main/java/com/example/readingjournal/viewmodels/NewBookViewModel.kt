package com.example.readingjournal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readingjournal.database.BooksDatabaseDao
import com.example.readingjournal.models.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NewBookViewModel(private val database: BooksDatabaseDao): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun addBook(author: String, title: String) {
        uiScope.launch {
            addBooksToDb(Book(Author = author, Title = title))
        }
    }

    private suspend fun addBooksToDb(book: Book){
        return database.insert(book)
    }
}