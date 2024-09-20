package com.twokwy.chessandroid

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.twokwy.chessandroid.PieceColor.BLACK
import com.twokwy.chessandroid.PieceColor.WHITE

class ChessPieceIcons() {

    private var blackIcons = mutableMapOf<ChessPieceType, Drawable>()
    private var whiteIcons = mutableMapOf<ChessPieceType, Drawable>()

    fun addDrawable(context: Context, piece: ChessPiece, drawableResId: Int) {
        when (piece.color) {
            WHITE -> whiteIcons[piece.pieceType] = getDrawable(context, drawableResId)
            BLACK -> blackIcons[piece.pieceType] = getDrawable(context, drawableResId)
        }
    }

    private fun getDrawable(context: Context, drawableResId: Int) =
            ContextCompat.getDrawable(context, drawableResId)!!

    fun getDrawable(piece: ChessPiece): Drawable? {
        return when (piece.color) {
            WHITE -> whiteIcons[piece.pieceType]
            BLACK -> blackIcons[piece.pieceType]
        }
    }

}