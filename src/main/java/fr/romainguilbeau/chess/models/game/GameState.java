package fr.romainguilbeau.chess.models.game;

import fr.romainguilbeau.chess.models.chesspieces.BaseChessPiece;

import java.security.InvalidParameterException;
import java.util.HashMap;

/**
 * Represents the games at a given time
 */
public class GameState {

    /**
     * Copy of all player one chess piece
     */
    private final HashMap<Pos, BaseChessPiece> blackChessPieces;
    /**
     * Copy of all player two chess piece
     */
    private final HashMap<Pos, BaseChessPiece> whiteChessPieces;
    /**
     * Player turn
     */
    private final Game.ChessColor colorTurn;

    /**
     * Create new game state
     *
     * @param whiteChessPieces all player one chess piece
     * @param blackChessPieces all player two chess piece
     * @param colorTurn        Player turn
     */
    public GameState(HashMap<Pos, BaseChessPiece> whiteChessPieces, HashMap<Pos, BaseChessPiece> blackChessPieces, Game.ChessColor colorTurn) {
        if (colorTurn == null || whiteChessPieces == null || blackChessPieces == null) {
            throw new NullPointerException("Invalid game state");
        }

        if (whiteChessPieces.size() != 16 || blackChessPieces.size() != 16) {
            throw new InvalidParameterException("Invalid game state");
        }

        this.colorTurn = colorTurn;

        this.whiteChessPieces = (HashMap<Pos, BaseChessPiece>) whiteChessPieces.clone();
        this.blackChessPieces = (HashMap<Pos, BaseChessPiece>) blackChessPieces.clone();
    }

    /**
     * Get the color turn
     *
     * @return The color turn
     */
    public Game.ChessColor getColorTurn() {
        return colorTurn;
    }

    /**
     * Get all white chess pieces copy
     *
     * @return all white chess pieces copy
     */
    public HashMap<Pos, BaseChessPiece> getWhiteChessPieces() {
        return (HashMap<Pos, BaseChessPiece>) whiteChessPieces.clone();
    }

    /**
     * Get all black chess pieces copy
     *
     * @return all black chess pieces copy
     */
    public HashMap<Pos, BaseChessPiece> getBlackChessPieces() {
        return (HashMap<Pos, BaseChessPiece>) blackChessPieces.clone();
    }
}
