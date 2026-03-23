package com.twokwy.chessandroid.pieces

import com.twokwy.chessandroid.board.Position
import com.twokwy.chessandroid.pieces.ChessPieceType.BISHOP
import com.twokwy.chessandroid.pieces.ChessPieceType.KING
import com.twokwy.chessandroid.pieces.ChessPieceType.KNIGHT
import com.twokwy.chessandroid.pieces.ChessPieceType.PAWN
import com.twokwy.chessandroid.pieces.ChessPieceType.QUEEN
import com.twokwy.chessandroid.pieces.ChessPieceType.ROOK
import kotlin.math.abs
import kotlin.math.max

class ChessPiece(val pieceType: ChessPieceType, val color: PieceColor) {
    fun isLegalMove(origin: Position, destination: Position, destinationPiece: ChessPiece? = null):
            Boolean = when (pieceType) {
        KING -> isLegalKingMove(origin, destination)
        QUEEN -> isLegalQueenMove(origin, destination)
        BISHOP -> isLegalBishopMove(origin, destination)
        KNIGHT -> isLegalKnightMove(origin, destination)
        ROOK -> isLegalRookMove(origin, destination)
        PAWN -> isLegalPawnMove(origin, destination, destinationPiece)
    }

    private fun isLegalKingMove(
        origin: Position,
        destination: Position
    ): Boolean = abs(destination.x - origin.x) <= 1 && abs(destination.y - origin.y) <= 1

    private fun isLegalQueenMove(
        origin: Position,
        destination: Position
    ): Boolean = isLegalRookMove(origin, destination) || isLegalBishopMove(origin, destination)

    private fun isLegalBishopMove(
        origin: Position,
        destination: Position
    ): Boolean = abs(destination.x - origin.x) == abs(destination.y - origin.y)

    private fun isLegalRookMove(
        origin: Position,
        destination: Position
    ): Boolean = origin.x == destination.x || origin.y == destination.y

    private fun isLegalPawnMove(
        origin: Position,
        destination: Position,
        destinationPiece: ChessPiece?
    ): Boolean {
        val xDiff = abs(destination.x - origin.x)
        val yDiff = destination.y - origin.y
        val forwardsMovement = when (color) {
            PieceColor.WHITE -> yDiff
            PieceColor.BLACK -> -yDiff
        }
        // allow valid diagonal taking moves.
        if (destinationPiece != null && destinationPiece.color != color && forwardsMovement == 1 &&
            xDiff == 1
        ) return true
        // disallow any other kinds of taking moves.
        // TODO: Allow taking other pawns en-passant when valid.
        if (destinationPiece != null) {
            return false
        }
        // disallow any other kinds of sideways movements.
        if (xDiff > 0) return false

        // special behavior for first pawn move
        val isFirstMove = when (color) {
            PieceColor.WHITE -> origin.y == 1
            PieceColor.BLACK -> origin.y == 6
        }
        if (isFirstMove && forwardsMovement == 2) {
            return true
        }
        return forwardsMovement == 1
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

    fun positionsToCheck(origin: Position, destination: Position): List<Position> {
        return when (pieceType) {
            KNIGHT -> emptyList()
            else -> straightLineBetween(origin, destination)
        }
    }


    private fun straightLineBetween(
        origin: Position,
        destination: Position
    ): List<Position> {
        val positions = mutableListOf<Position>()

        val xDiff = destination.x - origin.x
        val yDiff = destination.y - origin.y

        // Calculate the direction of movement: -1, 0, or 1
        val dx = if (xDiff == 0) 0 else xDiff / abs(xDiff)
        val dy = if (yDiff == 0) 0 else yDiff / abs(yDiff)

        // The number of steps is the maximum distance moved in either direction
        val steps = max(abs(xDiff), abs(yDiff))

        // Start from 1 to skip the 'origin' square,
        // and go 'until' steps to skip the 'destination' square.
        for (i in 1 until steps) {
            val x = origin.x + (i * dx)
            val y = origin.y + (i * dy)
            positions.add(Position.fromCoords(x, y))
        }

        return positions
    }

    override fun toString(): String {
        return "ChessPiece(pieceType=$pieceType, color=$color)"
    }
}
