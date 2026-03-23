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
    val pos: Position, val boardDrawable: ShapeDrawable, var piece: ChessPiece?
) {

    fun containsPoint(x: Float, y: Float): Boolean {
        val bounds = boardDrawable.bounds
        val xInRange = x > bounds.left && x < bounds.right
        val yInRange = y > bounds.top && y < bounds.bottom
        return xInRange && yInRange
    }

    fun drawBoard(canvas: Canvas) {
        boardDrawable.draw(canvas)
    }

    fun drawPiece(canvas: Canvas, icons: ChessPieceIcons) {
        piece?.let { chessPiece: ChessPiece ->
            val bounds = boardDrawable.copyBounds()
            bounds.inset(
                INSET_CHESS_PIECE_DISTANCE,
                INSET_CHESS_PIECE_DISTANCE
            )
            drawPiece(canvas, bounds, icons.getDrawable(chessPiece))
        }
    }

    fun isLegalMoveIgnoringCollisions(to: ChessSquare): Boolean =
        piece?.isLegalMove(pos, to.pos) ?: false

    fun positionsToCheckMovingTo(to: ChessSquare): List<Position> =
        piece?.positionsToCheck(pos, to.pos) ?: emptyList()

    override fun toString() = "ChessSquare{position=$pos, chessPiece=$piece}"

    companion object {
        const val INSET_CHESS_PIECE_DISTANCE = 22

        fun create(
            position: Position,
            bounds: Rect,
            piece: ChessPiece?,
            @ColorInt color: Int
        ): ChessSquare {
            val drawable = ShapeDrawable(RectShape())
            drawable.paint.color = color
            drawable.setBounds(bounds.left, bounds.top + 1, bounds.right - 1, bounds.bottom)
            return ChessSquare(position, drawable, piece)
        }

        private fun drawPiece(canvas: Canvas, bounds: Rect, pieceDrawable: Drawable?) {
            pieceDrawable!!.bounds = bounds
            pieceDrawable.draw(canvas)
        }
    }
}

