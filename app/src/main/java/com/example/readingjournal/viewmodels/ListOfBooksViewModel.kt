package com.example.readingjournal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readingjournal.database.BooksDatabaseDao
import com.example.readingjournal.models.Book
import com.example.readingjournal.models.Notation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ListOfBooksViewModel(private val database: BooksDatabaseDao) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private  var _books = database.getAllBooks()
    val books: LiveData<List<Book>>
        get(){
            return _books
        }
}