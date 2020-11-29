package com.example.readingjournal.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.example.readingjournal.database.BooksDatabase
import com.example.readingjournal.repository.BookRepository
import kotlinx.coroutines.launch

class OpeningViewModel(application: Application) : AndroidViewModel(application) {
    private val database = BooksDatabase.getInstance(application)
    private val repository = BookRepository(database)

    var lastBook = repository.LastBook

    //val lastBookTitle = Transformations.map(lastBook){
    //    it.Title
    //}

    //val lastBookAuthor = Transformations.map(lastBook){
    //    it.Author
    //}

    //val lastBookDate = Transformations.map(lastBook){
    //    it.publishDate
    //}

    init{
        viewModelScope.launch {
            repository.refreshBook()
        }
    }
}