package fr.romainguilbeau.chess.models.pawns;

import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;

import java.net.URL;
import java.security.InvalidParameterException;

public class King extends BasePawn {

    /**
     * {@inheritDoc}
     */
    public King(Game game, ChessPosition position, Game.PawnColor pawnColor) throws InvalidParameterException {
        super(game, position, pawnColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Roi";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getResourceImage() {
        return this.getClass().getResource(String.format("/img/pawns/king_%s.png", this.getPawnColor().toString().toLowerCase()));
    }
}
