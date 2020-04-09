package fr.romainguilbeau.chess.models.pawns;

import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Base of all chess pawn
 */
public abstract class BasePawn {

    /**
     * Chess position
     */
    private ChessPosition position;
    /**
     * The chess pawns color
     */
    private final Game.PawnColor pawnColor;
    /**
     * The current game
     */
    private final Game game;

    /**
     * Create new chess pawns
     *
     * @param game      The current game
     * @param position  The current position of this pawns
     * @param pawnColor The color of this pawns
     * @throws InvalidParameterException If invalid arguments
     */
    BasePawn(Game game, ChessPosition position, Game.PawnColor pawnColor) throws InvalidParameterException {
        if (position == null) {
            throw new NullPointerException();
        }

        if (pawnColor == null) {
            throw new NullPointerException();
        }

        if (game == null) {
            throw new NullPointerException();
        }

        this.game = game;
        this.position = position;
        this.pawnColor = pawnColor;
    }

    /**
     * Get the chess pawns name
     *
     * @return The chess pawns
     */
    public abstract String getName();

    /**
     * Get the chess image resource
     *
     * @return the chess image resource
     */
    public abstract URL getResourceImage();

    /**
     * Search all position that chess pawns can move
     *
     * @return all position that chess pawns can move
     */
    public ArrayList<ChessPosition> findPossibleMove() {
        ArrayList<ChessPosition> validMoves = new ArrayList<>();

        for (int x = 0; x < Game.BOARD_SIZE.x; x++) {
            for (int y = 0; y < Game.BOARD_SIZE.y; y++) {
                validMoves.add(new ChessPosition(x, y));
            }
        }

        for (BasePawn pawns : this.game.getPawns(this.pawnColor)) {
            if (pawns.getPosition().isPresent()) {
                validMoves.remove(pawns.getPosition().get());
            }
        }

        return (ArrayList<ChessPosition>) validMoves.clone();
    }

    /**
     * Return the current position of this chess pawns.
     * If optional is empty, it means that this chess pawns was eaten
     *
     * @return the current position of this chess pawns
     */
    public Optional<ChessPosition> getPosition() {
        if (position == null) {
            return Optional.empty();
        } else {
            return Optional.of((ChessPosition) position.clone());
        }
    }

    /**
     * Move the chess pawns
     *
     * @param chessPosition The new position
     * @throws Exception If invalid position
     */
    public void move(ChessPosition chessPosition) throws Exception {
        if (chessPosition == null) {
            throw new NullPointerException();
        }

        if (this.position == null) {
            throw new Exception("Unable to move dead pawn");
        }

        if (!this.findPossibleMove().contains(chessPosition)) {
            throw new Exception(String.format("Unable to move to %d;%d position", chessPosition.x, chessPosition.y));
        }

        Game.PawnColor opposingColor = Game.PawnColor.BLACK;
        if (this.pawnColor.equals(Game.PawnColor.BLACK)) {
            opposingColor = Game.PawnColor.WHITE;
        }

        for (BasePawn pawn : this.game.getPawns(opposingColor)) {
            if (pawn.getPosition().isPresent() && pawn.getPosition().get().equals(chessPosition)) {
                pawn.getsEaten();
            }
        }

        this.position = (ChessPosition) chessPosition.clone();
    }

    /**
     * Put this chess pawn in eaten state
     * (Optional getPosition() return empty)
     */
    public void getsEaten() {
        System.out.println("Eated !!");
        this.position = null;
    }

    /**
     * Get the chess pawn color
     *
     * @return he chess pawn color
     */
    public Game.PawnColor getPawnColor() {
        return this.pawnColor;
    }

}
