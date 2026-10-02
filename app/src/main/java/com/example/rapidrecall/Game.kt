package com.example.rapidrecall

/**
 * Data classes which are used to store data and don't require methods.
 * Values can be accessed using the dot operator on an instance.
 */
data class Attempt(val sequenceLength: Int = 0, val targetSequence: List<Int> = listOf(), val userInput: List<Int> = listOf(), val correct: Boolean = false)

data class Summary(val totalAttempts: Int, val totalCorrectAttempts: Int, val accuracy: Double)

/**
 * Handles each round being played and generating reports.
 * Accessed through public methods to record attempts and get the summary.
 * Uses private methods and variables to handle logic internally.
 */
class Game {
    private var roundsPlayed: Int = 0
    private var correctGuesses: Int = 0

    private fun addRound() {
        roundsPlayed++
    }

    private fun correctGuess(sequence: List<Int>, guess: List<Int>): Boolean {
        val correct: Boolean = sequence == guess
        if (correct) correctGuesses++
        return correct
    }

    fun recordAttempt(targetSequence: List<Int>, userInput: List<Int>): Attempt {
        addRound()
        return Attempt(targetSequence.size, targetSequence, userInput, correctGuess(targetSequence, userInput))
    }

    fun displaySummary(): Summary {
        var accuracy = 0.0
        if (roundsPlayed > 0)
            accuracy = (correctGuesses.toDouble() / roundsPlayed.toDouble())
        return Summary(roundsPlayed, correctGuesses, accuracy)
    }
}