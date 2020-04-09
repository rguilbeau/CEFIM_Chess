package fr.romainguilbeau.chess.controllers;

import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.game.Game;
import fr.romainguilbeau.chess.models.game.Player;
import fr.romainguilbeau.chess.models.pawns.BasePawn;
import fr.romainguilbeau.chess.utils.BoardCell;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
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
        // For keep board ratio when window is resized
        hBoxRootBoard.widthProperty().addListener(this::keepBoardRatio);
        hBoxRootBoard.heightProperty().addListener(this::keepBoardRatio);

        // Add buttons listener
        buttonNewGame.setOnMouseClicked(this::buttonNewGame);
        buttonQuit.setOnMouseClicked(event -> System.exit(0));

        // Create the board grid
        int index = 0;
        for (int row = 0; row < Game.BOARD_SIZE.x; row++) {
            for (int col = 0; col < Game.BOARD_SIZE.y; col++) {
                BoardCell boardCell = new BoardCell(new ChessPosition(col, row));
                boardCell.setDark(index % 2 == 0);
                // Add listener for selects and moves
                boardCell.setOnMouseClicked(this::onBoardCellClicked);
                gridPaneChessBoard.add(boardCell, col, row);
                index++;
            }
            index++;
        }
    }

    /**
     * Start a new game when button was clicked
     *
     * @param e mouse event
     */
    private void buttonNewGame(MouseEvent e) {
        game = new Game(new Player("Toto", Game.PawnColor.BLACK), new Player("Toto", Game.PawnColor.WHITE));

        ObservableList<Node> nodes = gridPaneChessBoard.getChildren();
        for (BasePawn pawn : game.getPawns()) {
            Optional<ChessPosition> optionalPos = pawn.getPosition();

            if (optionalPos.isEmpty()) {
                continue;
            }

            int index = (optionalPos.get().y * Game.BOARD_SIZE.x) + optionalPos.get().x;
            BoardCell boardCell = (BoardCell) nodes.get(index);
            boardCell.setPawn(pawn);
        }
    }

    /**
     * Select or move a pawn when cell was clicked
     *
     * @param event Mouse event
     */
    private void onBoardCellClicked(MouseEvent event) {
        if (event.getSource() instanceof BoardCell) {
            if (BoardCell.getFocused().isEmpty()) {
                onSelectPawn(event);
            } else {
                onMovePawn(event);
            }
        }
    }

    /**
     * Select a pawn
     *
     * @param event Mouse event
     */
    private void onSelectPawn(MouseEvent event) {
        if (BoardCell.getFocused().isEmpty()) {
            BoardCell boardCell = (BoardCell) event.getSource();

            if (boardCell.getPawn().isPresent()) {
                boardCell.setFocusedCell(true);
            }
        }
    }

    /**
     * Move pawn
     *
     * @param event Mouse event
     */
    private void onMovePawn(MouseEvent event) {
        Optional<BoardCell> optionalFocusedCell = BoardCell.getFocused();
        if (optionalFocusedCell.isPresent() && optionalFocusedCell.get().getPawn().isPresent()) {

            BoardCell boardCell = (BoardCell) event.getSource();
            BoardCell focusedCell = optionalFocusedCell.get();
            BasePawn pawn = focusedCell.getPawn().get();

            ChessPosition newPosition = boardCell.getPosition();

            try {
                pawn.move(newPosition);

                focusedCell.removePawn();

                ObservableList<Node> nodes = gridPaneChessBoard.getChildren();
                int newIndex = (newPosition.y * Game.BOARD_SIZE.x) + newPosition.x;
                BoardCell newBoardCell = (BoardCell) nodes.get(newIndex);
                newBoardCell.setPawn(pawn);
            } catch (Exception e) {
                System.err.println(e.getMessage());

                if (focusedCell.getPosition().equals(newPosition)) {
                    focusedCell.setFocusedCell(false);
                }
            }
            focusedCell.setFocusedCell(false);
        }
    }

    /**
     * For keep board ratio when window is resized
     *
     * @param observableValue observable Value
     * @param oldSceneHeight  old Height
     * @param newSceneHeight  new Height
     */
    private void keepBoardRatio(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
        double size = Math.min(hBoxRootBoard.getHeight(), hBoxRootBoard.getWidth());
        gridPaneChessBoard.setPrefHeight(size);
        gridPaneChessBoard.setPrefWidth(size);
    }
}
