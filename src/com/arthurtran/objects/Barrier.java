package com.arthurtran.objects;

import com.arthurtran.game.Objects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Barrier extends Objects {

    private Image blockTexture;

    public Barrier(double x, double y, Enum ID) {
        super(x, y, ID);


        blockTexture = new Image(getClass().getResourceAsStream("/textures/block1.png"));
    }

    @Override
    public void draw(GraphicsContext g) {
//        g.setFill(Color.gray(1));
//        g.fillRect(x, y, 32, 32);
//
//        g.setLineWidth(1);
//
//        g.setStroke(Color.gray(0));
//        g.strokeRect(x, y, 32, 32);

        g.drawImage(blockTexture, x, y);
    }

    @Override
    public void update() {

    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, 32, 32);
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
