package com.twokwy.chessandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ChessBoardView extends View {

    private ChessBoard chessBoard;

    public ChessBoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (ChessSquare square : chessBoard.getSquares()) {
            square.getDrawable().draw(canvas);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        chessBoard = ChessBoardFactory.createToFillWidthAndHeight(w, h);
    }
}
