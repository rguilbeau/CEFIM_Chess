package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Base of all chess piece
 */
public abstract class BaseChessPiece {

    /**
     * Chess piece position
     */
    private ChessPosition position;
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
     * @param position   The current position of this piece
     * @param chessColor The color of this piece
     * @throws InvalidParameterException If invalid arguments
     */
    BaseChessPiece(Game game, ChessPosition position, Game.ChessColor chessColor) throws InvalidParameterException {
        if (position == null) {
            throw new NullPointerException();
        }

        if (chessColor == null) {
            throw new NullPointerException();
        }

        if (game == null) {
            throw new NullPointerException();
        }

        this.game = game;
        this.position = position;
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
     * Search all position that chess pieces can move
     *
     * @return all position that chess pieces can move
     */
    public ArrayList<ChessPosition> findPossibleMove() {
        ArrayList<ChessPosition> validMoves = new ArrayList<>();

        for (int x = 0; x < Game.BOARD_SIZE.x; x++) {
            for (int y = 0; y < Game.BOARD_SIZE.y; y++) {
                validMoves.add(new ChessPosition(x, y));
            }
        }

        for (BaseChessPiece chessPieces : this.game.getChessPieces(this.chessColor)) {
            if (chessPieces.getPosition().isPresent()) {
                validMoves.remove(chessPieces.getPosition().get());
            }
        }

        return (ArrayList<ChessPosition>) validMoves.clone();
    }

    /**
     * Return the current position of this chess pieces.
     * If optional is empty, it means that this chess pieces was eaten
     *
     * @return the current position of this chess pieces
     */
    public Optional<ChessPosition> getPosition() {
        if (position == null) {
            return Optional.empty();
        } else {
            return Optional.of((ChessPosition) position.clone());
        }
    }

    /**
     * Move the chess pieces
     *
     * @param chessPosition The new position
     * @throws Exception If invalid position
     */
    public void move(ChessPosition chessPosition) throws Exception {
        if (chessPosition == null) {
            throw new NullPointerException();
        }

        if (this.position == null) {
            throw new Exception("Unable to move dead chess pieces");
        }

        if (!this.findPossibleMove().contains(chessPosition)) {
            throw new Exception(String.format("Unable to move to %d;%d position", chessPosition.x, chessPosition.y));
        }

        Game.ChessColor opposingColor = Game.ChessColor.BLACK;
        if (this.chessColor.equals(Game.ChessColor.BLACK)) {
            opposingColor = Game.ChessColor.WHITE;
        }

        for (BaseChessPiece chessPiece : this.game.getChessPieces(opposingColor)) {
            if (chessPiece.getPosition().isPresent() && chessPiece.getPosition().get().equals(chessPosition)) {
                chessPiece.getsEaten();
            }
        }

        this.position = (ChessPosition) chessPosition.clone();
    }

    /**
     * Put this chess piece in eaten state
     * (Optional getPosition() return empty)
     */
    public void getsEaten() {
        System.out.println("Eated !!");
        this.position = null;
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
