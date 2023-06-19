package fatsquare;

import java.awt.Rectangle;

public class Sprite extends Rectangle {
    private int velocity;

    Sprite(int x, int y, int width, int height) {
        super(x, y, width, height);
        velocity = 10;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public int getVelocity() {
        return this.velocity;
    }

    // movement
    public void moveUp() {
        super.y -= velocity;
    }

    public void moveDown() {
        super.y += velocity;
    }

    public void moveLeft() {
        super.x -= velocity;
    }

    public void moveRight() {
        super.x += velocity;
    }
}

