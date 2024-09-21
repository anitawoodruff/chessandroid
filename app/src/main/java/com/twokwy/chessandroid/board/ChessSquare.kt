package com.twokwy.chessandroid.board

import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.annotation.ColorInt
import com.twokwy.chessandroid.pieces.ChessPiece

data class ChessSquare(val location: Location, var piece:
ChessPiece?, val drawable: ShapeDrawable) {

    fun containsPoint(x: Float, y: Float): Boolean {
        val bounds = drawable.bounds
        val xInRange = x > bounds.left && x < bounds.right
        val yInRange = y > bounds.top && y < bounds.bottom
        return xInRange && yInRange
    }

    override fun toString() = "ChessSquare{location=$location, chessPiece=$piece}"

    companion object {
        fun create(
                location: Location,
                bounds: Rect,
                piece: ChessPiece?,
                @ColorInt color: Int): ChessSquare {
            val drawable = ShapeDrawable(RectShape())
            drawable.paint.color = color
            drawable.setBounds(bounds.left, bounds.top + 1, bounds.right - 1, bounds.bottom)
            return ChessSquare(location, piece, drawable)
        }
    }
}

data class Location(val x: Int, val y: Int) {

    private val FILES = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
    private val RANKS = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)

    override fun toString(): String {
        val file = FILES[x]
        val rank = RANKS[7 - y]
        return "$file$rank"
    }
}
