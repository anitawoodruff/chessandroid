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

    @Test
    fun isLegalMove_pawnForwardOne_returnsTrue() {
        val piece = ChessPiece(ChessPieceType.PAWN, PieceColor.WHITE)
        val origin = Position(3, 1)
        val destination = Position(3, 2)
        assertEquals(true, piece.isLegalMove(origin, destination))
    }

    @Test
    fun isLegalMove_pawnForwardTwoRightOne_returnsFalse() {
        val piece = ChessPiece(ChessPieceType.PAWN, PieceColor.WHITE)
        val origin = Position(3, 1)
        val destination = Position(4, 3)
        assertEquals(false, piece.isLegalMove(origin, destination))
    }

    @Test
    fun isLegalMove_whitePawnUpTwoFromSecondRank_returnsTrue() {
        val piece = ChessPiece(ChessPieceType.PAWN, PieceColor.WHITE)
        val origin = Position(3, 1)
        val destination = Position(3, 3)
        assertEquals(true, piece.isLegalMove(origin, destination))
    }

    @Test
    fun isLegalMove_blackPawnUpTwoFromSecondRank_returnsFalse() {
        val piece = ChessPiece(ChessPieceType.PAWN, PieceColor.BLACK)
        val origin = Position(3, 1)
        val destination = Position(3, 3)
        assertEquals(false, piece.isLegalMove(origin, destination))
    }

    @Test
    fun isLegalMove_blackPawnDownTwoFromSeventhRank_returnsTrue() {
        val piece = ChessPiece(ChessPieceType.PAWN, PieceColor.BLACK)
        val origin = Position(3, 6)
        val destination = Position(3, 4)
        assertEquals(true, piece.isLegalMove(origin, destination))
    }

    // TODO test & implement pawn moves that take other pieces
}