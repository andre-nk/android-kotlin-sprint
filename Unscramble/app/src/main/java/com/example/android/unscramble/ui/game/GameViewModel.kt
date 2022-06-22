package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private var wordsList: MutableList<String>
    private var _score = MutableLiveData(0)
    private var _currentWordCount = MutableLiveData(0)
    private val _currentScrambledWord = MutableLiveData<String>()

    private lateinit var currentWord: String

    val score: LiveData<Int>
        get() = _score

    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    init {
        Log.d("GameFragment", "GameVM Created!")
        wordsList =  mutableListOf()
        getNextWord()
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val nextWordSequence = currentWord.toCharArray()
        nextWordSequence.shuffle()

        while (String(nextWordSequence).equals(currentWord, false)) {
            nextWordSequence.shuffle()
        }

        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(nextWordSequence)
            (_currentWordCount.value)?.inc()
            Log.d("Current Word Value", _currentWordCount.value.toString())
            wordsList.add(currentWord)
        }
    }

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun validateAnswer(playerAnswer: String) : Boolean {
        if(playerAnswer.equals(currentWord, true)){
            increaseScore()
            return true
        }

        return false
    }

    fun nextWord() : Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else {
            false
        }
    }

    fun reinitialiseData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }
}