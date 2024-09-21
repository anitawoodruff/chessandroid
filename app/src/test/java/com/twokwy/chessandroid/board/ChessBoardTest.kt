package com.twokwy.chessandroid.board

import android.graphics.drawable.Drawable
import com.twokwy.chessandroid.board.ChessBoard
import com.twokwy.chessandroid.board.ChessSquare
import com.twokwy.chessandroid.icons.ChessPieceIcons
import com.twokwy.chessandroid.pieces.ChessPiece
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class ChessBoardTest {
    @Test
    fun constructChessboard_emptyBoard() {
        val icons = object : ChessPieceIcons {
            override fun getDrawable(piece: ChessPiece): Drawable? {
                return null // ShapeDrawable() also works
            }
        }
        val squares = listOf<ChessSquare>()
        ChessBoard(squares, icons)
    }
}