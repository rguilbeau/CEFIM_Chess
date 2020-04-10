package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.game.Pos;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;

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
    protected ArrayList<Pos> findChessPieceMove() {
        ArrayList<Pos> validMoves = new ArrayList<>();

        for (int x = 0; x < Pos.BOARD_SIZE.x; x++) {
            for (int y = 0; y < Pos.BOARD_SIZE.y; y++) {
                validMoves.add(new Pos(x, y));
            }
        }
        return validMoves;
    }

}
