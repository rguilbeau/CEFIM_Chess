package fr.romainguilbeau.chess.models.pawns;

import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;

import java.net.URL;
import java.security.InvalidParameterException;

public class Knight extends BasePawn {

    /**
     * {@inheritDoc}
     */
    public Knight(Game game, ChessPosition position, Game.PawnColor pawnColor) throws InvalidParameterException {
        super(game, position, pawnColor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Cavalier";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public URL getResourceImage() {
        return this.getClass().getResource(String.format("/img/pawns/knight_%s.png", this.getPawnColor().toString().toLowerCase()));
    }
}
