package com.twokwy.chessandroid;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

import java.util.Objects;
import java.util.Optional;

import androidx.annotation.ColorInt;

public class ChessSquare {
    private final ShapeDrawable drawable;
    private final String location;
    private Optional<ChessPiece> chessPiece;

    public ChessSquare(String location, ShapeDrawable drawable, Optional<ChessPiece> chessPiece) {
        this.location = location;
        this.drawable = drawable;
        this.chessPiece = chessPiece;
    }

    public static ChessSquare create(
            String location,
            @ColorInt int color,
            Rect bounds,
            Optional<ChessPiece> piece) {
        ShapeDrawable drawable = new ShapeDrawable(new RectShape());
        drawable.getPaint().setColor(color);
        drawable.setBounds(bounds.left, bounds.top + 1, bounds.right - 1, bounds.bottom);
        return new ChessSquare(location, drawable, piece);
    }


    public Drawable getDrawable() {
        return drawable;
    }

    public boolean containsPoint(float x, float y) {
        Rect bounds = drawable.getBounds();
        boolean xInRange = x > bounds.left && x < bounds.right;
        boolean yInRange = y > bounds.top && y < bounds.bottom;
        return xInRange && yInRange;
    }

    public Optional<ChessPiece> getPiece() {
        return chessPiece;
    }

    public void setPiece(Optional<ChessPiece> chessPiece) {
        this.chessPiece = chessPiece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessSquare that = (ChessSquare) o;
        return drawable.equals(that.drawable) && chessPiece.equals(that.chessPiece);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drawable, chessPiece);
    }

    @Override
    public String toString() {
        return "ChessSquare{" + "location=" + location + ", chessPiece=" + chessPiece + '}';
    }
}
