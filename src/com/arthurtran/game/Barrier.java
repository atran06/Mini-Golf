package com.arthurtran.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Rectangle2D;

public class Barrier extends Objects {

    public Barrier(double x, double y, Enum ID) {
        super(x, y, ID);
    }

    @Override
    protected void draw(GraphicsContext g) {
        g.setFill(Color.gray(1));
        g.fillRect(x, y, 32, 32);

        g.setLineWidth(1);

        g.setStroke(Color.gray(0));
        g.strokeRect(x, y, 32, 32);
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
