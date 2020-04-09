package fr.romainguilbeau.chess.models.pawns;

import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;

import java.net.URL;
import java.security.InvalidParameterException;

public class Rook extends BasePawn {

    /**
     * {@inheritDoc}
     */
    public Rook(Game game, ChessPosition position, Game.PawnColor pawnColor) throws InvalidParameterException {
        super(game, position, pawnColor);
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
        return this.getClass().getResource(String.format("/img/pawns/rook_%s.png", this.getPawnColor().toString().toLowerCase()));
    }
}
