package com.arthurtran.game;

import com.arthurtran.Arch2D.main.AudioPlayer;
import com.arthurtran.Arch2D.textures.BufferedImageLoader;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Runner extends Application {

    // TODO: 5/22/2018
    //point tracker
    //implement strength
    //implement collisions
    //create mini-map
    //make maps(MAKE HARD(IF TIME))
    //two player(WIP)(IF TIME)

    private Camera camera;
    private BufferedImageLoader loader; //comes from a library I wrote -Arthur
    private AudioPlayer audio;

    private double windowWidth, windowHeight;
    private double ballX, ballY;

    private boolean shoot = false;
    private boolean getVelocity = true; //used to get the velocity once per shot
    private boolean ballMoving = false;
    private boolean canShoot = false;

    private BufferedImage map1;

    private LinkedList<Objects> objects = new LinkedList<>();

    public static enum ID {
        ball, barrier, obstacle, aim
    }

    public Runner() {
        windowWidth = 800;
        windowHeight = 800;

        this.camera = new Camera(0, 0);

        loader = new BufferedImageLoader();
        map1 = loader.imageLoader("maps/map1v2.png");

        audio = new AudioPlayer("/music/golfOST2.wav", true);
        audio.setVolume(0.2f);
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

//        objects.add(new Ball(windowWidth / 2 - 8, windowHeight / 2 - 8, ID.ball, this));
//        objects.add(new Aim(ballX, ballY, ID.aim, this)); //7 is due to line width

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw(g);
            }
        }.start();

        loadMap(map1);

        stage.show();
    }

    public void draw(GraphicsContext g) {
        g.setFill(Color.gray(.5));
        g.fillRect(0, 0, windowWidth, windowHeight);

        g.translate(-camera.getX(), -camera.getY());

//        g.setFill(Color.gray(1));
//        g.fillRect(100, 100, 100, 100);
//        g.fillRect(300, 100, 100, 100);
//        g.fillRect(100, 300, 100, 100);

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
            for(int i = 0; i < objects.size(); i++) {
                if(objects.get(i).getID() == ID.aim) {
                    objects.remove(objects.get(i));
                }
            }
            objects.add(new Aim(ballX, ballY, ID.aim, this));
        } else {
            canShoot = false;
        }
    }

    public void keyInput(Canvas canvas) {
        canvas.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.SPACE) {
                if(canShoot) {
                    for(int i = 0; i < objects.size(); i++) {
                        if(objects.get(i).getID() == ID.aim) {
                            objects.remove(objects.get(i));
                        }
                    }
                    this.shoot = true;
                    this.ballMoving = true;
                }
            }
            if(e.getCode() == KeyCode.R) {
                restart();
            }
            if(e.getCode() == KeyCode.UP) {
                Aim.angle += 2;
            }
            if(e.getCode() == KeyCode.DOWN) {
                Aim.angle -= 2;
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
        objects.add(new Aim(ballX, ballY, ID.aim, this));
    }

    public void loadMap(BufferedImage map) {
        for(int y = 0; y < map.getHeight(); y++) {
            for(int x = 0; x < map.getWidth(); x++) {
                int color = map.getRGB(x, y);
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = (color) & 0xff;

                if(red == 255) {
                    objects.add(new Barrier(x * 32, y * 32, ID.barrier));
                }
                if(blue == 255) {
                    objects.add(new Ball(x * 32, y * 32, ID.ball, this));
                    objects.add(new Aim(ballX, ballY, ID.aim, this));
                }
            }
        }
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
