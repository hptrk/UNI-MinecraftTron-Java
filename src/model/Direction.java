package model;

/**
 * Enum representing the possible directions a player can move.
 * Each direction has associated x and y offsets.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public enum Direction {
    DOWN(1,0), LEFT(0,-1), UP(-1,0), RIGHT(0,1);

    public final int x,y;

    Direction(int x, int y){
        this.x = x;
        this.y = y;
    }
}
