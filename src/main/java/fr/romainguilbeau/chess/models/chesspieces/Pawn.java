package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;

import java.net.URL;
import java.security.InvalidParameterException;

public class Pawn extends BaseChessPiece {

    /**
     * {@inheritDoc}
     */
    public Pawn(Game game, ChessPosition position, Game.ChessColor chessColor) throws InvalidParameterException {
        super(game, position, chessColor);
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
}