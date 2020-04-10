package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.game.Pos;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Optional;

public class Knight extends BaseChessPiece {

    /**
     * {@inheritDoc}
     */
    public Knight(Game game, Game.ChessColor chessColor) throws InvalidParameterException {
        super(game, chessColor);
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
        return this.getClass().getResource(String.format("/img/chesspieces/knight_%s.png", this.getChessColor().toString().toLowerCase()));
    }

    /**
     * {@inheritDoc}
     *
     * @param currentPos
     */
    @Override
    protected ArrayList<Pos> findChessPieceMoves(Pos currentPos) {
        ArrayList<Pos> validMoves = new ArrayList<>();

        int[] xs = {+2, +2, -2, -2, -1, -1, +1, +1};
        int[] ys = {-1, +1, -1, +1, +2, -2, +2, +2};

        for (int i = 0; i < xs.length; i++) {
            try {
                int x = currentPos.getX() + xs[i];
                int y = currentPos.getY() + ys[i];
                validMoves.add(new Pos(x, y));
            } catch (IndexOutOfBoundsException ignored) {
            }
        }
        return validMoves;
    }

    /**
     * Not specifics directions, really specific moves
     *
     * @return
     */
    @Override
    protected Pos.Direction[] getAvailableDirections() {
        return new Pos.Direction[0];
    }

    /**
     * Not specifics limit, really specific moves
     *
     * @param currentPosition
     * @return
     */
    @Override
    protected Optional<Integer> getLimitMove(Pos currentPosition) {
        return Optional.empty();
    }

}
