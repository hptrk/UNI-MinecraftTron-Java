package persistence;

/**
 * Represents a high score entry with a player name and points.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public class HighScore {
    private String playerName;
    private int points;

    public HighScore(String playerName, int points){
        this.playerName = playerName;
        this.points = points;
    }
    public String getPlayerName() {
        return playerName;
    }
    public int getPoints() {
        return points;
    }
}