package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.game.Pos;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Base of all chess piece
 */
public abstract class BaseChessPiece {
    /**
     * The chess piece color
     */
    private final Game.ChessColor chessColor;
    /**
     * The current game
     */
    private final Game game;

    /**
     * Create new chess piece
     *
     * @param game       The current game
     * @param chessColor The color of this piece
     * @throws InvalidParameterException If invalid arguments
     */
    BaseChessPiece(Game game, Game.ChessColor chessColor) throws InvalidParameterException {
        if (chessColor == null) {
            throw new NullPointerException();
        }

        if (game == null) {
            throw new NullPointerException();
        }

        this.game = game;
        this.chessColor = chessColor;
    }

    /**
     * Get the chess chess piece name
     *
     * @return The chess chess piece
     */
    public abstract String getName();

    /**
     * Get the chess image resource
     *
     * @return the chess image resource
     */
    public abstract URL getResourceImage();

    /**
     * Find all the positions that the chess piece would normally do (regardless of other chess pieces)
     *
     * @return all the positions that the chess piece would normally do
     */
    protected abstract ArrayList<Pos> findChessPieceMove();

    /**
     * Search all position that chess pieces can move
     *
     * @return all position that chess pieces can move
     */
    public ArrayList<Pos> findValidMove() {
        ArrayList<Pos> validMove = new ArrayList<>();

        if (game.getColorTurn().equals(getChessColor())) {
            ArrayList<Pos> possibleMoves = findChessPieceMove();

            for (Map.Entry<Pos, BaseChessPiece> entrySet : game.getChessPieces(this.chessColor).entrySet()) {
                possibleMoves.remove(entrySet.getKey());
            }

            validMove = possibleMoves;
        }
        return validMove;
    }

    /**
     * Check if the chess pieces can move
     *
     * @param from The new position
     * @param to   The new position
     */
    public boolean canMove(Pos from, Pos to) {
        if (!game.getGameStatus().equals(Game.GameStatus.IN_GAME)) {
            return false;
        }

        if (!game.getColorTurn().equals(getChessColor())) {
            return false;
        }

        if (from == null || to == null) {
            return false;
        }

        if (!this.findValidMove().contains(to)) {
            return false;
        }

        return true;
    }

    /**
     * Get the chess piece color
     *
     * @return he chess piece color
     */
    public Game.ChessColor getChessColor() {
        return this.chessColor;
    }
}
