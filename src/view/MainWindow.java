package view;

import model.Direction;
import model.Game;
import model.GameStatus;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

 /**
 * Main window for the game, handling game initialization, player input, and game status updates.
 * Displays the game map and manages the game loop.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public class MainWindow extends JFrame {
    private JLabel timePassedLabel;
    private Game game;
    private MapPanel mapPanel;
    private double secondsPassed = 0;

    private static final int REFRESH_RATE = 100;

    public static void main(String[] args) {
        try {
            new MainWindow();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainWindow() throws IOException {
        // Window
        setupWindow();

        // Game initialization with player names from dialog
        String[] playerNames = getPlayerNamesFromDialog();
        initializeGame(playerNames);

        // Map panel initialization (view)
        initializeMapPanel();

        // Key listeners for player movement
        setKeyListeners();

        setTimer(REFRESH_RATE);

        finalizeWindow();
    }
    public void startNewGame() {
        initializeGame(new String[]{game.getPlayer1().getName(), game.getPlayer2().getName()});
        initializeMapPanel();
        secondsPassed = 0;
        timePassedLabel.setText("Eltelt idő: 00:00");
        setTimer(REFRESH_RATE);
    }

    private void setupWindow() {
        setTitle("Minecraft Tron");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("assets/icon.png")));
        setLayout(new BorderLayout());
        createTimeLabel();
    }

    private void createTimeLabel() {
        timePassedLabel = new JLabel("Eltelt idő: 00:00");
        timePassedLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timePassedLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(timePassedLabel, BorderLayout.NORTH);
    }

    private String[] getPlayerNamesFromDialog() {
        PlayerNameDialog playerNameDialog = new PlayerNameDialog(this);
        String player1Name = playerNameDialog.getPlayer1Name();
        String player2Name = playerNameDialog.getPlayer2Name();
        if (player1Name.isEmpty() || player2Name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "A játékosok nevét kötelező megadni!", "Hiba", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        return new String[]{player1Name, player2Name};
    }

    private void initializeGame(String[] playerNames) {
        game = new Game(new Player(playerNames[0], 1, 1, Direction.RIGHT),
                new Player(playerNames[1], 23, 40, Direction.LEFT));
    }

    private void initializeMapPanel() {
        mapPanel = new MapPanel(Game.rows, Game.cols, game.getMap());
        add(mapPanel, BorderLayout.CENTER);
    }

    private void setKeyListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                handleKeyPress(ke.getKeyCode());
            }
        });
    }

    private void handleKeyPress(int keyCode) {
        Direction direction1 = null;
        Direction direction2 = null;

        switch (keyCode) {
            case KeyEvent.VK_A -> direction1 = Direction.LEFT;
            case KeyEvent.VK_D -> direction1 = Direction.RIGHT;
            case KeyEvent.VK_W -> direction1 = Direction.UP;
            case KeyEvent.VK_S -> direction1 = Direction.DOWN;
            case KeyEvent.VK_LEFT -> direction2 = Direction.LEFT;
            case KeyEvent.VK_RIGHT -> direction2 = Direction.RIGHT;
            case KeyEvent.VK_UP -> direction2 = Direction.UP;
            case KeyEvent.VK_DOWN -> direction2 = Direction.DOWN;
        }

        if (direction1 != null) {
            game.getPlayer1().setDirection(direction1);
        }
        if (direction2 != null) {
            game.getPlayer2().setDirection(direction2);
        }
    }

    private void setTimer(int delay) {
        Timer timer = new Timer(delay, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGameStatus((Timer) e.getSource());
            }
        });
        timer.start();
    }

    private void updateGameStatus(Timer timer) {
        // Move players and repaint map
        GameStatus status = game.movePlayers();
        mapPanel.repaint();
        updateTimePassed();
        if (status != GameStatus.CONTINUE) {
            handleGameEnd(status);
            timer.stop();
        }
    }

    private void updateTimePassed() {
        secondsPassed += REFRESH_RATE / 1000.0;
        timePassedLabel.setText("Eltelt idő: " + getTimePassedFormatted());
    }

    private void handleGameEnd(GameStatus status) {
        String message = switch (status) {
            case PLAYER1_WINS -> game.getPlayer1().getName() + " nyert " + getTimePassedFormatted() + " alatt!";
            case PLAYER2_WINS -> game.getPlayer2().getName() + " nyert " + getTimePassedFormatted() + " alatt!";
            case DRAW -> "Döntetlen " + getTimePassedFormatted() + " alatt!";
            default -> "";
        };
        int option = JOptionPane.showOptionDialog(this, message, "Játék vége",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"Új játék", "Toplista", "Kilépés"}, "Új játék");

        if (option == 0) {
            startNewGame();
        } else if (option == 1) {
            showHighScores();
        } else if (option == 2 || option == JOptionPane.CLOSED_OPTION) {
            System.exit(0);
        }
    }

    private String getTimePassedFormatted() {
        return String.format("%02d:%02d", (int) secondsPassed / 60, (int) secondsPassed % 60);
    }


    private void showHighScores() {
        HighScoreView highScoreView = new HighScoreView(this);
        highScoreView.setVisible(true);
    }

    private void finalizeWindow() {
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
