package com.arthurtran.objects;

import com.arthurtran.game.Objects;
import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Rectangle2D;

public class woodBlock extends Objects {

    public woodBlock(double x, double y, Enum ID) {
        super(x, y, ID);
    }

    @Override
    public void draw(GraphicsContext g) {
        
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle2D getBounds() {
        return null;
    }

    @Override
    public Rectangle2D getBoundsTop() {
        return null;
    }

    @Override
    public Rectangle2D getBoundsBottom() {
        return null;
    }

    @Override
    public Rectangle2D getBoundsLeft() {
        return null;
    }

    @Override
    public Rectangle2D getBoundsRight() {
        return null;
    }
}
