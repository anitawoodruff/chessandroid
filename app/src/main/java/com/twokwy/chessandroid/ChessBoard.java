package com.twokwy.chessandroid;

import java.util.List;

public class ChessBoard {
    private final List<ChessSquare> squares;

    public ChessBoard(List<ChessSquare> squares) {
        this.squares = squares;
    }

    public List<ChessSquare> getSquares() {
        return squares;
    }
}
