package com.twokwy.chessandroid;

import android.content.Context;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChessBoardFactory {
    private final int darkSquareColor;
    private final int lightSquareColor;

    private static final char[] FILES = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
    private static final int[] RANKS = {1, 2, 3, 4, 5, 6, 7, 8};

    public ChessBoardFactory(Context context) {
        darkSquareColor = context.getResources().getColor(R.color.dark_square);
        lightSquareColor = context.getResources().getColor(R.color.light_square);
    }

    /**
     * Creates a chess board with 8x8 squares per to fit into the given dimensions.
     *
     * @param w width (px) available for the chess board
     * @param h height (px) available for the chess board
     */
    public ChessBoard createToFillWidthAndHeight(int w, int h) {
        final int maxSquareHeight = h / 8;
        final int maxSquareWidth = w / 8;

        final int squareSize = Math.min(maxSquareHeight, maxSquareWidth);

        int xOffset = (w - (squareSize * 8)) / 2;
        int yOffset = (h - (squareSize * 8)) / 2;

        List<ChessSquare> squares = createListOfSquaresForBoard(squareSize, xOffset, yOffset);

        return new ChessBoard(squares);
    }

    private List<ChessSquare> createListOfSquaresForBoard(
            int squareSize, int xOffset, int yOffset) {
        List<ChessSquare> squares = new ArrayList<>(64);
        for (int i = 0; i < 64; i++) {
            int x = i % 8;
            int y = i / 8;
            char file = FILES[x];
            int rank = RANKS[7 - y];
            String location = "" + file + rank;

            int leftBound = xOffset + x * squareSize;
            int rightBound = leftBound + squareSize;
            int topBound = yOffset + y * squareSize;
            int bottomBound = topBound + squareSize;

            Rect bounds = new Rect(leftBound, topBound, rightBound, bottomBound);
            int color = (x + y) % 2 == 0 ? darkSquareColor : lightSquareColor;
            ChessPiece chessPiece = getStartingPieceAt(x, y);
            squares.add(
                    i,
                    ChessSquare.create(location, color, bounds, Optional.ofNullable(chessPiece)));
        }
        return squares;
    }

    private static ChessPiece getStartingPieceAt(int x, int y) {
        ChessPieceType pieceType = getChessPieceTypeAt(x, y);
        if (pieceType == null) {
            return null;
        }
        return new ChessPiece(pieceType, y < 2 ? PieceColor.BLACK : PieceColor.WHITE);
    }

    private static ChessPieceType getChessPieceTypeAt(int x, int y) {
        if (y == 1 || y == 6) {
            return ChessPieceType.PAWN;
        } else if ((y == 0 || y == 7)) {
            switch (x) {
                case 0:
                case 7:
                    return ChessPieceType.ROOK;
                case 1:
                case 6:
                    return ChessPieceType.KNIGHT;
                case 2:
                case 5:
                    return ChessPieceType.BISHOP;
                case 3:
                    return ChessPieceType.KING;
                case 4:
                    return ChessPieceType.QUEEN;
            }
        }
        return null;
    }
}
