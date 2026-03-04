package com.twokwy.chessandroid.board

import android.graphics.Canvas
import android.util.Log
import com.twokwy.chessandroid.icons.ChessPieceIcons
import java.util.Optional

class ChessBoard(private val squares: List<ChessSquare>, private val icons: ChessPieceIcons) {

    fun drawToCanvas(canvas: Canvas) {
        for (square in squares) {
            square.drawBoard(canvas)
            square.drawPiece(canvas, icons)
        }
    }

    fun handleDrag(downX: Float, downY: Float, upX: Float, upY: Float): Boolean {
        val downSquare = toSquare(downX, downY)
        val upSquare = toSquare(upX, upY)
        Log.d("ChessBoard", String.format("downSquare=%s, upSquare=%s", downSquare, upSquare))
        if (!downSquare.isPresent || !upSquare.isPresent) {
            // Dragging onto or off of the board is not handled
            return false
        }
        if (downSquare == upSquare) {
            // Tapping on a square is a no-op
            return true
        }
        if (downSquare.get().piece == null) {
            // Dragging from an empty square is not handled
            return false
        }
        if (isLegalMove(downSquare.get(), upSquare.get())) {
            movePiece(downSquare.get(), upSquare.get())
        }
        return true
    }

    private fun isLegalMove(from: ChessSquare, to: ChessSquare): Boolean {
        val pieceToBeMoved = from.piece
        if (!from.pieceCanReach(to)) {
            return false
        }
        val pieceToBeTaken = to.piece
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
