package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.game.Pos;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Optional;

public class Rook extends BaseChessPiece {

    /**
     * {@inheritDoc}
     */
    public Rook(Game game, Game.ChessColor chessColor) throws InvalidParameterException {
        super(game, chessColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Tour";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getResourceImage() {
        return this.getClass().getResource(String.format("/img/chesspieces/rook_%s.png", this.getChessColor().toString().toLowerCase()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Pos.Direction[] getAvailableDirections() {
        return new Pos.Direction[]{Pos.Direction.NORTH, Pos.Direction.SOUTH, Pos.Direction.EAST, Pos.Direction.WEST};
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