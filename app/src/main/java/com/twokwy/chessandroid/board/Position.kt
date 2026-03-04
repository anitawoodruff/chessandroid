package com.twokwy.chessandroid.board

data class Position(val x: Int, val y: Int) {

    private val FILES = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
    private val RANKS = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)

    override fun toString(): String {
        val file = FILES[x]
        val rank = RANKS[7 - y]
        return "$file$rank"
    }
}