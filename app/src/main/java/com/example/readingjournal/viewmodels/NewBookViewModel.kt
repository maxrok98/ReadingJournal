package com.example.readingjournal.viewmodels

import androidx.lifecycle.*
import com.example.readingjournal.database.BooksDatabaseDao
import com.example.readingjournal.models.Book
import com.example.readingjournal.network.BookProperty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.readingjournal.network.BookApi
import com.example.readingjournal.network.BookInfo
import com.example.readingjournal.network.BookWrapper
import javax.security.auth.callback.Callback

class NewBookViewModel(private val database: BooksDatabaseDao): ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
    private val _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status
    private val _properties = MutableLiveData<BookWrapper>()
    val property: LiveData<BookWrapper>
            get() = _properties

    val title = Transformations.map(_properties){
        it.items[0].info.title
    }
    val author = Transformations.map(_properties){
        it.items[0].info.authors[0]
    }
    val thumbURL = Transformations.map(_properties){
        it.items[0].info.imgSrcUrl.thumbnail
    }

    lateinit var book: BookProperty

    fun getBookFromApi(isbn: String){
        viewModelScope.launch {
            _status.value = "Loading"
            try {
                _properties.value = BookApi.retrofitService.getProperties(isbn = "isbn:" + isbn)
                _status.value = "Done"
            } catch (e: Exception) {
                _status.value = "Error$e"
                //book = BookProperty()
            }
        }
    }

    fun addBook(author: String, title: String) {
        if(author != null && title != null){
            uiScope.launch {
                var thumb: String? = null
                if(_properties.value != null)
                    thumb = _properties.value!!.items[0].info.imgSrcUrl.thumbnail
                addBooksToDb(Book(Author = author, Title = title, thumbnailURL = thumb))
            }
        }
    }

    private suspend fun addBooksToDb(book: Book){
        return database.insert(book)
    }
}