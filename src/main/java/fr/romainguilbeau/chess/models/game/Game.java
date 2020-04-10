package fr.romainguilbeau.chess.models.game;

import fr.romainguilbeau.chess.models.chesspieces.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Chess game
 */
public class Game {

    /**
     * The current player that could playing
     */
    private ChessColor colorTurn;
    /**
     * All player 1 's chess pieces
     */
    private BaseChessPiece[] whiteChessPieces;
    /**
     * All player 2 's chess pieces
     */
    private BaseChessPiece[] blackChessPieces;
    /**
     * All game states (for undo)
     */
    private ArrayList<GameState> gameStates;
    /**
     * Current game status
     */
    private GameStatus gameStatus;

    /**
     * Create new game
     */
    public Game() {
        this.colorTurn = ChessColor.WHITE;
        this.whiteChessPieces = new BaseChessPiece[16];
        this.blackChessPieces = new BaseChessPiece[16];
        this.gameStates = new ArrayList<>();
        this.gameStatus = GameStatus.IN_GAME;

        populateBoard();
        gameStates.add(new GameState(whiteChessPieces, blackChessPieces, colorTurn));
    }

    /**
     * Populate the board with chess pieces (with initial positions)
     */
    private void populateBoard() {
        for (ChessColor color : ChessColor.values()) {

            BaseChessPiece[] chessPieces = whiteChessPieces;
            if (color.equals(ChessColor.BLACK)) {
                chessPieces = blackChessPieces;
            }

            int chessPieceY = color == ChessColor.BLACK ? 0 : 7;
            int pawnY = color == ChessColor.BLACK ? 1 : 6;

            chessPieces[0] = new Rook(this, new ChessPosition(0, chessPieceY), color);
            chessPieces[1] = new Knight(this, new ChessPosition(1, chessPieceY), color);
            chessPieces[2] = new Bishop(this, new ChessPosition(2, chessPieceY), color);
            chessPieces[3] = new King(this, new ChessPosition(3, chessPieceY), color);
            chessPieces[4] = new Queen(this, new ChessPosition(4, chessPieceY), color);
            chessPieces[5] = new Bishop(this, new ChessPosition(5, chessPieceY), color);
            chessPieces[6] = new Knight(this, new ChessPosition(6, chessPieceY), color);
            chessPieces[7] = new Rook(this, new ChessPosition(7, chessPieceY), color);

            for (int x = 0; x < ChessPosition.BOARD_SIZE.x; x++) {
                chessPieces[8 + x] = new Pawn(this, new ChessPosition(x, pawnY), color);
            }
        }

        // Add move listener
        for (BaseChessPiece chessPiece : this.getChessPieces()) {
            chessPiece.addChessPieceMoveListener(this::onChessPieceMoved);
            chessPiece.addChessPieceEatenListener(this::onChessPieceEaten);
        }
    }

    /**
     * Get the player whose turn it is
     *
     * @return Get the player whose turn it is
     */
    public ChessColor getColorTurn() {
        return this.colorTurn;
    }

    /**
     * Get all chess chess pieces
     *
     * @return All player 2 chess chess pieces
     */
    public BaseChessPiece[] getChessPieces() {
        return Stream.concat(Arrays.stream(whiteChessPieces.clone()), Arrays.stream(blackChessPieces.clone()))
                .toArray(BaseChessPiece[]::new);
    }

    /**
     * Get all chess pieces by color
     *
     * @param color The color of chess pieces
     * @return All chess pieces by color
     */
    public BaseChessPiece[] getChessPieces(ChessColor color) {
        if (color.equals(ChessColor.WHITE)) {
            return whiteChessPieces.clone();
        } else {
            return blackChessPieces.clone();
        }
    }

    /**
     * Change the player turn
     * Invoked when chess move
     *
     * @param chessPiece moved chess piece
     */
    public void onChessPieceMoved(BaseChessPiece chessPiece) {
        if (colorTurn.equals(ChessColor.WHITE)) {
            colorTurn = ChessColor.BLACK;
        } else {
            colorTurn = ChessColor.WHITE;
        }
        gameStates.add(new GameState(whiteChessPieces, blackChessPieces, colorTurn));
    }

    /**
     * Piece is eaten, check if win
     *
     * @param chessPiece Eaten chess piece
     */
    public void onChessPieceEaten(BaseChessPiece chessPiece) {
        if (chessPiece instanceof King) {
            if (chessPiece.getChessColor().equals(ChessColor.WHITE)) {
                gameStatus = GameStatus.BLACK_WIN;
            } else {
                gameStatus = GameStatus.WHITE_WIN;
            }
        }
    }

    /**
     * Undo
     */
    public void undo() {
        if (gameStates.size() > 1) {
            gameStates.remove(gameStates.size() - 1);

            GameState gameState = gameStates.get(gameStates.size() - 1);
            this.colorTurn = gameState.getColorTurn();
            this.whiteChessPieces = gameState.getWhiteChessPieces();
            this.blackChessPieces = gameState.getBlackChessPieces();
        }
    }

    /**
     * Get the current game status
     *
     * @return The current game status
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * All chess colors
     */
    public enum ChessColor {
        BLACK, WHITE
    }

    /**
     * List winner
     */
    public enum GameStatus {
        IN_GAME, WHITE_WIN, BLACK_WIN
    }
}
