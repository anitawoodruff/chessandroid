package com.twokwy.chessandroid;

import java.util.List;
import java.util.Optional;

public class ChessBoard {
    private final List<ChessSquare> squares;

    public ChessBoard(List<ChessSquare> squares) {
        this.squares = squares;
    }

    public List<ChessSquare> getSquares() {
        return squares;
    }

    public void movePiece(ChessSquare from, ChessSquare to) {
        to.setPiece(from.getPiece());
        from.setPiece(Optional.empty());
    }
}
