package com.twokwy.chessandroid

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.twokwy.chessandroid.board.ChessBoard
import com.twokwy.chessandroid.board.ChessBoardFactory

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
        if (chessBoard.handleDrag(downX, downY, upX, upY)) {
            invalidate()
            return true
        } else {
            return super.performClick()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> {
                upX = event.x
                upY = event.y
                return performClick()
            }
            else -> return super.onTouchEvent(event)
        }
    }
}
