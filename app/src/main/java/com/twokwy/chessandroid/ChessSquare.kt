package com.twokwy.chessandroid

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.annotation.ColorInt
import java.util.Objects
import java.util.Optional

class ChessSquare(private val location: String, val drawable: ShapeDrawable, var piece: Optional<ChessPiece>) {

    fun containsPoint(x: Float, y: Float): Boolean {
        val bounds = drawable.bounds
        val xInRange = x > bounds.left && x < bounds.right
        val yInRange = y > bounds.top && y < bounds.bottom
        return xInRange && yInRange
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ChessSquare
        return drawable == that.drawable && piece == that.piece
    }

    override fun hashCode(): Int {
        return Objects.hash(drawable, piece)
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
