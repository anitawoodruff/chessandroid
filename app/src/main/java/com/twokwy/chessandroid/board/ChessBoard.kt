package com.twokwy.chessandroid.board

import android.graphics.Canvas
import android.util.Log
import com.twokwy.chessandroid.icons.ChessPieceIcons
import com.twokwy.chessandroid.pieces.PieceColor
import java.util.Optional

class ChessBoard(
    private val squares: List<ChessSquare>,
    private val icons: ChessPieceIcons,
    private var whoseTurn: PieceColor = PieceColor.WHITE,
    private val enforceTurnTaking: Boolean =true
) {

    fun drawToCanvas(canvas: Canvas) {
        for (square in squares) {
            square.drawBoard(canvas)
            square.drawPiece(canvas, icons)
        }
    }

    fun handleDrag(downX: Float, downY: Float, upX: Float, upY: Float): Boolean {
        val downSquare = toSquare(downX, downY)
        val upSquare = toSquare(upX, upY)
        if (!downSquare.isPresent || !upSquare.isPresent) {
            // Dragging onto or off of the board is not handled
            return false
        }
        Log.d(
            "ChessBoard", String.format(
                "downSquare=%s, upSquare=%s", downSquare.get(), upSquare.get()
            )
        )
        if (downSquare == upSquare) {
            // Tapping on a square is a no-op
            return true
        }
        if (downSquare.get().piece == null) {
            // Dragging from an empty square is not handled
            return false
        }
        if (enforceTurnTaking && downSquare.get().piece?.color != whoseTurn) {
            Log.d("ChessBoard", "Can't move when it's not your turn!")
            return false
        }
        if (isLegalMove(downSquare.get(), upSquare.get())) {
            movePiece(downSquare.get(), upSquare.get())
            whoseTurn = PieceColor.entries[(whoseTurn.ordinal + 1) % 2]
        }
        return true
    }

    private fun isLegalMove(origin: ChessSquare, destination: ChessSquare): Boolean {
        val pieceToBeMoved = origin.piece
        if (!origin.isLegalMoveIgnoringCollisions(destination)) {
            return false
        }
        val positionsToCheck = origin.positionsToCheckMovingTo(destination)
        for (position in positionsToCheck) {
            val square = squares.find { it.pos == position }
            if (square?.piece != null) {
                return false
            }
        }
        val pieceToBeTaken = destination.piece
        if (pieceToBeTaken != null && pieceToBeMoved?.color == pieceToBeTaken.color) {
            // Attempting to take a piece of the same color is a no-op
            return false
        }
        return true
    }

    private fun toSquare(x: Float, y: Float): Optional<ChessSquare> {
        for (square in squares) {
            if (square.containsPoint(x, y)) {
                return Optional.of(square)
            }
        }
        return Optional.empty()
    }

    private fun movePiece(from: ChessSquare, to: ChessSquare) {
        to.piece = from.piece
        from.piece = null
    }
}
