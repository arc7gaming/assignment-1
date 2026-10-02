package com.example.rapidrecall

data class Attempt(val sequenceLength: Int = 0, val targetSequence: List<Int> = listOf(), val userInput: List<Int> = listOf(), val correct: Boolean = false)

data class Summary(val totalAttempts: Int, val totalCorrectAttempts: Int, val accuracy: Double)

class Game {
    private val user: User = User()
    private var roundsPlayed: Int = 0
    private var correctGuesses: Int = 0

    private fun addRound() {
        roundsPlayed++
    }

    private fun getRounds(): Int {
        return roundsPlayed
    }

    private fun showDigit(sequence: List<Int>, index: Int): Int {
        return sequence[index]
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
            accuracy = (roundsPlayed.toDouble() / correctGuesses.toDouble())
        return Summary(roundsPlayed, correctGuesses, accuracy)
    }
}