package com.twokwy.chessandroid

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import java.util.Optional

class ChessBoard(private val squares: List<ChessSquare>, private val icons: ChessPieceIcons) {

    fun movePiece(from: ChessSquare, to: ChessSquare) {
        to.piece = from.piece
        from.piece = Optional.empty()
    }

    fun getSquareContainingPoint(
            x: Float, y: Float): Optional<ChessSquare> {
        for (square in squares) {
            if (square.containsPoint(x, y)) {
                return Optional.of(square)
            }
        }
        return Optional.empty()
    }

    fun drawToCanvas(canvas: Canvas) {
        for (square in squares) {
            val squareDrawable = square.drawable
            squareDrawable.draw(canvas)
            val piece = square.piece
            piece.ifPresent { chessPiece: ChessPiece ->
                val bounds = squareDrawable.copyBounds()
                bounds.inset(ChessBoardView.INSET_CHESS_PIECE_DISTANCE, ChessBoardView.INSET_CHESS_PIECE_DISTANCE)
                drawPiece(canvas, bounds, icons.getDrawable(chessPiece))
            }
        }
    }

    private fun drawPiece(canvas: Canvas, bounds: Rect, pieceDrawable: Drawable?) {
        pieceDrawable!!.bounds = bounds
        pieceDrawable.draw(canvas)
    }
}
