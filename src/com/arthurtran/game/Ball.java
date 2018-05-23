package com.arthurtran.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.geom.Rectangle2D;

public class Ball extends Objects {

    private Runner runner;

    private double width, height;
    private double speed = 10; //the higher the 'speed' the slower the ball

    public Ball(double x, double y, Enum ID, Runner runner) {
        super(x, y, ID);

        this.runner = runner;
        this.width = 16;
        this.height = 16;
    }

    @Override
    protected void draw(GraphicsContext g) {
        g.setFill(Color.gray(1));
//        g.fillOval(x, y, width, height);
        g.setStroke(Color.gray(1));
        g.strokeOval(x, y, width, height);

        g.setLineWidth(1);

        //getBoundsTop
        g.setStroke(Color.rgb(255, 0, 0));
        g.strokeRect(x + 5, y, width - 10, height / 2);

        //getBoundsBottom
        g.setStroke(Color.rgb(0, 255, 0));
        g.strokeRect(x + 5, y + width / 2, width - 10, height / 2);

        //getBoundsRight
        g.setStroke(Color.rgb(0, 0, 255));
        g.strokeRect(x + width / 2, y, width / 2, height);

        //getBoundsLeft
        g.setStroke(Color.gray(0));
        g.strokeRect(x, y, width / 2, height);
    }

    @Override
    protected void update() {

        collision();

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
            velX *= .96;
            velY *= .96;
        }
        x += velX;
        y += velY;
    }

    public void collision() {
        for(int i = 0; i < runner.getObjects().size(); i++) {
            if(runner.getObjects().get(i).getID() == Runner.ID.barrier) {
                Objects barrier = runner.getObjects().get(i);

                if(this.getBoundsTop().intersects(barrier.getBounds())) {
                    velY = -velY;
                    this.y = barrier.getY() + 32;
                }
                if(this.getBoundsBottom().intersects(barrier.getBounds())) {
                    velY = -velY;
                    this.y = barrier.getY() - 16;
                }
                if(this.getBoundsRight().intersects(barrier.getBounds())) {
                    velX = -velX;
                    this.x = barrier.getX() - 16;
                }
                if(this.getBoundsLeft().intersects(barrier.getBounds())) {
                    velX = -velX;
                    this.x = barrier.getX() + 32;
                }
            }
        }
    }

    @Override
    protected Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    protected Rectangle2D getBoundsTop() {
        return new Rectangle2D.Double(x + 5, y, width - 10, height / 2);
    }

    @Override
    protected Rectangle2D getBoundsBottom() {
        return new Rectangle2D.Double(x + 5, y + width / 2, width - 10, height / 2);
    }

    @Override
    protected Rectangle2D getBoundsLeft() {
        return new Rectangle2D.Double(x, y, width / 2, height);
    }

    @Override
    protected Rectangle2D getBoundsRight() {
        return new Rectangle2D.Double(x + width / 2, y, width / 2, height);
    }
}
