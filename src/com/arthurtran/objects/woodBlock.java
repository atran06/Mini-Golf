package com.arthurtran.objects;

import com.arthurtran.game.Objects;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.awt.geom.Rectangle2D;

public class woodBlock extends Objects {

    private Image woodTexture;

    public woodBlock(double x, double y, Enum ID) {
        super(x, y, ID);

        woodTexture = new Image(getClass().getResourceAsStream("/textures/wood.png"));
    }

    @Override
    public void draw(GraphicsContext g) {
        g.drawImage(woodTexture, x, y);
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
