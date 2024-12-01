package model;

/**
 * Represents a player in the game with a name, position, and direction.
 * Handles player movement and direction changes.
 *
 * @author Horánszki Patrik Donát (CJJ14N)
 */
public class Player {
    private int x,y;
    private Direction direction;
    private final String name;

    public Player(String name, int x, int y, Direction direction){
        this.name = name;
        this.x = x;
        this.y = y;
        this.direction = direction;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String getName() {
        return name;
    }
    public Direction getDirection() {
        return direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }
    public void move(){
        x += direction.x;
        y += direction.y;
    }
}
