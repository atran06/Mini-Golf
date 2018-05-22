package com.arthurtran.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Runner extends Application {

    private Camera camera;

    private double windowWidth, windowHeight;
    private double ballX, ballY;

    private boolean shoot = false;
    private boolean getVelocity = true; //used to get the velocity once per shot
    private boolean ballMoving = false;
    private boolean canShoot = false;

    private LinkedList<Objects> objects = new LinkedList<>();

    public static enum ID {
        ball, barrier, obstacle, aim
    }

    public Runner() {
        windowWidth = 800;
        windowHeight = 800;

        this.camera = new Camera(0, 0);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Golf");
        stage.requestFocus();

        Canvas canvas = new Canvas(windowWidth, windowHeight);
        canvas.setFocusTraversable(true);

        keyInput(canvas);

        GraphicsContext g = canvas.getGraphicsContext2D();

        StackPane root = new StackPane();
        root.getChildren().add(canvas);

        stage.setScene(new Scene(root, windowWidth, windowHeight));
        stage.setResizable(false); //strange bug with this method *cough* Swing is better *cough*
        stage.sizeToScene(); //fixed with this

        objects.add(new Ball(windowWidth / 2 - 8, windowHeight / 2 - 8, ID.ball, this));
        System.out.println(ballX);
        objects.add(new Aim(ballX + 7, ballY + 7, ID.aim));

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw(g);
            }
        }.start();

        stage.show();
    }

    public void draw(GraphicsContext g) {
        g.setFill(Color.gray(.5));
        g.fillRect(0, 0, windowWidth, windowHeight);

        g.translate(-camera.getX(), -camera.getY());

        g.setFill(Color.gray(1));
        g.fillRect(100, 100, 100, 100);
        g.fillRect(300, 100, 100, 100);
        g.fillRect(100, 300, 100, 100);

        for(Objects ob : objects) {
            ob.draw(g);
        }

        g.translate(camera.getX(), camera.getY());
    }

    public void update() {
        for(Objects ob : objects) {
            ob.update();
            if(ob.getID() == ID.ball) {
                camera.update(ob);
            }
        }

        updateAim();
    }

    public void updateAim() {
        if(!ballMoving) {
            canShoot = true;
            for(Objects ob : objects) {
                if(ob.getID() == ID.aim) {
                    objects.remove(ob);
                }
            }
            objects.add(new Aim(ballX + 8, ballY + 8, ID.aim));
        } else {
            canShoot = false;
        }
    }

    public void keyInput(Canvas canvas) {
        canvas.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.SPACE) {
                if(canShoot) {
                    this.shoot = true;
                    this.ballMoving = true;
                }
            }
            if(e.getCode() == KeyCode.R) {
                restart();
            }
        });

        canvas.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.SPACE) {
                this.shoot = false;
                this.getVelocity = true;
            }
        });
    }

    public void restart() {
        objects.clear();

        objects.add(new Ball(windowWidth / 2 - 8, windowHeight / 2 - 8, ID.ball, this));
        objects.add(new Aim(windowWidth / 2, windowHeight / 2, ID.aim));
    }

    public LinkedList<Objects> getObjects() {
        return objects;
    }

    public boolean getShoot() {
        return this.shoot;
    }

    public boolean getGetVelocity() {
        return this.getVelocity;
    }

    public void setGetVelocity(boolean getVelocity) {
        this.getVelocity = getVelocity;
    }

    public void setBallX(double ballX) {
        this.ballX = ballX;
    }

    public void setBallY(double ballY) {
        this.ballY = ballY;
    }

    public void setBallMoving(boolean moving) {
        this.ballMoving = moving;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
