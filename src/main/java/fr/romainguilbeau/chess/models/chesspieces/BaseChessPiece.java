package fr.romainguilbeau.chess.models.chesspieces;

import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.listeners.ChessPieceEatenListener;
import fr.romainguilbeau.chess.models.listeners.ChessPieceMoveListener;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Base of all chess piece
 */
public abstract class BaseChessPiece implements Cloneable {

    /**
     * Chess piece position
     */
    private ChessPosition position;
    /**
     * The chess piece color
     */
    private final Game.ChessColor chessColor;
    /**
     * The current game
     */
    private final Game game;
    /**
     * All chess move listeners
     */
    private ArrayList<ChessPieceMoveListener> chessPieceMoveListeners;
    /**
     * All chess move listeners
     */
    private ArrayList<ChessPieceEatenListener> chessPieceEatenListeners;


    /**
     * Create new chess piece
     *
     * @param game       The current game
     * @param position   The current position of this piece
     * @param chessColor The color of this piece
     * @throws InvalidParameterException If invalid arguments
     */
    BaseChessPiece(Game game, ChessPosition position, Game.ChessColor chessColor) throws InvalidParameterException {
        if (position == null) {
            throw new NullPointerException();
        }

        if (chessColor == null) {
            throw new NullPointerException();
        }

        if (game == null) {
            throw new NullPointerException();
        }

        this.game = game;
        this.position = position;
        this.chessColor = chessColor;
        this.chessPieceMoveListeners = new ArrayList<>();
        this.chessPieceEatenListeners = new ArrayList<>();
    }

    /**
     * Get the chess chess piece name
     *
     * @return The chess chess piece
     */
    public abstract String getName();

    /**
     * Get the chess image resource
     *
     * @return the chess image resource
     */
    public abstract URL getResourceImage();

    /**
     * Find all the positions that the chess piece would normally do (regardless of other chess pieces)
     *
     * @return all the positions that the chess piece would normally do
     */
    protected abstract ArrayList<ChessPosition> findChessPieceMove();

    /**
     * Search all position that chess pieces can move
     *
     * @return all position that chess pieces can move
     */
    public ArrayList<ChessPosition> findValidMove() {
        ArrayList<ChessPosition> validMove = new ArrayList<>();

        if (game.getColorTurn().equals(getChessColor())) {
            ArrayList<ChessPosition> possibleMove = findChessPieceMove();

            for (BaseChessPiece chessPieces : this.game.getChessPieces(this.chessColor)) {
                if (chessPieces.getPosition().isPresent()) {
                    possibleMove.remove(chessPieces.getPosition().get());
                }
            }
            validMove = (ArrayList<ChessPosition>) possibleMove.clone();
        }
        return validMove;
    }

    /**
     * Return the current position of this chess pieces.
     * If optional is empty, it means that this chess pieces was eaten
     *
     * @return the current position of this chess pieces
     */
    public Optional<ChessPosition> getPosition() {
        if (position == null) {
            return Optional.empty();
        } else {
            return Optional.of((ChessPosition) position.clone());
        }
    }

    /**
     * Move the chess pieces
     *
     * @param chessPosition The new position
     * @throws Exception If invalid position
     */
    public void move(ChessPosition chessPosition) throws Exception {
        if (!game.getGameStatus().equals(Game.GameStatus.IN_GAME)) {
            throw new Exception("Not \"in game\"");
        }

        if (!game.getColorTurn().equals(getChessColor())) {
            throw new Exception("It's not your turn !");
        }

        if (chessPosition == null) {
            throw new NullPointerException();
        }

        if (this.getPosition().isEmpty()) {
            throw new Exception("Unable to move dead chess pieces");
        }

        if (!this.findValidMove().contains(chessPosition)) {
            throw new Exception(String.format("Unable to move to %d;%d position", chessPosition.x, chessPosition.y));
        }

        Game.ChessColor opposingColor = Game.ChessColor.BLACK;
        if (this.chessColor.equals(Game.ChessColor.BLACK)) {
            opposingColor = Game.ChessColor.WHITE;
        }

        BaseChessPiece chessEaten;
        for (BaseChessPiece chessPiece : this.game.getChessPieces(opposingColor)) {
            if (chessPiece.getPosition().isPresent() && chessPiece.getPosition().get().equals(chessPosition)) {
                chessEaten = chessPiece;
                chessEaten.eaten();
            }
        }

        ChessPosition previousPosition = (ChessPosition) this.position.clone();
        this.position = (ChessPosition) chessPosition.clone();

        for (ChessPieceMoveListener chessPieceMoveListener : chessPieceMoveListeners) {
            chessPieceMoveListener.onChessPieceMove(this);
        }
    }

    /**
     * Put this chess piece in eaten state and invoke all listeners
     * (Optional getPosition() return empty)
     */
    public void eaten() {
        this.position = null;

        for (ChessPieceEatenListener chessPieceEatenListener : chessPieceEatenListeners) {
            chessPieceEatenListener.onChessPieceEaten(this);
        }
    }

    /**
     * Get the chess piece color
     *
     * @return he chess piece color
     */
    public Game.ChessColor getChessColor() {
        return this.chessColor;
    }

    /**
     * Add chess piece move listener
     *
     * @param chessPieceMoveListener chess piece move listener
     */
    public void addChessPieceMoveListener(ChessPieceMoveListener chessPieceMoveListener) {
        chessPieceMoveListeners.add(chessPieceMoveListener);
    }

    /**
     * Add chess piece eaten listener
     *
     * @param chessPieceEatenListener chess piece eaten listener
     */
    public void addChessPieceEatenListener(ChessPieceEatenListener chessPieceEatenListener) {
        chessPieceEatenListeners.add(chessPieceEatenListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object clone() {
        BaseChessPiece chessPiece = null;
        try {
            chessPiece = (BaseChessPiece) super.clone();
            if (chessPiece.position != null) {
                chessPiece.position = (ChessPosition) position.clone();
            }
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace(System.err);
        }
        return chessPiece;
    }
}
