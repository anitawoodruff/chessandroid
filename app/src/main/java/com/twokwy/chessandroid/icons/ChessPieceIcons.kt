package com.twokwy.chessandroid.icons

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.twokwy.chessandroid.R
import com.twokwy.chessandroid.pieces.ChessPieceType.*
import com.twokwy.chessandroid.pieces.PieceColor.BLACK
import com.twokwy.chessandroid.pieces.PieceColor.WHITE
import com.twokwy.chessandroid.pieces.ChessPiece
import com.twokwy.chessandroid.pieces.ChessPieceType

interface ChessPieceIcons {
    fun getDrawable(piece: ChessPiece): Drawable?
}

class ChessPieceIconsImpl(private val context: Context) : ChessPieceIcons {

    private var blackIcons = mutableMapOf<ChessPieceType, Drawable>()
    private var whiteIcons = mutableMapOf<ChessPieceType, Drawable>()

    init {
        addDrawable(ChessPiece(KING, WHITE), R.drawable.ic_king_white)
        addDrawable(ChessPiece(QUEEN, WHITE), R.drawable.ic_queen_white)
        addDrawable(ChessPiece(BISHOP, WHITE), R.drawable.ic_bishop_white)
        addDrawable(ChessPiece(KNIGHT, WHITE), R.drawable.ic_knight_white)
        addDrawable(ChessPiece(ROOK, WHITE), R.drawable.ic_rook_white);
        addDrawable(ChessPiece(PAWN, WHITE), R.drawable.ic_pawn_white)
        addDrawable(ChessPiece(KING, BLACK), R.drawable.ic_king_black)
        addDrawable(ChessPiece(QUEEN, BLACK), R.drawable.ic_queen_black)
        addDrawable(ChessPiece(BISHOP, BLACK), R.drawable.ic_bishop_black)
        addDrawable(ChessPiece(KNIGHT, BLACK), R.drawable.ic_knight_black)
        addDrawable(ChessPiece(ROOK, BLACK), R.drawable.ic_rook_black)
        addDrawable(ChessPiece(PAWN, BLACK), R.drawable.ic_pawn_black)
    }
    private fun addDrawable(piece: ChessPiece, drawableResId: Int) {
        when (piece.color) {
            WHITE -> whiteIcons[piece.pieceType] = loadDrawable(drawableResId)
            BLACK -> blackIcons[piece.pieceType] = loadDrawable(drawableResId)
        }
    }

    private fun loadDrawable(drawableResId: Int) =
            ContextCompat.getDrawable(context, drawableResId)!!

    override fun getDrawable(piece: ChessPiece): Drawable? {
        return when (piece.color) {
            WHITE -> whiteIcons[piece.pieceType]
            BLACK -> blackIcons[piece.pieceType]
        }
    }

}