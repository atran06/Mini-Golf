package com.arthurtran.game;

import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Rectangle2D;

public abstract class Objects {

    protected double x, y;
    protected double velX, velY;
    protected Enum ID;

    public Objects(double x, double y, Enum ID) {
        this.x = x;
        this.y = y;
        this.ID = ID;
    }

    protected abstract void draw(GraphicsContext g);
    protected abstract void update();
    protected abstract Rectangle2D getBounds();

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
