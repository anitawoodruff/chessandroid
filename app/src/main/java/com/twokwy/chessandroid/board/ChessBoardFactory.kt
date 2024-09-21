package com.twokwy.chessandroid.board

import android.content.Context
import android.graphics.Rect
import com.twokwy.chessandroid.R
import com.twokwy.chessandroid.icons.ChessPieceIconsImpl
import com.twokwy.chessandroid.pieces.ChessPiece
import com.twokwy.chessandroid.pieces.ChessPieceType
import com.twokwy.chessandroid.pieces.PieceColor
import java.util.Optional

class ChessBoardFactory(private val context: Context) {
    private val darkSquareColor: Int = context.resources.getColor(R.color.dark_square)
    private val lightSquareColor: Int = context.resources.getColor(R.color.light_square)

    /**
     * Creates a chess board with 8x8 squares per to fit into the given dimensions.
     *
     * @param w width (px) available for the chess board
     * @param h height (px) available for the chess board
     */
    fun createToFillWidthAndHeight(w: Int, h: Int): ChessBoard {
        val maxSquareHeight = h / 8
        val maxSquareWidth = w / 8
        val squareSize = Math.min(maxSquareHeight, maxSquareWidth)
        val xOffset = (w - squareSize * 8) / 2
        val yOffset = (h - squareSize * 8) / 2
        val squares = createListOfSquaresForBoard(squareSize, xOffset, yOffset)
        return ChessBoard(squares, ChessPieceIconsImpl(context))
    }

    private fun createListOfSquaresForBoard(
            squareSize: Int, xOffset: Int, yOffset: Int): List<ChessSquare> {
        val squares: MutableList<ChessSquare> = ArrayList(64)
        for (i in 0..63) {
            val x = i % 8
            val y = i / 8
            val file = FILES[x]
            val rank = RANKS[7 - y]
            val location = "" + file + rank
            val leftBound = xOffset + x * squareSize
            val rightBound = leftBound + squareSize
            val topBound = yOffset + y * squareSize
            val bottomBound = topBound + squareSize
            val bounds = Rect(leftBound, topBound, rightBound, bottomBound)
            val color = if ((x + y) % 2 == 0) darkSquareColor else lightSquareColor
            val chessPiece = getStartingPieceAt(x, y)
            squares.add(
                    i,
                    ChessSquare.create(location, color, bounds, Optional.ofNullable<ChessPiece>(chessPiece)))
        }
        return squares
    }

    companion object {
        private val FILES = charArrayOf('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h')
        private val RANKS = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
        private fun getStartingPieceAt(x: Int, y: Int): ChessPiece? {
            val pieceType = getChessPieceTypeAt(x, y) ?: return null
            return ChessPiece(pieceType, if (y < 2) PieceColor.BLACK else PieceColor.WHITE)
        }

        private fun getChessPieceTypeAt(x: Int, y: Int): ChessPieceType? {
            if (y == 1 || y == 6) {
                return ChessPieceType.PAWN
            } else if (y == 0 || y == 7) {
                when (x) {
                    0, 7 -> return ChessPieceType.ROOK
                    1, 6 -> return ChessPieceType.KNIGHT
                    2, 5 -> return ChessPieceType.BISHOP
                    3 -> return ChessPieceType.KING
                    4 -> return ChessPieceType.QUEEN
                }
            }
            return null
        }
    }
}
