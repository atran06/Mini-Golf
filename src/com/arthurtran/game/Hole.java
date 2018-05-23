package com.arthurtran.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Hole extends Objects {

    public Hole(double x, double y, Enum ID) {
        super(x, y, ID);
    }

    @Override
    protected void draw(GraphicsContext g) {
        g.setStroke(Color.gray(0));
        g.setLineWidth(1);
//        g.strokeRect(x, y, 32, 32);
        g.strokeOval(x, y, 32, 32);
    }

    @Override
    protected void update() {

    }

    @Override
    protected Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, 32, 32);
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
}
