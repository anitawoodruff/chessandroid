package com.twokwy.chessandroid.pieces

import com.twokwy.chessandroid.board.Position
import kotlin.math.abs

class ChessPiece(val pieceType: ChessPieceType, val color: PieceColor) {
    fun isLegalMove(origin: Position, destination: Position): Boolean {
        return when (pieceType) {
            ChessPieceType.KNIGHT -> {
                isLegalKnightMove(origin, destination)
            }
            else -> {
                false
            }
        }
    }

    private fun isLegalKnightMove(
        origin: Position,
        destination: Position
    ): Boolean {
        val xDiff = abs(destination.x - origin.x)
        val yDiff = abs(destination.y - origin.y)
        return when (xDiff) {
            2 -> yDiff == 1
            1 -> yDiff == 2
            else -> false
        }
    }
}
