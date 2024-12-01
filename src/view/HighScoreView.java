package view;

import persistence.Database;
import persistence.HighScore;
import persistence.HighScoreDAO;
import persistence.HighScoreDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Comparator;
import java.util.List;

 /**
 * Displays the high scores in a table format.
 * Fetches high scores from the database and sorts them.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public class HighScoreView extends JFrame {
    private static final String TITLE = "Toplista";
    private static final int WIDTH = 400;
    private static final int HEIGHT = 400;

    public HighScoreView(MainWindow mainWindow) {
        setupWindow(mainWindow);
        List<HighScore> highScores = fetchHighScores();
        JTable table = createHighScoreTable(highScores);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void setupWindow(MainWindow mainWindow) {
        setTitle(TITLE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainWindow.startNewGame();
            }
        });
    }

    private List<HighScore> fetchHighScores() {
        Database database = new Database();
        HighScoreDAO highScoreDAO = new HighScoreDAOImpl(database);
        List<HighScore> highScores = highScoreDAO.getAllHighScores();
        highScores.sort(Comparator.comparingInt(HighScore::getPoints).reversed());
        return highScores;
    }

    private JTable createHighScoreTable(List<HighScore> highScores) {
        String[] columnNames = {"Név", "Pontszám"};
        String[][] data = new String[highScores.size()][2];
        for (int i = 0; i < highScores.size(); i++) {
            data[i][0] = highScores.get(i).getPlayerName();
            data[i][1] = String.valueOf(highScores.get(i).getPoints());
        }
        return new JTable(data, columnNames);
    }
}
