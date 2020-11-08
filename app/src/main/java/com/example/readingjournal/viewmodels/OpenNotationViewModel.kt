package com.example.readingjournal.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readingjournal.models.Notation

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class OpenNotationViewModel(notation: Notation): ViewModel(){

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
    private val _notation = MutableLiveData<Notation>()
    val notation: LiveData<Notation>
        get() = _notation

    private val _likesCount = MutableLiveData<Int>()
    val likesCount: LiveData<Int>
        get() = _likesCount

    private val _eventBuzz = MutableLiveData<BuzzType>()
    val eventBuzz: LiveData<BuzzType>
        get() = _eventBuzz

    init {
        _notation.value = notation
        _likesCount.value = 0
    }

    fun like(){
        _likesCount.value = (_likesCount.value)?.plus(1)
        if(_likesCount.value!! % 10 == 0)
            _eventBuzz.value = BuzzType.CORRECT
    }

    fun onBuzzComplete() {
        _eventBuzz.value = BuzzType.NO_BUZZ
    }
}