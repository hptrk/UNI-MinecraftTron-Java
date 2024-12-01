package persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the HighScoreDAO interface for managing high scores in the database.
 * Provides methods to add, get, update, and delete high scores.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public class HighScoreDAOImpl implements HighScoreDAO {
    private final Database database;

    public HighScoreDAOImpl(Database database){
        this.database = database;
    }

    @Override
    public void addHighScore(HighScore highScore){
        String query = "INSERT INTO tron_high_scores (player_name, points) VALUES (?, ?)";
        try (Connection connection = database.connect();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, highScore.getPlayerName());
            stmt.setInt(2, highScore.getPoints());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HighScore getHighScore(String playerName) {
        String query = "SELECT * FROM tron_high_scores WHERE player_name = ?";
        try (Connection connection = database.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new HighScore(resultSet.getString("player_name"), resultSet.getInt("points"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<HighScore> getAllHighScores() {
        List<HighScore> highScores = new ArrayList<>();
        String query = "SELECT * FROM tron_high_scores";
        try (Connection connection = database.connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                highScores.add(new HighScore(resultSet.getString("player_name"), resultSet.getInt("points")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highScores;
    }

    @Override
    public void updateHighScore(HighScore highScore) {
        String query = "UPDATE tron_high_scores SET points = ? WHERE player_name = ?";
        try (Connection connection = database.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, highScore.getPoints());
            statement.setString(2, highScore.getPlayerName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteHighScore(String playerName) {
        String query = "DELETE FROM tron_high_scores WHERE player_name = ?";
        try (Connection connection = database.connect();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}