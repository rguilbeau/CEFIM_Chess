package fr.romainguilbeau.chess.models.game;

/**
 *
 */
public class Player {

    /**
     * Player name
     */
    private final String playerName;
    /**
     * Chess color
     */
    private final Game.PawnColor color;

    /**
     * Create new player
     *
     * @param playerName The planer name
     * @param color      The chess colot
     */
    public Player(String playerName, Game.PawnColor color) {
        if (playerName == null) {
            throw new NullPointerException();
        }

        if (color == null) {
            throw new NullPointerException();
        }

        this.playerName = playerName;
        this.color = color;
    }

    /**
     * Return the player name
     *
     * @return The player name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Get the player color
     *
     * @return The player color
     */
    public Game.PawnColor getColor() {
        return color;
    }
}
