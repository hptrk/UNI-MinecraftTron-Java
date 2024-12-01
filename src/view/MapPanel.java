package view;

import model.MapItem;

import javax.swing.*;
import java.awt.*;

/**
 * Panel for displaying the game map.
 * Renders the map items based on the game state.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public class MapPanel extends JPanel {
    private final MapItem[][] map;
    private final int rows;
    private final int cols;
    private static final int TILE_SIZE = 32;

    public MapPanel(int rows, int cols, MapItem[][] map){
        this.rows = rows;
        this.cols = cols;
        this.map = map;
        setPreferredSize(new Dimension(cols * TILE_SIZE, rows * TILE_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                g.drawImage(map[row][col].getImage(), col * 32, row * 32, this);
            }
        }
    }
}
