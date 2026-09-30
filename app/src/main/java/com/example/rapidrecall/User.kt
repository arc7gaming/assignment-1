package com.example.rapidrecall

class User {
    private fun chooseSequenceLength(input: String): Int {
        return input.toInt()
    }

    private fun enterSequence(guess: String): List<Int> {
        val guessSequence: List<Char> = guess.toList()
        return guessSequence.map { it.digitToInt() }
    }
}