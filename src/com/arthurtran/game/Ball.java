package com.arthurtran.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Rectangle2D;

public class Ball extends Objects {

    private Runner runner;

    private double width, height;
    private double friction = 0.1;

    public Ball(double x, double y, Enum ID, Runner runner) {
        super(x, y, ID);

        this.runner = runner;
    }

    @Override
    protected void draw(GraphicsContext g) {
        g.setFill(Color.gray(1));
        g.fillOval(x, y, 16, 16);
    }

    @Override
    protected void update() {

        if(runner.getShoot())  {
            if(runner.getGetVelocity()) {
                velX = (Aim.x2 - x) / 10;
                velY = (Aim.y2 - y) / 10;
                runner.setGetVelocity(false);
            }
        }
        if(Math.abs(velX) < .2) {
            velX = 0;
            runner.setBallMoving(false);
            runner.setBallX(x);
            runner.setBallY(y);
        } else {
            if(velX > 0) {
                velX -= friction;
            } else {
                velX += friction;
            }
        }
        if(Math.abs(velY) < .2) {
            velY = 0;
        } else {
            if(velY > 0) {
                velY -= friction;
            } else {
                velY += friction;
            }
        }
        x += velX;
        y += velY;
    }

    @Override
    protected Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }
}
