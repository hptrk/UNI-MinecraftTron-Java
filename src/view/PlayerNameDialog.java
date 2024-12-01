package view;

import javax.swing.*;
import java.awt.*;

/**
 * Dialog for entering player names before starting the game.
 * Displays player name fields with associated images.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public class PlayerNameDialog extends JDialog {
    private JTextField player1NameField;
    private JTextField player2NameField;

    public PlayerNameDialog(Frame parent){
        super(parent, "Játékosok neve", true);
        // Layout
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setResizable(false);

        // Form
        add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/boat.png"))));
        add(new JLabel("Játékos 1 neve:"));
        player1NameField = new JTextField(10);
        add(player1NameField);

        add(new JLabel(new ImageIcon(getClass().getClassLoader().getResource("assets/minecart.png"))));
        add(new JLabel("Játékos 2 neve:"));
        player2NameField = new JTextField(10);
        add(player2NameField);

        // Start button
        JButton startButton = new JButton("Játék");
        startButton.addActionListener(e -> setVisible(false));
        add(startButton);

        // Finalize dialog
        setSize(280, 150);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    public String getPlayer1Name() {
        return player1NameField.getText();
    }

    public String getPlayer2Name() {
        return player2NameField.getText();
    }
}
