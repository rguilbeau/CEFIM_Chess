package fr.romainguilbeau.chess.utils;


import fr.romainguilbeau.chess.models.game.ChessPosition;
import fr.romainguilbeau.chess.models.pawns.BasePawn;
import javafx.beans.binding.Bindings;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.util.Optional;

/**
 * Represents a cell on the game board
 */
public class BoardCell extends BorderPane {

    /**
     * Classname for dark cells
     */
    private static final String CSS_DARK_CELL = "darkcell";
    /**
     * Classname for focused cells
     */
    private static final String CSS_FOCUSED_CELL = "focusedcell";

    //////// STATIC - focused cell

    /**
     * Current active focused cell (only one for all cells)
     */
    private static BoardCell focused;

    /**
     * Get the current focused cell
     *
     * @return The current focused cell
     */
    public static Optional<BoardCell> getFocused() {
        return Optional.ofNullable(focused);
    }

    //////// END STATIC

    /**
     * The pawn on the cell
     */
    private BasePawn pawn;
    /**
     * The cell position
     */
    private ChessPosition position;

    /**
     * Create new cell on the game board
     *
     * @param position The cell position
     */
    public BoardCell(ChessPosition position) {
        if (position == null) {
            throw new NullPointerException();
        }
        this.position = position;
    }

    /**
     * Set this cell dark
     *
     * @param dark true for set dark, else false
     */
    public void setDark(boolean dark) {
        if (dark) {
            getStyleClass().add(CSS_DARK_CELL);
        } else {
            getStyleClass().remove(CSS_DARK_CELL);
        }
    }

    /**
     * Set a pawn on the cell
     *
     * @param pawn The pawn to put on the cell
     */
    public void setPawn(BasePawn pawn) {
        ImageView imageView = new ImageView(pawn.getResourceImage().toString());
        imageView.fitWidthProperty().bind(Bindings.min(this.widthProperty().subtract(35), this.widthProperty()));
        imageView.fitHeightProperty().bind(Bindings.min(this.widthProperty().subtract(35), this.widthProperty()));
        imageView.setPreserveRatio(true);
        this.setCenter(imageView);
        this.pawn = pawn;
    }

    /**
     * Get the pawn on the cell
     *
     * @return the pawn on the cell (empty if no pawn)
     */
    public Optional<BasePawn> getPawn() {
        return Optional.ofNullable(this.pawn);
    }

    /**
     * Get the cell position
     *
     * @return The cell position
     */
    public ChessPosition getPosition() {
        return (ChessPosition) this.position.clone();
    }

    /**
     * Remove the pawn that is on the cell
     */
    public void removePawn() {
        this.pawn = null;
        this.setCenter(null);
    }

    /**
     * Set or not this cell as the only focused cell
     *
     * @param focusedCell true for set focused, else false
     */
    public void setFocusedCell(boolean focusedCell) {
        if (focusedCell) {
            if (getFocused().isPresent()) {
                getFocused().get().setFocusedCell(false);
            }
            this.getStyleClass().add(CSS_FOCUSED_CELL);
            focused = this;
        } else {
            this.getStyleClass().remove(CSS_FOCUSED_CELL);
            focused = null;
        }

    }
}
