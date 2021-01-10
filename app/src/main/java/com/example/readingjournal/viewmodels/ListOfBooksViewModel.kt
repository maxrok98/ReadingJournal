package com.example.readingjournal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readingjournal.database.BooksDatabaseDao
import com.example.readingjournal.models.Book
import com.example.readingjournal.models.Notation
import kotlinx.coroutines.*
import java.io.IOException

class ListOfBooksViewModel(private val database: BooksDatabaseDao) : ViewModel(){

    private var filter = FilterHolder()
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    private var currentJob: Job? = null

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private var _authorList = MutableLiveData<List<String>>()
    val authorList: LiveData<List<String>>
    get() = _authorList


    private var _books = MutableLiveData<List<Book>>()
    val books: LiveData<List<Book>>
    get() = _books
    //val books: LiveData<List<Book>>
    //    get(){
    //        return _books
    //    }

    init{
        onQueryChanged()
    }

    private fun onQueryChanged() {
        currentJob?.cancel() // if a previous query is running cancel it before starting another
        currentJob = viewModelScope.launch {
            try {
                // this will run on a thread managed by Retrofit
                if(filter.currentValue != null){
                    _books.value = database.getByAuthor(filter.currentValue)
                }
                else{
                    // TODO: somehow get live data from dV
                    _books.value = database.getAllBooks()
                }
                database.getAllAuthors().let { it ->
                    // only update the filters list if it's changed since the last time
                    if (it != _authorList.value) {
                        _authorList.value = it
                    }
                }
            } catch (e: IOException) {
                _books.value = listOf()
            }
        }
        //_books.value = database.getByAuthor(filter.currentValue)
        //database.getAllAuthors().let { it ->
        //    // only update the filters list if it's changed since the last time
        //    if (it != _authorList.value) {
        //        _authorList.value = it
        //    }
        //}
        }

        fun onFilterChanged(filter: String, isChecked: Boolean) {
        if (this.filter.update(filter, isChecked)) {
            onQueryChanged()
        }
    }

    private class FilterHolder {
        var currentValue: String? = null
            private set

        fun update(changedFilter: String, isChecked: Boolean): Boolean {
            if (isChecked) {
                currentValue = changedFilter
                return true
            } else if (currentValue == changedFilter) {
                currentValue = null
                return true
            }
            return false
        }
    }
}