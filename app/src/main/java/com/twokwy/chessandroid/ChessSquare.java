package com.twokwy.chessandroid;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;

import androidx.annotation.ColorInt;

public class ChessSquare {
    private final ShapeDrawable drawable;

    public ChessSquare(ShapeDrawable drawable) {
        this.drawable = drawable;
    }

    public static ChessSquare create(@ColorInt int color, Rect bounds) {
        ShapeDrawable drawable = new ShapeDrawable(new RectShape());
        drawable.getPaint().setColor(color);
        drawable.setBounds(bounds.left, bounds.top + 1, bounds.right - 1, bounds.bottom);
        return new ChessSquare(drawable);
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
