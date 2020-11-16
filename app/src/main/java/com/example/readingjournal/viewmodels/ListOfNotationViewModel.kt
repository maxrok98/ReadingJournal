package com.example.readingjournal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.readingjournal.database.BooksDatabaseDao
import com.example.readingjournal.database.NotationsDatabaseDao
import com.example.readingjournal.models.Book
import com.example.readingjournal.models.Notation
import kotlinx.coroutines.*
import kotlin.random.Random

class ListOfNotationViewModel(private val bookId: Long = 0L,
                               private val database: NotationsDatabaseDao,
                              private  val bookSource: BooksDatabaseDao) : ViewModel(){

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book>
        get() = _book

    private var _notations = database.getAllFromBook(bookId)
    val notation: LiveData<List<Notation>>
        get() = _notations

    val clearButtonVisible = Transformations.map(_notations) {
        it?.isNotEmpty()
    }

    private var _showSnackbarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean>
        get() = _showSnackbarEvent
    fun doneShowingSnackbar() {
        _showSnackbarEvent.value = false
    }

    init {
        _book.value = bookSource.get(bookId)
        var nota = database.getOneFromBook(bookId)
        var a = 0
        a++
    }

    fun onClear(){
        uiScope.launch {
            clear()
        }
        _showSnackbarEvent.value = true
    }

    private suspend fun clear() {
        database.clearFromBook(bookId)
    }

    private suspend fun update(notation: Notation) {
        database.update(notation)
    }

    private suspend fun insert(notation: Notation) {
        database.insert(notation)
    }

    fun addNotation(title: String, text: String){
        uiScope.launch {
            insert(Notation(Id = Random.nextLong(), title = title, text = text, bookId = bookId))
        }
    }

}