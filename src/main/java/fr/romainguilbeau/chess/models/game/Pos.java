package fr.romainguilbeau.chess.models.game;

import java.awt.*;
import java.util.Objects;

/**
 * Position of chess piece
 */
public class Pos {

    /**
     * The x position
     */
    private final int x;
    /**
     * The y position
     */
    private final int y;

    /**
     * Chess board size
     */
    public static final Point BOARD_SIZE = new Point(8, 8);

    /**
     * Create new chess position
     *
     * @param x The x position
     * @param y The y position
     */
    public Pos(int x, int y) {
        if (x < 0 || x > BOARD_SIZE.x - 1 || y < 0 || y > BOARD_SIZE.y - 1) {
            throw new IndexOutOfBoundsException("Outside board");
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Return the x position
     *
     * @return The x position
     */
    public int getX() {
        return this.x;
    }

    /**
     * Return the y position
     *
     * @return The y position
     */
    public int getY() {
        return this.y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pos pos = (Pos) o;
        return x == pos.x &&
                y == pos.y;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
