package persistence;

import java.util.List;

/**
 * Defines the data access object (DAO) interface for high scores.
 * Provides methods to add, get, update, and delete high scores.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public interface HighScoreDAO {
    void addHighScore(HighScore highScore);
    HighScore getHighScore(String playerName);
    List<HighScore> getAllHighScores();
    void updateHighScore(HighScore highScore);
    void deleteHighScore(String playerName);
}
