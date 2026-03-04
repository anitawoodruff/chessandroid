package com.twokwy.chessandroid.board

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.annotation.ColorInt
import com.twokwy.chessandroid.icons.ChessPieceIcons
import com.twokwy.chessandroid.pieces.ChessPiece

data class ChessSquare(
    val location: Location, var piece:
    ChessPiece?, val drawable: ShapeDrawable
) {

    fun containsPoint(x: Float, y: Float): Boolean {
        val bounds = drawable.bounds
        val xInRange = x > bounds.left && x < bounds.right
        val yInRange = y > bounds.top && y < bounds.bottom
        return xInRange && yInRange
    }

    fun drawBoard(canvas: Canvas) {
        drawable.draw(canvas)
    }

    fun drawPiece(canvas: Canvas, icons: ChessPieceIcons) {
        val piece = piece
        piece?.let { chessPiece: ChessPiece ->
            val bounds = drawable.copyBounds()
            bounds.inset(
                INSET_CHESS_PIECE_DISTANCE,
                INSET_CHESS_PIECE_DISTANCE
            )
            drawPiece(canvas, bounds, icons.getDrawable(chessPiece))
        }
    }

    override fun toString() = "ChessSquare{location=$location, chessPiece=$piece}"

    companion object {
        const val INSET_CHESS_PIECE_DISTANCE = 22

        fun create(
            location: Location,
            bounds: Rect,
            piece: ChessPiece?,
            @ColorInt color: Int
        ): ChessSquare {
            val drawable = ShapeDrawable(RectShape())
            drawable.paint.color = color
            drawable.setBounds(bounds.left, bounds.top + 1, bounds.right - 1, bounds.bottom)
            return ChessSquare(location, piece, drawable)
        }

        private fun drawPiece(canvas: Canvas, bounds: Rect, pieceDrawable: Drawable?) {
            pieceDrawable!!.bounds = bounds
            pieceDrawable.draw(canvas)
        }
    }
}

