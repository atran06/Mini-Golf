package com.arthurtran.game;

import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Rectangle2D;

public abstract class Objects {

    protected double x, y; //The x and y positions of the object
    protected double velX, velY; //The velocities of the object
    protected Enum ID; //The IDs of the objects

    public Objects(double x, double y, Enum ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;
    }

    public abstract void draw(GraphicsContext g);
    public abstract void update();

    //Used for collisions
    public abstract Rectangle2D getBounds();
    public abstract Rectangle2D getBoundsTop();
    public abstract Rectangle2D getBoundsBottom();
    public abstract Rectangle2D getBoundsLeft();
    public abstract Rectangle2D getBoundsRight();

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public Enum getID() {
        return ID;
    }

    public void setID(Enum ID) {
        this.ID = ID;
    }
}
