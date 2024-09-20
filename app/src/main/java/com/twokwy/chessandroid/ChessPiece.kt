package com.twokwy.chessandroid

import java.util.Objects

data class ChessPiece(val pieceType: ChessPieceType, private val color: PieceColor) {
    val isWhite: Boolean
        get() = color == PieceColor.WHITE
}
