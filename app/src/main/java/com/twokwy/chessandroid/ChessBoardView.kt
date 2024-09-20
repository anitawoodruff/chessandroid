package com.twokwy.chessandroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class ChessBoardView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val chessBoardFactory: ChessBoardFactory
    private lateinit var chessBoard: ChessBoard
    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f

    init {
        chessBoardFactory = ChessBoardFactory(context)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        chessBoard = chessBoardFactory.createToFillWidthAndHeight(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        chessBoard.drawToCanvas(canvas)
    }

    override fun performClick(): Boolean {
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
        return super.performClick()
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
                return performClick()
            }

            else -> super.onTouchEvent(event)
        }
    }

    companion object {
        const val INSET_CHESS_PIECE_DISTANCE = 22
    }
}
