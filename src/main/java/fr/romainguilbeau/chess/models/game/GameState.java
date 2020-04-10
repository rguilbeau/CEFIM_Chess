package fr.romainguilbeau.chess.models.game;

import fr.romainguilbeau.chess.models.chesspieces.BaseChessPiece;

import java.security.InvalidParameterException;

/**
 * Represents the games at a given time
 */
public class GameState {

    /**
     * Copy of all player one chess piece
     */
    private final BaseChessPiece[] blackChessPieces;
    /**
     * Copy of all player two chess piece
     */
    private final BaseChessPiece[] whiteChessPieces;
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
    public GameState(BaseChessPiece[] whiteChessPieces, BaseChessPiece[] blackChessPieces, Game.ChessColor colorTurn) {
        if (colorTurn == null || whiteChessPieces == null || blackChessPieces == null) {
            throw new NullPointerException("Invalid game state");
        }

        if (whiteChessPieces.length != 16 || blackChessPieces.length != 16) {
            throw new InvalidParameterException("Invalid game state");
        }

        this.colorTurn = colorTurn;

        this.whiteChessPieces = copy(whiteChessPieces);
        this.blackChessPieces = copy(blackChessPieces);
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
    public BaseChessPiece[] getWhiteChessPieces() {
        return copy(whiteChessPieces);
    }

    /**
     * Get all black chess pieces copy
     *
     * @return all black chess pieces copy
     */
    public BaseChessPiece[] getBlackChessPieces() {
        return copy(blackChessPieces);
    }

    /**
     * Deep copy of chess pieces
     *
     * @param chessPieces chess pieces to copy
     * @return copy of chess pieces
     */
    private BaseChessPiece[] copy(BaseChessPiece[] chessPieces) {
        BaseChessPiece[] copy = new BaseChessPiece[chessPieces.length];

        for (int i = 0; i < chessPieces.length; i++) {
            copy[i] = (BaseChessPiece) chessPieces[i].clone();
        }

        return copy;
    }
}
