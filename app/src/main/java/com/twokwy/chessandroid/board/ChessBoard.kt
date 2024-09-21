package com.twokwy.chessandroid.board

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import com.twokwy.chessandroid.icons.ChessPieceIcons
import com.twokwy.chessandroid.pieces.ChessPiece
import java.util.Optional

class ChessBoard(private val squares: List<ChessSquare>, private val icons: ChessPieceIcons) {

    fun drawToCanvas(canvas: Canvas) {
        for (square in squares) {
            val squareDrawable = square.drawable
            squareDrawable.draw(canvas)
            val piece = square.piece
            piece?.let { chessPiece: ChessPiece ->
                val bounds = squareDrawable.copyBounds()
                bounds.inset(INSET_CHESS_PIECE_DISTANCE, INSET_CHESS_PIECE_DISTANCE)
                drawPiece(canvas, bounds, icons.getDrawable(chessPiece))
            }
        }
    }

    private fun drawPiece(canvas: Canvas, bounds: Rect, pieceDrawable: Drawable?) {
        pieceDrawable!!.bounds = bounds
        pieceDrawable.draw(canvas)
    }

    fun handleDrag(downX: Float, downY: Float, upX: Float, upY: Float): Boolean {
        // TODO: Refactor to new class 'ChessMove'? (init with 'from' & 'to' squares)
        // Could have a method isValid(), if valid then movePiece() (could become ChessMove
        // .execute())
        val downSquare = toSquare(downX, downY)
        val upSquare = toSquare(upX, upY)
        Log.d("ChessBoard", String.format("downSquare=%s, upSquare=%s", downSquare, upSquare))
        if (!downSquare.isPresent || !upSquare.isPresent) {
            // Dragging onto or off of the board is not handled
            return false
        }
        if (downSquare.get().piece == null) {
            // Dragging from an empty square is not handled
            return false
        }
        if (downSquare == upSquare) {
            // Tapping on a square is a no-op
            return true
        }
        val pieceToBeMoved = downSquare.get().piece
        val pieceToBeTaken = upSquare.get().piece
        if (pieceToBeTaken != null && pieceToBeMoved?.color == pieceToBeTaken.color) {
            // Attempting to take a piece of the same color is a no-op
            return true
        }
        movePiece(downSquare.get(), upSquare.get())
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

    companion object {
        const val INSET_CHESS_PIECE_DISTANCE = 22
    }
}
