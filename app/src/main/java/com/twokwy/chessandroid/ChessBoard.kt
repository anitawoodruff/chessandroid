package com.twokwy.chessandroid

import java.util.Optional

class ChessBoard(val squares: List<ChessSquare>) {

    fun movePiece(from: ChessSquare, to: ChessSquare) {
        to.piece = from.piece
        from.piece = Optional.empty()
    }

    fun getSquareContainingPoint(
            x: Float, y: Float): Optional<ChessSquare> {
        for (square in squares) {
            if (square.containsPoint(x, y)) {
                return Optional.of(square)
            }
        }
        return Optional.empty()
    }
}
