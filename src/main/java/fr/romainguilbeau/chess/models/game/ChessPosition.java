package fr.romainguilbeau.chess.models.game;

import java.awt.*;

/**
 * Position of chess piece
 */
public class ChessPosition extends Point {

    /**
     * Chess board size
     */
    public static final Point BOARD_SIZE = new Point(8, 8);

    /**
     * {@inheritDoc}
     */
    public ChessPosition(int x, int y) {
        super(x, y);

        if (x < 0 || x > BOARD_SIZE.x || y < 0 || y > BOARD_SIZE.y) {
            throw new IndexOutOfBoundsException("Outside board");
        }
    }
}
