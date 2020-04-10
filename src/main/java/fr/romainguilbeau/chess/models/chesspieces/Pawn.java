package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.game.Pos;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Optional;

public class Pawn extends BaseChessPiece {

    /**
     * The first position
     */
    private Pos initialPosition;

    /**
     * {@inheritDoc}
     */
    public Pawn(Game game, Game.ChessColor chessColor, Pos initialPosition) throws InvalidParameterException {
        super(game, chessColor);
        this.initialPosition = initialPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Pion";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getResourceImage() {
        return this.getClass().getResource(String.format("/img/chesspieces/pawn_%s.png", this.getChessColor().toString().toLowerCase()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Pos.Direction[] getAvailableDirections() {
        if (getChessColor().equals(Game.ChessColor.BLACK)) {
            return new Pos.Direction[]{Pos.Direction.SOUTH};
        } else {
            return new Pos.Direction[]{Pos.Direction.NORTH};
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Optional<Integer> getLimitMove(Pos currentPosition) {
        if (currentPosition.equals(initialPosition)) {
            return Optional.of(2);
        } else {
            return Optional.of(1);
        }
    }
}
