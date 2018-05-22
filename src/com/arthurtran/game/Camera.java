package com.arthurtran.game;

public class Camera {

    private double x, y;

    public Camera(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(Objects object) {
//        x = object.getX() - (800 / 2 - 8);
//        y = object.getY() - (800 / 2 - 8);
        x += ((object.getX() - x) - 800 / 2 - 8) * 0.05; //tweening algorithm
        y += ((object.getY() - y) - 800 / 2 - 8) * 0.05;
    }

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
}
