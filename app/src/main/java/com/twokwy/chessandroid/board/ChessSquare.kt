package com.twokwy.chessandroid.board

import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.annotation.ColorInt
import com.twokwy.chessandroid.pieces.ChessPiece
import java.util.Optional

data class ChessSquare(private val location: String, val drawable: ShapeDrawable, var piece:
Optional<ChessPiece>) {

    fun containsPoint(x: Float, y: Float): Boolean {
        val bounds = drawable.bounds
        val xInRange = x > bounds.left && x < bounds.right
        val yInRange = y > bounds.top && y < bounds.bottom
        return xInRange && yInRange
    }

    override fun toString(): String {
        return "ChessSquare{" + "location=" + location + ", chessPiece=" + piece + '}'
    }

    companion object {
        fun create(
                location: String,
                @ColorInt color: Int,
                bounds: Rect,
                piece: Optional<ChessPiece>): ChessSquare {
            val drawable = ShapeDrawable(RectShape())
            drawable.paint.color = color
            drawable.setBounds(bounds.left, bounds.top + 1, bounds.right - 1, bounds.bottom)
            return ChessSquare(location, drawable, piece)
        }
    }
}
