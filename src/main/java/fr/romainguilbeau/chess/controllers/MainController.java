package fr.romainguilbeau.chess.controllers;

import fr.romainguilbeau.chess.models.chesspieces.BaseChessPiece;
import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.utils.BoardCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Main window controller
 */
public class MainController implements Initializable {

    /**
     * The play board (GridPane) container
     */
    @FXML
    private HBox hBoxRootBoard;
    /**
     * The play board
     */
    @FXML
    private GridPane gridPaneChessBoard;
    /**
     * The button to start a new game
     */
    @FXML
    private Button buttonNewGame;
    /**
     * The button to quit game
     */
    @FXML
    private Button buttonQuit;

    /**
     * The button to undo
     */
    @FXML
    private Button buttonUndo;
    /**
     * Label that indicates who's on turn
     */
    @FXML
    private Label labelPlayerTurn;
    /**
     * The current game
     */
    private Game game;

    /**
     * Init view
     *
     * @param location  The location
     * @param resources The Resource bundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        game = new Game();

        // For keep board ratio when window is resized
        hBoxRootBoard.widthProperty().addListener(observable -> keepBoardRatio());
        hBoxRootBoard.heightProperty().addListener(observable -> keepBoardRatio());

        // Add buttons listener
        buttonNewGame.setOnMouseClicked(event -> startNewGame());
        buttonQuit.setOnMouseClicked(event -> System.exit(0));
        buttonUndo.setOnMouseClicked(event -> undo());

        // Create the board grid
        int darkCell = 0;
        for (int row = 0; row < ChessPosition.BOARD_SIZE.x; row++) {
            for (int col = 0; col < ChessPosition.BOARD_SIZE.y; col++) {
                BoardCell boardCell = new BoardCell(new ChessPosition(col, row), darkCell % 2 == 0);
                // Add listener for selects and moves
                boardCell.setOnMouseClicked(event -> onBoardCellClicked((BoardCell) event.getSource()));
                gridPaneChessBoard.add(boardCell, col, row);
                darkCell++;
            }
            darkCell++;
        }

    }

    /**
     * Start a new game when button was clicked
     */
    private void startNewGame() {
        game = new Game();

        cleanBoardCells();

        ObservableList<Node> nodes = gridPaneChessBoard.getChildren();
        for (BaseChessPiece chessPiece : game.getChessPieces()) {
            Optional<ChessPosition> optionalPos = chessPiece.getPosition();

            if (optionalPos.isEmpty()) {
                continue;
            }

            BoardCell boardCell = findBoardCellAtPosition(optionalPos.get());
            boardCell.setChessPiece(chessPiece);
        }
        updateUIPlayerTurnLabel();
    }

    /**
     * Select or move a chess piece when cell was clicked
     *
     * @param boardCell board cell
     */
    private void onBoardCellClicked(Object boardCell) {
        if (boardCell instanceof BoardCell) {
            if (BoardCell.getFocused().isEmpty()) {
                onSelectChessPiece((BoardCell) boardCell);
            } else {
                onMoveChessPiece((BoardCell) boardCell);
            }
        }
    }

    /**
     * Revert move and reload all view
     */
    private void undo() {
        game.undo();

        cleanBoardCells();

        for (BaseChessPiece chessPiece : game.getChessPieces()) {
            Optional<ChessPosition> optionalPos = chessPiece.getPosition();
            if (optionalPos.isPresent()) {
                BoardCell boardCell = findBoardCellAtPosition(optionalPos.get());
                boardCell.setChessPiece(chessPiece);
            }
        }

        updateUIPlayerTurnLabel();
    }

    /**
     * Select a chess piece
     *
     * @param boardCell board cell
     */
    private void onSelectChessPiece(BoardCell boardCell) {
        if (game.getGameStatus().equals(Game.GameStatus.IN_GAME)
                && BoardCell.getFocused().isEmpty()
                && boardCell.getChessPiece().isPresent()
                && boardCell.getChessPiece().get().getChessColor().equals(game.getColorTurn())) {

            BaseChessPiece chessPiece = boardCell.getChessPiece().get();
            ArrayList<ChessPosition> validPositions = chessPiece.findValidMove();

            for (ChessPosition validPosition : validPositions) {
                BoardCell newBoardCell = findBoardCellAtPosition(validPosition);
                newBoardCell.setHighlight(true);
            }
            boardCell.setFocusedCell(true);
        }
    }

    /**
     * Move chess piece
     *
     * @param boardCell Mouse event
     */
    private void onMoveChessPiece(BoardCell boardCell) {
        Optional<BoardCell> optionalFocusedCell = BoardCell.getFocused();
        if (optionalFocusedCell.isPresent() && optionalFocusedCell.get().getChessPiece().isPresent()) {

            BoardCell focusedCell = optionalFocusedCell.get();
            BaseChessPiece chessPiece = focusedCell.getChessPiece().get();

            ChessPosition newPosition = boardCell.getPosition();
            boolean unsetFocus = true;

            try {
                chessPiece.move(newPosition);
                focusedCell.removeChessPiece();

                BoardCell newBoardCell = findBoardCellAtPosition(newPosition);
                newBoardCell.setChessPiece(chessPiece);
            } catch (Exception e) {
                System.err.println(e.getMessage());

                if (!focusedCell.getPosition().equals(newPosition)) {
                    unsetFocus = false;
                }
            }

            if (unsetFocus) {
                focusedCell.setFocusedCell(false);

                ObservableList<Node> nodes = gridPaneChessBoard.getChildren();
                for (Node node : nodes) {
                    if (node instanceof BoardCell) {
                        ((BoardCell) node).setHighlight(false);
                    }
                }
            }
        }
        updateUIPlayerTurnLabel();
    }

    /**
     * Find board cell at a position
     *
     * @param position The wanted position
     * @return the board cell
     * @throws IndexOutOfBoundsException if invalid index
     */
    private BoardCell findBoardCellAtPosition(ChessPosition position) throws IndexOutOfBoundsException {
        ObservableList<Node> nodes = gridPaneChessBoard.getChildren();
        int newIndex = (position.y * ChessPosition.BOARD_SIZE.x) + position.x;
        Node newBoardCell = nodes.get(newIndex);

        if (newBoardCell instanceof BoardCell) {
            return (BoardCell) newBoardCell;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Remove all chess piece from board
     */
    private void cleanBoardCells() {
        ObservableList<Node> nodes = gridPaneChessBoard.getChildren();
        for (Node node : nodes) {
            if (node instanceof BoardCell) {
                ((BoardCell) node).removeChessPiece();
            }
        }
    }

    /**
     * Update player turn label
     */
    private void updateUIPlayerTurnLabel() {
        if (game.getGameStatus().equals(Game.GameStatus.BLACK_WIN)) {
            labelPlayerTurn.setText("Les noirs ont gagnés");
        } else if (game.getGameStatus().equals(Game.GameStatus.WHITE_WIN)) {
            labelPlayerTurn.setText("Les blancs ont gagnés");
        } else if (game.getColorTurn().equals(Game.ChessColor.WHITE)) {
            labelPlayerTurn.setText("Au tour des : Blancs");
        } else {
            labelPlayerTurn.setText("Au tour des : Noirs");
        }
    }

    /**
     * For keep board ratio when window is resized
     */
    private void keepBoardRatio() {
        double size = Math.min(hBoxRootBoard.getHeight(), hBoxRootBoard.getWidth());
        gridPaneChessBoard.setPrefHeight(size);
        gridPaneChessBoard.setPrefWidth(size);
    }
}
