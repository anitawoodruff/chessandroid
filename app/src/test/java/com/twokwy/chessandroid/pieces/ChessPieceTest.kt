package com.twokwy.chessandroid.pieces

import com.twokwy.chessandroid.board.Position
import org.junit.Assert.assertEquals
import org.junit.Test

class ChessPieceTest {

    @Test
    fun isLegalMove_knightIllegalMove_returnsFalse() {
        val piece = ChessPiece(ChessPieceType.KNIGHT, PieceColor.WHITE)
        val origin = Position(0, 0)
        val destination = Position(5, 5)
        assertEquals(false, piece.isLegalMove(origin, destination))
    }

    @Test
    fun isLegalMove_knightLegalMove_returnsTrue() {
        val piece = ChessPiece(ChessPieceType.KNIGHT, PieceColor.WHITE)
        val origin = Position(0, 0)
        val destination = Position(1, 2)
        assertEquals(true, piece.isLegalMove(origin, destination))
    }
}