package com.twokwy.chessandroid;

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
}
