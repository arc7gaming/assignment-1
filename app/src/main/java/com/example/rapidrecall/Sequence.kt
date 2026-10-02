package com.example.rapidrecall

/**
 * Allows for creation of sequence with specified length.
 * Sequence creation is handled in public method.
 * Public method allows sequence to be accessed in list type.
 */
class Sequence(private val length: Int) {
    private var sequence: List<Int> = listOf()

    fun createSequence() {
        sequence = List(length) { (0..9).random() }
    }

    fun getSequence(): List<Int> {
        return sequence
    }
}