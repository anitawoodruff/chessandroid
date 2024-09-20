package com.twokwy.chessandroid

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

class ChessPieceIcons() {

    private var icons = mutableMapOf<ChessPieceType, Drawable>()

    fun addDrawable(context: Context, pieceType: ChessPieceType, drawableResId: Int) {
        icons[pieceType] = ContextCompat.getDrawable(context, drawableResId)!!
    }

    fun getDrawable(pieceType: ChessPieceType): Drawable? {
        return icons[pieceType]
    }

}