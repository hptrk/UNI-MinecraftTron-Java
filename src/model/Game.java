package model;

import persistence.Database;
import persistence.HighScore;
import persistence.HighScoreDAO;
import persistence.HighScoreDAOImpl;

/**
 * Manages the game state, including player movements, map updates, and game status.
 * Handles interactions with the high score database.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public class Game {
    public static final int rows = 25;
    public static final int cols = 42;

    private final MapItem[][] map;
    private final Player player1, player2;
    private final Database database;
    private final HighScoreDAO highScoreDAO;

    public Game(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        map = new MapItem[rows][cols];
        database = new Database();
        highScoreDAO = new HighScoreDAOImpl(database);

        initMap();
    }
    public MapItem[][] getMap() {
        return map;
    }
    public Player getPlayer1() {
        return player1;
    }
    public Player getPlayer2() {
        return player2;
    }

    public GameStatus movePlayers(){
        // Trail
        map[player1.getX()][player1.getY()] = MapItem.WATER;
        map[player2.getX()][player2.getY()] = MapItem.LAVA;

        // Check for collisions with borders before moving
        if (player1.getX() + player1.getDirection().x < 0 || player1.getX() + player1.getDirection().x >= rows
                || player1.getY() + player1.getDirection().y < 0 || player1.getY() + player1.getDirection().y >= cols) {
            updateHighScore(player2.getName());
            return GameStatus.PLAYER2_WINS;
        }
        if (player2.getX() + player2.getDirection().x < 0 || player2.getX() + player2.getDirection().x >= rows
                || player2.getY() + player2.getDirection().y < 0 || player2.getY() + player2.getDirection().y >= cols) {
            updateHighScore(player1.getName());
            return GameStatus.PLAYER1_WINS;
        }

        // Move the players
        player1.move();
        player2.move();

        // Check for collisions with LAVA, WATER
        if (map[player1.getX()][player1.getY()] == MapItem.LAVA || map[player1.getX()][player1.getY()] == MapItem.WATER) {
            updateHighScore(player2.getName());
            return GameStatus.PLAYER2_WINS;
        }
        if (map[player2.getX()][player2.getY()] == MapItem.WATER || map[player2.getX()][player2.getY()] == MapItem.LAVA) {
            updateHighScore(player1.getName());
            return GameStatus.PLAYER1_WINS;
        }

        // Players collide with each other (draw)
        if (player1.getX() == player2.getX() && player1.getY() == player2.getY()) {
            return GameStatus.DRAW;
        }

        map[player1.getX()][player1.getY()] = MapItem.BOAT;
        map[player2.getX()][player2.getY()] = MapItem.MINECART;

        return GameStatus.CONTINUE;
    }

    private void updateHighScore(String playerName){
        HighScore highScore = highScoreDAO.getHighScore(playerName);
        if (highScore == null){
            highScoreDAO.addHighScore(new HighScore(playerName, 1));
        } else {
            highScoreDAO.updateHighScore(new HighScore(playerName, highScore.getPoints() + 1));
        }
    }

    private void initMap(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                map[i][j] = MapItem.EMPTY;
            }
        }
        map[player1.getX()][player1.getY()] = MapItem.BOAT;
        map[player2.getX()][player2.getY()] = MapItem.MINECART;
    }
}
