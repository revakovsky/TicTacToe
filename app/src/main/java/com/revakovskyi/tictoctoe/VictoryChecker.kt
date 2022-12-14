package com.revakovskyi.tictoctoe

import android.widget.Button

class VictoryChecker(
    private val cellA1: Button,
    private val cellA2: Button,
    private val cellA3: Button,

    private val cellB1: Button,
    private val cellB2: Button,
    private val cellB3: Button,

    private val cellC1: Button,
    private val cellC2: Button,
    private val cellC3: Button
) {

    fun isVictory(symbol: String): Boolean {
        return horizontalCheck(symbol) ||
                verticalCheck(symbol) ||
                diagonalCheck(symbol)
    }

    private fun horizontalCheck(symbol: String): Boolean {
        return (match(cellA1, symbol) && match(cellA2, symbol) && match(cellA3, symbol)) ||
                (match(cellB1, symbol) && match(cellB2, symbol) && match(cellB3, symbol)) ||
                (match(cellC1, symbol) && match(cellC2, symbol) && match(cellC3, symbol))
    }

    private fun verticalCheck(symbol: String): Boolean {
        return (match(cellA1, symbol) && match(cellB1, symbol) && match(cellC1, symbol)) ||
                (match(cellA2, symbol) && match(cellB2, symbol) && match(cellC2, symbol)) ||
                (match(cellA3, symbol) && match(cellB3, symbol) && match(cellC3, symbol))
    }

    private fun diagonalCheck(symbol: String): Boolean {
        return (match(cellA1, symbol) && match(cellB2, symbol) && match(cellC3, symbol)) ||
                (match(cellA3, symbol) && match(cellB2, symbol) && match(cellC1, symbol))
    }

    private fun match(cell: Button, symbol: String): Boolean = cell.text == symbol
}