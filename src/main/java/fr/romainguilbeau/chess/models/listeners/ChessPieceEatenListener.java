package fr.romainguilbeau.chess.models.listeners;

import fr.romainguilbeau.chess.models.chesspieces.BaseChessPiece;

/**
 * Event chess piece move listener
 */
public interface ChessPieceEatenListener {

    /**
     * @param baseChessPiece Chess piece that eaten
     */
    void onChessPieceEaten(BaseChessPiece baseChessPiece);
}