package com.twokwy.chessandroid;

import static com.twokwy.chessandroid.ChessPieceType.BISHOP;
import static com.twokwy.chessandroid.ChessPieceType.KING;
import static com.twokwy.chessandroid.ChessPieceType.KNIGHT;
import static com.twokwy.chessandroid.ChessPieceType.PAWN;
import static com.twokwy.chessandroid.ChessPieceType.QUEEN;
import static com.twokwy.chessandroid.ChessPieceType.ROOK;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.EnumMap;
import java.util.Optional;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class ChessBoardView extends View {
    public static final int INSET_CHESS_PIECE_DISTANCE = 22;

    private final ChessBoardFactory chessBoardFactory;

    private final EnumMap<ChessPieceType, Drawable> whitePieceDrawables;
    private final EnumMap<ChessPieceType, Drawable> blackPieceDrawables;

    private ChessBoard chessBoard;
    private float downX;
    private float downY;
    private float upX;
    private float upY;

    public ChessBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        chessBoardFactory = new ChessBoardFactory(context);

        whitePieceDrawables = new EnumMap<>(ChessPieceType.class);
        addDrawable(context, whitePieceDrawables, KING, R.drawable.ic_king_white);
        addDrawable(context, whitePieceDrawables, QUEEN, R.drawable.ic_queen_white);
        addDrawable(context, whitePieceDrawables, BISHOP, R.drawable.ic_bishop_white);
        addDrawable(context, whitePieceDrawables, KNIGHT, R.drawable.ic_knight_white);
        addDrawable(context, whitePieceDrawables, ROOK, R.drawable.ic_rook_white);
        addDrawable(context, whitePieceDrawables, PAWN, R.drawable.ic_pawn_white);

        blackPieceDrawables = new EnumMap<>(ChessPieceType.class);
        addDrawable(context, blackPieceDrawables, KING, R.drawable.ic_king_black);
        addDrawable(context, blackPieceDrawables, QUEEN, R.drawable.ic_queen_black);
        addDrawable(context, blackPieceDrawables, BISHOP, R.drawable.ic_bishop_black);
        addDrawable(context, blackPieceDrawables, KNIGHT, R.drawable.ic_knight_black);
        addDrawable(context, blackPieceDrawables, ROOK, R.drawable.ic_rook_black);
        addDrawable(context, blackPieceDrawables, PAWN, R.drawable.ic_pawn_black);
    }

    private static void addDrawable(Context context,
                             EnumMap<ChessPieceType, Drawable> map,
                             ChessPieceType chessPieceType,
                             int drawable) {
        map.put(chessPieceType, ContextCompat.getDrawable(context, drawable));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        chessBoard = chessBoardFactory.createToFillWidthAndHeight(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ChessSquare square : chessBoard.getSquares()) {
            Drawable squareDrawable = square.getDrawable();
            squareDrawable.draw(canvas);
            Optional<ChessPiece> piece = square.getPiece();
            piece.ifPresent(chessPiece -> {
                Rect bounds = squareDrawable.copyBounds();
                bounds.inset(INSET_CHESS_PIECE_DISTANCE, INSET_CHESS_PIECE_DISTANCE);
                drawPiece(canvas, bounds, getDrawable(chessPiece));
            });
        }
    }

    @Override
    public boolean performClick() {
        Optional<ChessSquare> downSquare = getSquareContainingPoint(downX, downY, chessBoard);
        Optional<ChessSquare> upSquare = getSquareContainingPoint(upX, upY, chessBoard);
        if (downSquare.isPresent() && downSquare.get().getPiece().isPresent() && upSquare.isPresent()) {
            // move the piece
            Log.d("ANITA",
                  String.format("placing piece: %s at square %s",
                                downSquare.get().getPiece().get().getPieceType(),
                                upSquare.get()));
            chessBoard.movePiece(downSquare.get(), upSquare.get());
            invalidate();
            return true;
        }
        return super.performClick();
    }

    public static Optional<ChessSquare> getSquareContainingPoint(float x,
                                                                 float y,
                                                                 ChessBoard chessBoard) {
        for (ChessSquare square : chessBoard.getSquares()) {
            if (square.containsPoint(x, y)) {

                return Optional.of(square);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                upX = event.getX();
                upY = event.getY();
                return performClick();
            default:
                return super.onTouchEvent(event);
        }
    }

    private Drawable getDrawable(ChessPiece piece) {
        if (piece.isWhite()) {
            return whitePieceDrawables.get(piece.getPieceType());
        }
        return blackPieceDrawables.get(piece.getPieceType());
    }

    private static void drawPiece(Canvas canvas, Rect bounds, Drawable pieceDrawable) {
        pieceDrawable.setBounds(bounds);
        pieceDrawable.draw(canvas);
    }

}
