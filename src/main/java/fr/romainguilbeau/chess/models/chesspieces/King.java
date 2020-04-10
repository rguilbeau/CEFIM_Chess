package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;

public class King extends BaseChessPiece {

    /**
     * {@inheritDoc}
     */
    public King(Game game, ChessPosition position, Game.ChessColor chessColor) throws InvalidParameterException {
        super(game, position, chessColor);
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
        return this.getClass().getResource(String.format("/img/chesspieces/king_%s.png", this.getChessColor().toString().toLowerCase()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected ArrayList<ChessPosition> findChessPieceMove() {
        ArrayList<ChessPosition> validMoves = new ArrayList<>();

        for (int x = 0; x < ChessPosition.BOARD_SIZE.x; x++) {
            for (int y = 0; y < ChessPosition.BOARD_SIZE.y; y++) {
                validMoves.add(new ChessPosition(x, y));
            }
        }
        return validMoves;
    }
}
