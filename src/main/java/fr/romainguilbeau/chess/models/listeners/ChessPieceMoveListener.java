package fr.romainguilbeau.chess.models.listeners;

import fr.romainguilbeau.chess.models.chesspieces.BaseChessPiece;

/**
 * Event chess piece move listener
 */
public interface ChessPieceMoveListener {

    /**
     * @param baseChessPiece Chess piece that moved
     */
    void onChessPieceMove(BaseChessPiece baseChessPiece);
}
