package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.game.Pos;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

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
    protected Game game;

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
     * @param currentPos
     * @return all the positions that the chess piece would normally do
     */
    protected ArrayList<Pos> findChessPieceMoves(Pos currentPos) {
        ArrayList<Pos> validMoves = new ArrayList<>();

        for (Pos.Direction direction : getAvailableDirections()) {
            Optional<Integer> limitMove = getLimitMove(currentPos);

            Pos searchPos = currentPos;
            boolean availablePosition = true;
            int moves = 0;

            do {
                try {
                    searchPos = searchPos.incrementPos(direction);
                    moves++;
                    if (game.getChessPieces().containsKey(searchPos)) {
                        availablePosition = false;
                        if (!game.getChessPieces().get(searchPos).getChessColor().equals(getChessColor())) {
                            validMoves.add(searchPos);
                        }
                    } else {
                        validMoves.add(searchPos);
                    }

                    if (limitMove.isPresent() && limitMove.get() <= moves) {
                        availablePosition = false;
                    }
                } catch (IndexOutOfBoundsException ex) {
                    availablePosition = false;
                }
            } while (availablePosition);
        }
        return validMoves;
    }

    /**
     * Search all position that chess pieces can move
     *
     * @param currentPos
     * @return all position that chess pieces can move
     */
    public ArrayList<Pos> findValidMoves(Pos currentPos) {
        ArrayList<Pos> validMove = new ArrayList<>();

        if (game.getColorTurn().equals(getChessColor())) {
            ArrayList<Pos> possibleMoves = findChessPieceMoves(currentPos);

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
            System.err.println("Game is not in game");
            return false;
        }

        if (!game.getColorTurn().equals(getChessColor())) {
            System.err.println("It's not your turn");
            return false;
        }

        if (from == null || to == null) {
            System.err.println("Bad from or to values");
            return false;
        }

        if (!this.findValidMoves(from).contains(to)) {
            System.err.println("Not valid position");
            return false;
        }

        return true;
    }

    /**
     * Get all chess piece available directions
     *
     * @return all chess piece available directions
     */
    protected abstract Pos.Direction[] getAvailableDirections();

    /**
     * Get cell number limit move (empty optional if not limit)
     *
     * @param currentPosition
     * @return Get cell number limit move
     */
    protected abstract Optional<Integer> getLimitMove(Pos currentPosition);

    /**
     * Get the chess piece color
     *
     * @return he chess piece color
     */
    public Game.ChessColor getChessColor() {
        return this.chessColor;
    }
}
