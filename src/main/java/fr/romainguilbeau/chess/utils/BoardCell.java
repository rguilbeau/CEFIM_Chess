package fr.romainguilbeau.chess.utils;


import fr.romainguilbeau.chess.models.chesspieces.BaseChessPiece;
import fr.romainguilbeau.chess.models.game.Pos;
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
    /**
     * Classname fot highlight cell
     */
    private static final String CSS_HIGHLIGHT_CELL = "highlightcell";

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
    private BaseChessPiece pawn;
    /**
     * The cell position
     */
    private Pos position;

    /**
     * Create new cell on the game board
     *
     * @param position The cell position
     * @param isDark   true if cell is dark
     */
    public BoardCell(Pos position, boolean isDark) {
        if (position == null) {
            throw new NullPointerException();
        }

        if (isDark) {
            getStyleClass().add(CSS_DARK_CELL);
        }

        this.position = position;
    }

    /**
     * Highlight this cell
     *
     * @param highlight true for highlight
     */
    public void setHighlight(boolean highlight) {
        if (highlight) {
            getStyleClass().add(CSS_HIGHLIGHT_CELL);
        } else {
            getStyleClass().remove(CSS_HIGHLIGHT_CELL);
        }
    }

    /**
     * Set a pawn on the cell
     *
     * @param pawn The pawn to put on the cell
     */
    public void setChessPiece(BaseChessPiece pawn) {
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
    public Optional<BaseChessPiece> getChessPiece() {
        return Optional.ofNullable(this.pawn);
    }

    /**
     * Get the cell position
     *
     * @return The cell position
     */
    public Pos getPosition() {
        return this.position;
    }

    /**
     * Remove the pawn that is on the cell
     */
    public void removeChessPiece() {
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
