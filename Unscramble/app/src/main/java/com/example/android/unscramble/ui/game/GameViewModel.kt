package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private lateinit var wordsList: MutableList<String>

    init {
        Log.d("GameFragment", "GameVM Created!")
        wordsList =  mutableListOf()
        getNextWord()
    }

    private var _score = 0
    private var _currentWordCount = 0

    private lateinit var _currentScrambledWord: String
    private lateinit var currentWord: String

    val score: Int
        get() = _score

    val currentScrambledWord: String
        get() = _currentScrambledWord

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameVM Destroyed!")
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
            _currentScrambledWord = String(nextWordSequence)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    fun validateAnswer(playerAnswer: String) : Boolean {
        if(playerAnswer.equals(currentWord, true)){
            increaseScore()
            return true
        }

        return false
    }

    fun nextWord() : Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else {
            false
        }
    }

    fun reinitialiseData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
}