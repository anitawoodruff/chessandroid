package com.twokwy.chessandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
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
    public ChessBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        chessBoardFactory = new ChessBoardFactory(context);

        whitePieceDrawables = new EnumMap<>(ChessPieceType.class);
        whitePieceDrawables.put(ChessPieceType.KING, ContextCompat.getDrawable(context, R.drawable.ic_king_white));
        whitePieceDrawables.put(ChessPieceType.QUEEN, ContextCompat.getDrawable(context, R.drawable.ic_queen_white));
        whitePieceDrawables.put(ChessPieceType.BISHOP, ContextCompat.getDrawable(context, R.drawable.ic_bishop_white));
        whitePieceDrawables.put(ChessPieceType.KNIGHT, ContextCompat.getDrawable(context, R.drawable.ic_knight_white));
        whitePieceDrawables.put(ChessPieceType.ROOK, ContextCompat.getDrawable(context, R.drawable.ic_rook_white));
        whitePieceDrawables.put(ChessPieceType.PAWN, ContextCompat.getDrawable(context, R.drawable.ic_pawn_white));

        blackPieceDrawables = new EnumMap<>(ChessPieceType.class);
        blackPieceDrawables.put(ChessPieceType.KING, ContextCompat.getDrawable(context, R.drawable.ic_king_black));
        blackPieceDrawables.put(ChessPieceType.QUEEN, ContextCompat.getDrawable(context, R.drawable.ic_queen_black));
        blackPieceDrawables.put(ChessPieceType.BISHOP, ContextCompat.getDrawable(context, R.drawable.ic_bishop_black));
        blackPieceDrawables.put(ChessPieceType.KNIGHT, ContextCompat.getDrawable(context, R.drawable.ic_knight_black));
        blackPieceDrawables.put(ChessPieceType.ROOK, ContextCompat.getDrawable(context, R.drawable.ic_rook_black));
        blackPieceDrawables.put(ChessPieceType.PAWN, ContextCompat.getDrawable(context, R.drawable.ic_pawn_black));
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
                Rect bounds = squareDrawable.getBounds();
                bounds.inset(INSET_CHESS_PIECE_DISTANCE, INSET_CHESS_PIECE_DISTANCE);
                drawPiece(canvas, bounds, getDrawable(chessPiece));
            });
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
