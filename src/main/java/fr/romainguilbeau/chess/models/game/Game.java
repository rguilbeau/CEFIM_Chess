package fr.romainguilbeau.chess.models.game;

import fr.romainguilbeau.chess.models.chesspieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private HashMap<Pos, BaseChessPiece> whiteChessPieces;
    /**
     * All player 2 's chess pieces
     */
    private HashMap<Pos, BaseChessPiece> blackChessPieces;
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
        this.whiteChessPieces = new HashMap<>();
        this.blackChessPieces = new HashMap<>();
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

            HashMap<Pos, BaseChessPiece> chessPieces = whiteChessPieces;
            if (color.equals(ChessColor.BLACK)) {
                chessPieces = blackChessPieces;
            }

            int chessPieceY = color == ChessColor.BLACK ? 0 : 7;
            int pawnY = color == ChessColor.BLACK ? 1 : 6;

            chessPieces.put(new Pos(0, chessPieceY), new Rook(this, color));
            chessPieces.put(new Pos(1, chessPieceY), new Knight(this, color));
            chessPieces.put(new Pos(2, chessPieceY), new Bishop(this, color));
            chessPieces.put(new Pos(3, chessPieceY), new King(this, color));
            chessPieces.put(new Pos(4, chessPieceY), new Queen(this, color));
            chessPieces.put(new Pos(5, chessPieceY), new Bishop(this, color));
            chessPieces.put(new Pos(6, chessPieceY), new Knight(this, color));
            chessPieces.put(new Pos(7, chessPieceY), new Rook(this, color));

            for (int x = 0; x < Pos.BOARD_SIZE.x; x++) {
                chessPieces.put(new Pos(x, pawnY), new Pawn(this, color, new Pos(x, pawnY)));
            }
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
     * Get all chess piece
     *
     * @return all chess piece
     */
    public HashMap<Pos, BaseChessPiece> getChessPieces() {
        HashMap<Pos, BaseChessPiece> allChessPiece = new HashMap<>();
        allChessPiece.putAll(whiteChessPieces);
        allChessPiece.putAll(blackChessPieces);
        return (HashMap<Pos, BaseChessPiece>) allChessPiece.clone();
    }

    /**
     * Move chess piece
     *
     * @param previousPosition The previous position
     * @param nextPosition     The next position
     * @throws Exception If invalid move
     */
    public void move(Pos previousPosition, Pos nextPosition) throws Exception {
        BaseChessPiece chessPiece = null;

        if (this.whiteChessPieces.containsKey(previousPosition)) {
            chessPiece = this.whiteChessPieces.get(previousPosition);
        } else if (this.blackChessPieces.containsKey(previousPosition)) {
            chessPiece = this.blackChessPieces.get(previousPosition);
        }

        if (chessPiece == null) {
            throw new Exception("No chess piece here");
        }

        if (!chessPiece.canMove(previousPosition, nextPosition)) {
            throw new Exception("This chess piece can't move");
        }

        HashMap<Pos, BaseChessPiece> playerChessPieces = this.blackChessPieces;
        if (chessPiece.getChessColor().equals(ChessColor.WHITE)) {
            playerChessPieces = this.whiteChessPieces;
        }

        ChessColor opposing = ChessColor.WHITE;
        if (chessPiece.getChessColor().equals(ChessColor.WHITE)) {
            opposing = ChessColor.BLACK;
        }

        playerChessPieces.remove(previousPosition);
        getChessPieces(opposing).remove(nextPosition);
        playerChessPieces.put(nextPosition, chessPiece);

        if (chessPiece.getChessColor().equals(ChessColor.WHITE)) {
            this.colorTurn = ChessColor.BLACK;
        } else {
            this.colorTurn = ChessColor.WHITE;
        }

        gameStates.add(new GameState(whiteChessPieces, blackChessPieces, colorTurn));
        updateGameStatus();
    }

    /**
     * Get all chess pieces by color
     *
     * @param color The color of chess pieces
     * @return All chess pieces by color
     */
    public HashMap<Pos, BaseChessPiece> getChessPieces(ChessColor color) {
        if (color.equals(ChessColor.WHITE)) {
            return (HashMap<Pos, BaseChessPiece>) whiteChessPieces.clone();
        } else {
            return (HashMap<Pos, BaseChessPiece>) blackChessPieces.clone();
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
     * Update the current game status
     */
    private void updateGameStatus() {
        for (ChessColor color : ChessColor.values()) {
            boolean kingIsDead = true;
            for (Map.Entry<Pos, BaseChessPiece> entrySet : this.getChessPieces(color).entrySet()) {
                if (entrySet.getValue() instanceof King) {
                    kingIsDead = false;
                    break;
                }
            }

            if (kingIsDead && color.equals(ChessColor.WHITE)) {
                gameStatus = GameStatus.BLACK_WIN;
            } else if (kingIsDead && color.equals(ChessColor.BLACK)) {
                gameStatus = GameStatus.WHITE_WIN;
            }
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
