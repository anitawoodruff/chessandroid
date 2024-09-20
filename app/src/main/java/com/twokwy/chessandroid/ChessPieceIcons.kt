package com.twokwy.chessandroid

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.twokwy.chessandroid.PieceColor.BLACK
import com.twokwy.chessandroid.PieceColor.WHITE

class ChessPieceIcons(private val context: Context) {

    private var blackIcons = mutableMapOf<ChessPieceType, Drawable>()
    private var whiteIcons = mutableMapOf<ChessPieceType, Drawable>()

    init {
        addDrawable(ChessPiece(ChessPieceType.KING, WHITE), R.drawable.ic_king_white)
        addDrawable(ChessPiece(ChessPieceType.QUEEN, WHITE), R.drawable.ic_queen_white)
        addDrawable(ChessPiece(ChessPieceType.BISHOP, WHITE), R.drawable.ic_bishop_white)
        addDrawable(ChessPiece(ChessPieceType.KNIGHT, WHITE), R.drawable.ic_knight_white)
        addDrawable(ChessPiece(ChessPieceType.ROOK, WHITE), R.drawable.ic_rook_white);
        addDrawable(ChessPiece(ChessPieceType.PAWN, WHITE), R.drawable.ic_pawn_white)
        addDrawable(ChessPiece(ChessPieceType.KING, BLACK), R.drawable.ic_king_black)
        addDrawable(ChessPiece(ChessPieceType.QUEEN, BLACK), R.drawable.ic_queen_black)
        addDrawable(ChessPiece(ChessPieceType.BISHOP, BLACK), R.drawable.ic_bishop_black)
        addDrawable(ChessPiece(ChessPieceType.KNIGHT, BLACK), R.drawable.ic_knight_black)
        addDrawable(ChessPiece(ChessPieceType.ROOK, BLACK), R.drawable.ic_rook_black)
        addDrawable(ChessPiece(ChessPieceType.PAWN, BLACK), R.drawable.ic_pawn_black)
    }
    private fun addDrawable(piece: ChessPiece, drawableResId: Int) {
        when (piece.color) {
            WHITE -> whiteIcons[piece.pieceType] = loadDrawable(drawableResId)
            BLACK -> blackIcons[piece.pieceType] = loadDrawable(drawableResId)
        }
    }

    private fun loadDrawable(drawableResId: Int) =
            ContextCompat.getDrawable(context, drawableResId)!!

    fun getDrawable(piece: ChessPiece): Drawable? {
        return when (piece.color) {
            WHITE -> whiteIcons[piece.pieceType]
            BLACK -> blackIcons[piece.pieceType]
        }
    }

}