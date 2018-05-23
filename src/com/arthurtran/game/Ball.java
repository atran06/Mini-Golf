package com.arthurtran.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Rectangle2D;

public class Ball extends Objects {

    private Runner runner;

    private double width, height;
    private double speed = 5;

    public Ball(double x, double y, Enum ID, Runner runner) {
        super(x, y, ID);

        this.runner = runner;
    }

    @Override
    protected void draw(GraphicsContext g) {
        g.setFill(Color.gray(1));
//        g.fillOval(x, y, 16, 16);
        g.setStroke(Color.gray(1));
        g.strokeOval(x, y, 16, 16);
    }

    @Override
    protected void update() {
        if(runner.getShoot())  {
            if(runner.getGetVelocity()) {
                velX = (Aim.x2 - x - 7) / speed;
                velY = (Aim.y2 - y - 7) / speed;
                runner.setGetVelocity(false);
            }
        }
        if(Math.abs(velX) < 0.01 || Math.abs(velY) < 0.001 ) {
            velX = 0;
            velY = 0;
            runner.setBallMoving(false);
            runner.setBallX(x);
            runner.setBallY(y);
        } else {
            velX *= .97;
            velY *= .97;
        }
        x += velX;
        y += velY;
    }

    @Override
    protected Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
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
