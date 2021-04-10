package com.twokwy.chessandroid;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

import java.util.Optional;

import androidx.annotation.ColorInt;

public class ChessSquare {
    private final ShapeDrawable drawable;
    private final Optional<ChessPiece> chessPiece;

    public ChessSquare(ShapeDrawable drawable, Optional<ChessPiece> chessPiece) {
        this.drawable = drawable;
        this.chessPiece = chessPiece;
    }

    public static ChessSquare create(@ColorInt int color, Rect bounds, Optional<ChessPiece> piece) {
        ShapeDrawable drawable = new ShapeDrawable(new RectShape());
        drawable.getPaint().setColor(color);
        drawable.setBounds(bounds.left, bounds.top + 1, bounds.right - 1, bounds.bottom);
        return new ChessSquare(drawable, piece);
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public Optional<ChessPiece> getPiece() {
        return chessPiece;
    }
}
