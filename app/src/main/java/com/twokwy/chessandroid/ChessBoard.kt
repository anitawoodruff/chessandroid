package com.twokwy.chessandroid

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.Log
import java.util.Optional

class ChessBoard(private val squares: List<ChessSquare>, private val icons: ChessPieceIcons) {

    fun drawToCanvas(canvas: Canvas) {
        for (square in squares) {
            val squareDrawable = square.drawable
            squareDrawable.draw(canvas)
            val piece = square.piece
            piece.ifPresent { chessPiece: ChessPiece ->
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

    fun handleDrag(downX: Float, downY: Float, upX: Float, upY: Float) : Boolean {
        val downSquare = getSquareContainingPoint(downX, downY)
        val upSquare = getSquareContainingPoint(upX, upY)
        Log.d("ChessBoard", String.format("downSquare=%s, upSquare=%s", downSquare, upSquare))
        if (downSquare == upSquare) {
            return true
        }
        if (downSquare.isPresent && downSquare.get().piece.isPresent && upSquare.isPresent) {
            movePiece(downSquare.get(), upSquare.get())
            return true
        }
        return false
    }

    private fun getSquareContainingPoint(
            x: Float, y: Float): Optional<ChessSquare> {
        for (square in squares) {
            if (square.containsPoint(x, y)) {
                return Optional.of(square)
            }
        }
        return Optional.empty()
    }

    private fun movePiece(from: ChessSquare, to: ChessSquare) {
        to.piece = from.piece
        from.piece = Optional.empty()
    }

    companion object {
        const val INSET_CHESS_PIECE_DISTANCE = 22
    }
}
