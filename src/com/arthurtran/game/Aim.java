package com.arthurtran.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Rectangle2D;

public class Aim extends Objects {

    private Runner runner;

    public static double angle = 0;
    private double angleRad;
    private double length = 100;
    private double lineWidth = 3;
    private double offset = 8;

    public static double x2, y2;

    public Aim(double x, double y, Enum ID, Runner runner) {
        super(x, y, ID);

        this.runner = runner;

        angleRad = Math.toRadians(-angle);

        x2 = offset + x + (length * Math.cos(angleRad));
        y2 = offset + y + (length * Math.sin(angleRad));
    }

    @Override
    protected void draw(GraphicsContext g) {
        g.setStroke(Color.gray(1));
        g.setLineWidth(lineWidth);
        g.strokeLine(x + offset, y + offset, x2, y2);
    }

    @Override
    protected void update() {

    }

    @Override
    protected Rectangle2D getBounds() {
        return null;
    }

    @Override
    protected Rectangle2D getBoundsTop() {
        return null;
    }

    @Override
    protected Rectangle2D getBoundsBottom() {
        return null;
    }

    @Override
    protected Rectangle2D getBoundsLeft() {
        return null;
    }

    @Override
    protected Rectangle2D getBoundsRight() {
        return null;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }
}
