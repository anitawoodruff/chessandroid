package com.twokwy.chessandroid

import java.util.Objects

class ChessPiece(val pieceType: ChessPieceType, private val color: PieceColor) {
    val isWhite: Boolean
        get() = color == PieceColor.WHITE

    override fun toString(): String {
        return "ChessPiece{" + "type=" + pieceType + ", color=" + color + '}'
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as ChessPiece
        return pieceType == that.pieceType && color == that.color
    }

    override fun hashCode(): Int {
        return Objects.hash(pieceType, color)
    }
}
