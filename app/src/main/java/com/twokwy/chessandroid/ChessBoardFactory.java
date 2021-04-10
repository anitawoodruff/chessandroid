package com.twokwy.chessandroid;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

public class ChessBoardFactory {
    private final int darkSquareColor;
    private final int lightSquareColor;

    public ChessBoardFactory(Context context) {
        darkSquareColor = context.getResources().getColor(R.color.dark_square);
        lightSquareColor = context.getResources().getColor(R.color.light_square);
    }

    /**
     * Creates a chess board with 8x8 squares per to fit into the given dimensions.
     * @param w width (px) available for the chess board
     * @param h height (px) available for the chess board
     */
    public ChessBoard createToFillWidthAndHeight(int w, int h) {
        final int maxSquareHeight = h / 8;
        final int maxSquareWidth = w / 8;

        final int squareSize = Math.min(maxSquareHeight, maxSquareWidth);

        int xOffset = (w - (squareSize * 8)) / 2;
        int yOffset = (h - (squareSize * 8)) / 2;

        List<ChessSquare> squares = createListOfEmptySquaresForBoard(squareSize, xOffset, yOffset);

        return new ChessBoard(squares);
    }

    private List<ChessSquare> createListOfEmptySquaresForBoard(int squareSize, int xOffset, int yOffset) {
        List<ChessSquare> squares = new ArrayList<>(64);
        for (int i = 0; i < 64; i++) {
            int x = i % 8;
            int y = i / 8;

            int leftBound = xOffset + x * squareSize;
            int rightBound = leftBound + squareSize;
            int topBound = yOffset + y * squareSize;
            int bottomBound = topBound + squareSize;

            Rect bounds = new Rect(leftBound, topBound, rightBound, bottomBound);
            int color = (x + y) % 2 == 0 ? lightSquareColor : darkSquareColor;
            squares.add(i, ChessSquare.create(color, bounds));
        }
        return squares;
    }
}
