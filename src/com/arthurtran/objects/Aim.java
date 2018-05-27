package com.arthurtran.objects;

import com.arthurtran.game.Objects;
import com.arthurtran.game.Runner;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Rectangle2D;

public class Aim extends Objects {

    private Runner runner;

    public static double angle = 0; //The angle in degrees
    private double angleRad; //The angle in radians (Gets calculated in the constructor)
    private double length = 100; //Length of the aiming line
    private double lineWidth = 3; //Width of the aim line
    private double offset = 8; //So the ball is 16x16 which means the line should be drawn in the center

    public static double x2, y2; //The second points of the line

    public Aim(double x, double y, Enum ID, Runner runner) {
        super(x, y, ID);

        this.runner = runner;

        angleRad = Math.toRadians(-angle); //Calculates the angle to radians

        //Calculates the second x and y of the line
        x2 = offset + x + (length * Math.cos(angleRad));
        y2 = offset + y + (length * Math.sin(angleRad));
    }

    @Override
    public void draw(GraphicsContext g) {
        g.setStroke(Color.gray(1));
        g.setLineWidth(lineWidth);
        g.strokeLine(x + offset, y + offset, x2, y2);
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

    public double getX2() {
        return x2;
    }

    public double getY2() {
        return y2;
    }
}
