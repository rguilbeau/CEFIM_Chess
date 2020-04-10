package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.game.Pos;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Optional;

public class Queen extends BaseChessPiece {

    /**
     * {@inheritDoc}
     */
    public Queen(Game game, Game.ChessColor chessColor) throws InvalidParameterException {
        super(game, chessColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Reine";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getResourceImage() {
        return this.getClass().getResource(String.format("/img/chesspieces/queen_%s.png", this.getChessColor().toString().toLowerCase()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Pos.Direction[] getAvailableDirections() {
        return Pos.Direction.values();
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
