package com.twokwy.chessandroid.board

import android.graphics.drawable.ShapeDrawable
import com.twokwy.chessandroid.pieces.ChessPiece
import com.twokwy.chessandroid.pieces.ChessPieceType
import com.twokwy.chessandroid.pieces.PieceColor
import org.junit.Assert.assertEquals
import org.junit.Test


class ChessSquareTest {

    @Test
    fun pieceCanReach_knightIllegalMove_returnsFalse() {
        val origin = knightAt(Position(0, 0))
        val destination = emptySquareAt(Position(1, 1))
        assertEquals(false, origin.pieceCanReach(destination))
    }

    @Test
    fun pieceCanReach_knight_rightTwoUpOne_returnsTrue() {
        val origin = knightAt(Position(0, 0))
        val destination = emptySquareAt(Position(2, 1))
        assertEquals(true, origin.pieceCanReach(destination))
    }

    @Test
    fun pieceCanReach_knight_rightOneUpTwo_returnsTrue() {
        val origin = knightAt(Position(0, 0))
        val destination = emptySquareAt(Position(1, 2))
        assertEquals(true, origin.pieceCanReach(destination))
    }

    @Test
    fun pieceCanReach_knight_rightTwoDownOne_returnsTrue() {
        val origin = knightAt(Position(0, 1))
        val destination = emptySquareAt(Position(2, 0))
        assertEquals(true, origin.pieceCanReach(destination))
    }

    @Test
    fun pieceCanReach_knight_rightOneDownTwo_returnsTrue() {
        val origin = knightAt(Position(0, 2))
        val destination = emptySquareAt(Position(1, 0))
        assertEquals(true, origin.pieceCanReach(destination))
    }

    @Test
    fun pieceCanReach_knight_leftOneUpTwo_returnsTrue() {
        val origin = knightAt(Position(1, 0))
        val destination = emptySquareAt(Position(0, 2))
        assertEquals(true, origin.pieceCanReach(destination))
    }

    @Test
    fun pieceCanReach_knight_leftOneDownTwo_returnsTrue() {
        val origin = knightAt(Position(1, 2))
        val destination = emptySquareAt(Position(0, 0))
        assertEquals(true, origin.pieceCanReach(destination))
    }

    companion object {
        fun knightAt(position: Position) = ChessSquare(
            position, ShapeDrawable(), ChessPiece(
                ChessPieceType.KNIGHT, PieceColor.BLACK
            )
        )

        fun emptySquareAt(position: Position) = ChessSquare(position, ShapeDrawable(), null)
    }
}