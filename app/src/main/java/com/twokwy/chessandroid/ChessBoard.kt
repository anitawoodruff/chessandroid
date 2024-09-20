package com.twokwy.chessandroid

import java.util.Optional

class ChessBoard(val squares: List<ChessSquare>) {

    fun movePiece(from: ChessSquare, to: ChessSquare) {
        to.piece = from.piece
        from.piece = Optional.empty()
    }
}
