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
     * Increment position fot a direction
     *
     * @param direction The wanted direction
     * @return The new position
     * @throws IndexOutOfBoundsException if invalid direction (out of board)
     */
    public Pos incrementPos(Direction direction) throws IndexOutOfBoundsException {
        Pos newPos = this;
        switch (direction) {
            case NORTH:
                return new Pos(newPos.getX(), newPos.getY() - 1);
            case NORTH_EAST:
                return new Pos(newPos.getX() + 1, newPos.getY() - 1);
            case EAST:
                return new Pos(newPos.getX() + 1, newPos.getY());
            case SOUTH_EAST:
                return new Pos(newPos.getX() + 1, newPos.getY() + 1);
            case SOUTH:
                return new Pos(newPos.getX(), newPos.getY() + 1);
            case SOUTH_WEST:
                return new Pos(newPos.getX() - 1, newPos.getY() + 1);
            case WEST:
                return new Pos(newPos.getX() - 1, newPos.getY());
            case NORTH_WEST:
                return new Pos(newPos.getX() - 1, newPos.getY() - 1);
            default:
                throw new IndexOutOfBoundsException();
        }
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

    /**
     * All possible directions
     */
    public enum Direction {
        NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
    }
}
