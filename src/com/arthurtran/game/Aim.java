package com.arthurtran.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Rectangle2D;

public class Aim extends Objects {

    private double angle = 135;
    private double angleRad;
    private double length = 100;
    private double lineWidth = 3;

    public static double x2, y2;

    public Aim(double x, double y, Enum ID) {
        super(x, y, ID);

        angleRad = Math.toRadians(angle - 90);

        x2 = x + (length * Math.cos(angleRad));
        y2 = y + (length * Math.sin(angleRad));
    }

    @Override
    protected void draw(GraphicsContext g) {
        g.setStroke(Color.gray(1));
        g.setLineWidth(lineWidth);
        g.strokeLine(x, y, x2, y2);
    }

    @Override
    protected void update() {

    }

    @Override
    protected Rectangle2D getBounds() {
        return null;
    }

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }
}
