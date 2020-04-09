package fr.romainguilbeau.chess.models.game;

import fr.romainguilbeau.chess.models.pawns.*;

import java.awt.*;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Chess game
 */
public class Game {

    /**
     * Chess board size
     */
    public static final Point BOARD_SIZE = new Point(8, 8);
    /**
     * Player 1
     */
    private final Player player1;
    /**
     * Player 2
     */
    private final Player player2;
    /**
     * The current player that could playing
     */
    private Player playerTurn;
    /**
     * All player 1 's pawns
     */
    private final BasePawn[] player1Pawns;
    /**
     * All player 2 's pawns
     */
    private final BasePawn[] player2Pawns;

    /**
     * Create new game
     *
     * @param player1 The player one
     * @param player2 The player two
     */
    public Game(Player player1, Player player2) {
        if (player1 == null || player2 == null) {
            throw new NullPointerException();
        }

        if (player1.getColor().equals(player2.getColor())) {
            throw new InvalidParameterException("Player 2 and player can not have same color");
        }

        this.player1 = player1;
        this.player2 = player2;
        this.playerTurn = player1;
        player1Pawns = new BasePawn[16];
        player2Pawns = new BasePawn[16];
        populateBoard();
    }

    /**
     * Populate the board with pawns (with initial positions)
     */
    private void populateBoard() {
        for (PawnColor color : PawnColor.values()) {

            BasePawn[] pawns = player1Pawns;
            if (color.equals(player2.getColor())) {
                pawns = player2Pawns;
            }

            int basePawnsY = color == PawnColor.BLACK ? 0 : 7;
            int pawnY = color == PawnColor.BLACK ? 1 : 6;

            pawns[0] = new Rook(this, new ChessPosition(0, basePawnsY), color);
            pawns[1] = new Knight(this, new ChessPosition(1, basePawnsY), color);
            pawns[2] = new Bishop(this, new ChessPosition(2, basePawnsY), color);
            pawns[3] = new King(this, new ChessPosition(3, basePawnsY), color);
            pawns[4] = new Queen(this, new ChessPosition(4, basePawnsY), color);
            pawns[5] = new Bishop(this, new ChessPosition(5, basePawnsY), color);
            pawns[6] = new Knight(this, new ChessPosition(6, basePawnsY), color);
            pawns[7] = new Rook(this, new ChessPosition(7, basePawnsY), color);

            for (int x = 0; x < BOARD_SIZE.x; x++) {
                pawns[8 + x] = new Pawn(this, new ChessPosition(x, pawnY), color);
            }
        }
    }

    /**
     * Get the current player that could playing
     *
     * @return the current player that could playing
     */
    public Player getPlayerTurn() {
        return this.playerTurn;
    }

    /**
     * Get all chess pawns
     *
     * @return All player 2 chess pawns
     */
    public BasePawn[] getPawns() {
        return Stream.concat(Arrays.stream(player1Pawns), Arrays.stream(player2Pawns))
                .toArray(BasePawn[]::new);
    }

    /**
     * Get all pawns by color
     *
     * @param color The color of pawns
     * @return All pawns by color
     */
    public BasePawn[] getPawns(PawnColor color) {
        if (color.equals(player1.getColor())) {
            return player1Pawns.clone();
        } else {
            return player2Pawns.clone();
        }
    }

    /**
     * All chess colors
     */
    public enum PawnColor {
        BLACK, WHITE
    }
}
