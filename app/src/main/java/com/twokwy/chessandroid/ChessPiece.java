package com.twokwy.chessandroid;

import java.util.Objects;

public class ChessPiece {
    private final ChessPieceType type;
    private final PieceColor color;

    public ChessPiece(ChessPieceType type, PieceColor color) {
        this.type = type;
        this.color = color;
    }

    public ChessPieceType getPieceType() {
        return type;
    }

    public boolean isWhite() {
        return color == PieceColor.WHITE;
    }

    @Override
    public String toString() {
        return "ChessPiece{" + "type=" + type + ", color=" + color + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return type == that.type && color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color);
    }
}
