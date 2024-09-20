package com.twokwy.chessandroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import java.util.EnumMap
import java.util.Optional

class ChessBoardView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private val chessBoardFactory: ChessBoardFactory
    private val whitePieceDrawables: EnumMap<ChessPieceType, Drawable?>
    private val blackPieceDrawables: EnumMap<ChessPieceType, Drawable?>
    private var chessBoard: ChessBoard? = null
    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f

    init {
        chessBoardFactory = ChessBoardFactory(context)
        whitePieceDrawables = EnumMap(ChessPieceType::class.java)
        addDrawable(context, whitePieceDrawables, ChessPieceType.KING, R.drawable.ic_king_white)
        addDrawable(context, whitePieceDrawables, ChessPieceType.QUEEN, R.drawable.ic_queen_white)
        addDrawable(context, whitePieceDrawables, ChessPieceType.BISHOP, R.drawable.ic_bishop_white)
        addDrawable(context, whitePieceDrawables, ChessPieceType.KNIGHT, R.drawable.ic_knight_white)
        addDrawable(context, whitePieceDrawables, ChessPieceType.ROOK, R.drawable.ic_rook_white)
        addDrawable(context, whitePieceDrawables, ChessPieceType.PAWN, R.drawable.ic_pawn_white)
        blackPieceDrawables = EnumMap(ChessPieceType::class.java)
        addDrawable(context, blackPieceDrawables, ChessPieceType.KING, R.drawable.ic_king_black)
        addDrawable(context, blackPieceDrawables, ChessPieceType.QUEEN, R.drawable.ic_queen_black)
        addDrawable(context, blackPieceDrawables, ChessPieceType.BISHOP, R.drawable.ic_bishop_black)
        addDrawable(context, blackPieceDrawables, ChessPieceType.KNIGHT, R.drawable.ic_knight_black)
        addDrawable(context, blackPieceDrawables, ChessPieceType.ROOK, R.drawable.ic_rook_black)
        addDrawable(context, blackPieceDrawables, ChessPieceType.PAWN, R.drawable.ic_pawn_black)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        chessBoard = chessBoardFactory.createToFillWidthAndHeight(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (square in chessBoard!!.squares) {
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
        val downSquare = getSquareContainingPoint(downX, downY, chessBoard)
        val upSquare = getSquareContainingPoint(upX, upY, chessBoard)
        Log.d("ChessBoardView", String.format("downSquare=%s, upSquare=%s", downSquare, upSquare))
        if (downSquare == upSquare) {
            return true
        }
        if (downSquare.isPresent && downSquare.get().piece.isPresent && upSquare.isPresent) {
            chessBoard!!.movePiece(downSquare.get(), upSquare.get())
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
        return if (piece.isWhite) {
            whitePieceDrawables[piece.pieceType]
        } else blackPieceDrawables[piece.pieceType]
    }

    companion object {
        const val INSET_CHESS_PIECE_DISTANCE = 22
        private fun addDrawable(
                context: Context,
                map: EnumMap<ChessPieceType, Drawable?>,
                chessPieceType: ChessPieceType,
                drawable: Int) {
            map[chessPieceType] = ContextCompat.getDrawable(context, drawable)
        }

        fun getSquareContainingPoint(
                x: Float, y: Float, chessBoard: ChessBoard?): Optional<ChessSquare> {
            for (square in chessBoard!!.squares) {
                if (square.containsPoint(x, y)) {
                    return Optional.of(square)
                }
            }
            return Optional.empty()
        }

        private fun drawPiece(canvas: Canvas, bounds: Rect, pieceDrawable: Drawable?) {
            pieceDrawable!!.bounds = bounds
            pieceDrawable.draw(canvas)
        }
    }
}
