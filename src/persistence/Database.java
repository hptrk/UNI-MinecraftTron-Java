package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the database connection for the high score system.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/tron_high_scores";
    private static final String USER = "user123";
    private static final String PASSWORD = "pass123";

    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to establish connection to the database.");
            e.printStackTrace();
        }
        return connection;
    }
}