package fr.romainguilbeau.chess.models.game;

import fr.romainguilbeau.chess.models.chesspieces.*;

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
     * All player 1 's chess pieces
     */
    private final BaseChessPiece[] player1ChessPieces;
    /**
     * All player 2 's chess pieces
     */
    private final BaseChessPiece[] player2ChessPieces;

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
        player1ChessPieces = new BaseChessPiece[16];
        player2ChessPieces = new BaseChessPiece[16];
        populateBoard();
    }

    /**
     * Populate the board with chess pieces (with initial positions)
     */
    private void populateBoard() {
        for (ChessColor color : ChessColor.values()) {

            BaseChessPiece[] chessPieces = player1ChessPieces;
            if (color.equals(player2.getColor())) {
                chessPieces = player2ChessPieces;
            }

            int chessPieceY = color == ChessColor.BLACK ? 0 : 7;
            int pawnY = color == ChessColor.BLACK ? 1 : 6;

            chessPieces[0] = new Rook(this, new ChessPosition(0, chessPieceY), color);
            chessPieces[1] = new Knight(this, new ChessPosition(1, chessPieceY), color);
            chessPieces[2] = new Bishop(this, new ChessPosition(2, chessPieceY), color);
            chessPieces[3] = new King(this, new ChessPosition(3, chessPieceY), color);
            chessPieces[4] = new Queen(this, new ChessPosition(4, chessPieceY), color);
            chessPieces[5] = new Bishop(this, new ChessPosition(5, chessPieceY), color);
            chessPieces[6] = new Knight(this, new ChessPosition(6, chessPieceY), color);
            chessPieces[7] = new Rook(this, new ChessPosition(7, chessPieceY), color);

            for (int x = 0; x < BOARD_SIZE.x; x++) {
                chessPieces[8 + x] = new Pawn(this, new ChessPosition(x, pawnY), color);
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
     * Get all chess chess pieces
     *
     * @return All player 2 chess chess pieces
     */
    public BaseChessPiece[] getChessPieces() {
        return Stream.concat(Arrays.stream(player1ChessPieces), Arrays.stream(player2ChessPieces))
                .toArray(BaseChessPiece[]::new);
    }

    /**
     * Get all chess pieces by color
     *
     * @param color The color of chess pieces
     * @return All chess pieces by color
     */
    public BaseChessPiece[] getChessPieces(ChessColor color) {
        if (color.equals(player1.getColor())) {
            return player1ChessPieces.clone();
        } else {
            return player2ChessPieces.clone();
        }
    }

    /**
     * All chess colors
     */
    public enum ChessColor {
        BLACK, WHITE
    }
}
