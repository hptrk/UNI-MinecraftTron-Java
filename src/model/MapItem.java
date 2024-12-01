package model;

import javax.swing.*;
import java.awt.*;

 /**
 * Enum representing the different types of map items.
 * Each map item has an associated image.
 * Used to render the game map.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public enum MapItem {
    EMPTY("grass.png"), WATER("water.png"), BOAT("boat.png"), MINECART("minecart.png"), LAVA("lava.png");

    private final String imagePath;

    MapItem(String imagePath) {
        this.imagePath = imagePath;
    }

    public Image getImage() {
        return new ImageIcon(getClass().getClassLoader().getResource("assets/" + imagePath)).getImage();
    }
}
