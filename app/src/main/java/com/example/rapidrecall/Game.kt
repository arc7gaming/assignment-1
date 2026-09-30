package com.example.rapidrecall

data class Attempt(val sequenceLength: Int, val targetSequence: List<Int>, val userInput: List<Int>, val correct: Boolean)

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

    private fun recordAttempt(sequenceLength: Int, targetSequence: List<Int>, userInput: List<Int>, correct: Boolean): Attempt {
        return Attempt(sequenceLength, targetSequence, userInput, correct)
    }

    private fun displaySummary(): Summary {
        return Summary(roundsPlayed, correctGuesses, (roundsPlayed.toDouble() / correctGuesses.toDouble()))
    }
}