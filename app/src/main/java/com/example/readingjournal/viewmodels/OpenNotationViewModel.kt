package com.example.readingjournal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readingjournal.database.NotationsDatabaseDao
import com.example.readingjournal.models.Notation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class OpenNotationViewModel(private val notationId: Long,
                            private  val database: NotationsDatabaseDao): ViewModel(){

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    companion object {
        // These represent different important times in the game, such as game length.

        // This is when the game is over
        private const val DONE = 0L

        // This is the time when the phone will start buzzing each second
        private const val COUNTDOWN_PANIC_SECONDS = 10L

        // This is the number of milliseconds in a second
        private const val ONE_SECOND = 1000L

        // This is the total time of the game
        private const val COUNTDOWN_TIME = 60000L

    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _notation = MutableLiveData<Notation>()
    val notation: LiveData<Notation>
        get() = _notation


    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    init {
        initializeNotation()
        //_likesCount.value = 0
    }

    private fun initializeNotation() {
        uiScope.launch {
            _notation.value = getNotationFromDb()
        }
    }

    private suspend fun getNotationFromDb():  Notation? {
        return database.get(notationId)
    }

    private suspend fun clear() {
        database.clear()
    }

    private suspend fun update(notation: Notation) {
        database.update(notation)
    }

    private suspend fun insert(notation: Notation) {
        database.insert(notation)
    }

    fun addNotation(title: String, text: String){
        uiScope.launch {
            val notation = Notation(title = title, text = text)
            insert(notation)
        }
    }

    fun like(){
        uiScope.launch {
            val not = database.get(notationId)
            if (not != null) {
                not?.likes = not?.likes?.plus(1)!!
                update(not)
                initializeNotation()
                if(not?.likes?.rem(10).equals(0L)) {
                    _eventBuzz.value = BuzzType.CORRECT
                }
            }
        }
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }
}