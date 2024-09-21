package com.twokwy.chessandroid

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

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