package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.game.Pos;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Optional;

/**
 * Bishop pawn
 */
public class Bishop extends BaseChessPiece {

    /**
     * {@inheritDoc}
     */
    public Bishop(Game game, Game.ChessColor chessColor) throws InvalidParameterException {
        super(game, chessColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Fou";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getResourceImage() {
        return this.getClass().getResource(String.format("/img/chesspieces/bishop_%s.png", this.getChessColor().toString().toLowerCase()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Pos.Direction[] getAvailableDirections() {
        return new Pos.Direction[]{Pos.Direction.NORTH_EAST, Pos.Direction.SOUTH_EAST, Pos.Direction.SOUTH_WEST, Pos.Direction.NORTH_WEST};
    }

    /**
     * {@inheritDoc}
     *
     * @param currentPosition
     */
    @Override
    protected Optional<Integer> getLimitMove(Pos currentPosition) {
        return Optional.empty();
    }
}
