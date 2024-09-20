package com.twokwy.chessandroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.twokwy.chessandroid.ChessPieceType.*
import com.twokwy.chessandroid.PieceColor.BLACK
import com.twokwy.chessandroid.PieceColor.WHITE

class ChessBoardView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val chessBoardFactory: ChessBoardFactory
    private val chessPieceIcons: ChessPieceIcons
    private lateinit var chessBoard: ChessBoard
    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f

    init {
        chessBoardFactory = ChessBoardFactory(context)
        chessPieceIcons = ChessPieceIcons(context)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        chessBoard = chessBoardFactory.createToFillWidthAndHeight(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (square in chessBoard.squares) {
            val squareDrawable = square.drawable
            squareDrawable.draw(canvas)
            val piece = square.piece
            piece.ifPresent { chessPiece: ChessPiece ->
                val bounds = squareDrawable.copyBounds()
                bounds.inset(INSET_CHESS_PIECE_DISTANCE, INSET_CHESS_PIECE_DISTANCE)
                drawPiece(canvas, bounds, getDrawable(chessPiece))
            }
        }
    }

    fun endDrag(): Boolean {
        val downSquare = chessBoard.getSquareContainingPoint(downX, downY)
        val upSquare = chessBoard.getSquareContainingPoint(upX, upY)
        Log.d("ChessBoardView", String.format("downSquare=%s, upSquare=%s", downSquare, upSquare))
        if (downSquare == upSquare) {
            return true
        }
        if (downSquare.isPresent && downSquare.get().piece.isPresent && upSquare.isPresent) {
            chessBoard.movePiece(downSquare.get(), upSquare.get())
            invalidate()
            return true
        }
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        return when (action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                true
            }

            MotionEvent.ACTION_UP -> {
                upX = event.x
                upY = event.y
                return endDrag()
            }

            else -> super.onTouchEvent(event)
        }
    }

    private fun getDrawable(piece: ChessPiece): Drawable? {
        return chessPieceIcons.getDrawable(piece)
    }

    companion object {
        const val INSET_CHESS_PIECE_DISTANCE = 22
        private fun drawPiece(canvas: Canvas, bounds: Rect, pieceDrawable: Drawable?) {
            pieceDrawable!!.bounds = bounds
            pieceDrawable.draw(canvas)
        }
    }
}
