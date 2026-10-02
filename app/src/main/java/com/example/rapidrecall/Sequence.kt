package com.example.rapidrecall

class Sequence(val length: Int) {
    private var sequence: List<Int> = listOf()

    fun createSequence() {
        sequence = List(length) { (0..9).random() }
    }

    fun getSequence(): List<Int> {
        return sequence
    }
}