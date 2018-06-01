package com.arthurtran.objects;

import com.arthurtran.game.Objects;
import com.arthurtran.main.Runner;
import com.arthurtran.game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.awt.geom.Rectangle2D;

public class Ball extends Objects {

    private Game game;
    private Image ballTexture;

    private double width, height;
    public static double speed = 5; //the higher the 'speed' the slower the ball. 2-100 is the range

    public Ball(double x, double y, Enum ID, Game game) {
        super(x, y, ID);

        this.game = game;
        this.width = 16;
        this.height = 16;

        ballTexture = new Image(getClass().getResourceAsStream("/textures/ballSmiley.png"));
    }

    @Override
    public void draw(GraphicsContext g) {

        g.drawImage(ballTexture, x, y);
    }

    @Override
    public void update() {

        collision();

        if(game.getShoot()) {
            if(game.getGetVelocity()) {
                velX = (Aim.x2 - x - 7) / speed;
                velY = (Aim.y2 - y - 7) / speed;
                game.setGetVelocity(false);
            }
        }

        if(Math.abs(velX) < 0.01 || Math.abs(velY) < 0.01 ) {
            velX = 0;
            velY = 0;
            game.setBallMoving(false);
        } else {
            velX *= .97; //updates the friction
            velY *= .97; //updates the friction
        }

        game.setBallX(x);
        game.setBallY(y);

        x += velX;
        y += velY;
    }

    /**
     * Uses the list of objects in the Runner and the getBounds methods of each Object to detect collisions
     */
    public void collision() {
        for(int i = 0; i < game.getObjects().size(); i++) {

            //Checks if the object is a Barrier
            if(game.getObjects().get(i).getID() == Runner.ID.barrier) {
                Objects barrier = game.getObjects().get(i);

                if(this.getBoundsRight().intersects(barrier.getBounds())) {
                    this.x = barrier.getX() - 16 - 2;
                    velX = -velX;
                }
                if(this.getBoundsLeft().intersects(barrier.getBounds())) {
                    this.x = barrier.getX() + 32 + 2;
                    velX = -velX;
                }
                if(this.getBoundsTop().intersects(barrier.getBounds())) {
                    this.y = barrier.getY() + 32 + 2;
                    velY = -velY;
                }
                if(this.getBoundsBottom().intersects(barrier.getBounds())) {
                    this.y = barrier.getY() - 16 - 2;
                    velY = -velY;
                }
            }

            //Checks if the object is the hole
            if(game.getObjects().get(i).getID() == Runner.ID.hole) {
                Objects hole = game.getObjects().get(i);

                if(this.getBounds().intersects(hole.getBounds()) && game.getEnd()) {
                    game.nextLevel();
                    game.state = Runner.STATE.end;
                } else if(this.getBounds().intersects(hole.getBounds())) {
                    game.nextLevel();
                }
            }
        }
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    @Override
    public Rectangle2D getBoundsTop() {
        return new Rectangle2D.Double(x + 6, y, width - 12, height / 2);
    }

    @Override
    public Rectangle2D getBoundsBottom() {
        return new Rectangle2D.Double(x + 6, y + height / 2, width - 12, height / 2);
    }

    @Override
    public Rectangle2D getBoundsLeft() {
        return new Rectangle2D.Double(x, y + 7, 6, height - 14);
    }

    @Override
    public Rectangle2D getBoundsRight() {
        return new Rectangle2D.Double(x + width - 6, y + 7, 6, height - 14);
    }
}
