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
    //implement strength

    private Camera camera;
    private BufferedImageLoader loader; //comes from a library I wrote -Arthur
    private AudioPlayer audio;
    private MiniMap miniMap;

    private double windowWidth, windowHeight;
    private double ballX, ballY;

    private int par;
    private int stroke;
    private int scoreCurrent = 0;
    private int scoreFinal = 0;
    private int hole = 1;

    private boolean shoot = false;
    private boolean getVelocity = true; //used to get the velocity once per shot
    private boolean ballMoving = false;
    private boolean canShoot = false;

    private BufferedImage map1;
    private BufferedImage map2;

    private LinkedList<Objects> objects = new LinkedList<>();

    public static enum ID {
        ball, barrier, obstacle, aim, hole
    }

    public static enum STATE {
        menu, game, end
    }

    public STATE state = STATE.menu;

    public Runner() {
        windowWidth = 800;
        windowHeight = 800;

        this.camera = new Camera(0, 0);

        loader = new BufferedImageLoader();
        map1 = loader.imageLoader("maps/map2.png");
        map2 = loader.imageLoader("maps/map1v2.png");

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
        if(state == STATE.menu) {
            g.setFill(Color.gray(0));
            g.fillRect(0, 0, windowWidth, windowHeight);
        } else if(state == STATE.game) {
            g.setFill(Color.gray(.5));
            g.fillRect(0, 0, windowWidth, windowHeight);

            g.translate(-camera.getX(), -camera.getY());

            for (Objects ob : objects) {
                ob.draw(g);
            }

            g.translate(camera.getX(), camera.getY());

            miniMap.draw(g);
        } else if(state == STATE.end) {
            g.fillRect(0, 0, windowWidth, windowHeight);
            g.strokeText(Integer.toString(scoreFinal), 400, 400);
        }
    }

    public void update() {
        for(int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getID() == ID.ball) {
                camera.update(objects.get(i));
            }
            objects.get(i).update();
        }

        getHole();

        if (hole == 1) this.miniMap = new MiniMap(map1, this);
        if (hole == 2) this.miniMap = new MiniMap(map2, this);

        scoreCurrent = stroke - par;

        updateAim();
    }

    public void getHole() {
        if(hole == 1) par = 3;
        if(hole == 2) par = 5;
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
                    stroke++;
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
            if(e.getCode() == KeyCode.DIGIT1) {
                this.state = STATE.menu;
            }
            if(e.getCode() == KeyCode.DIGIT2) {
                this.state = STATE.game;
            }
            if(e.getCode() == KeyCode.DIGIT3) {
                this.state = STATE.end;
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

        if(hole == 1) loadMap(map1);
        if(hole == 2) loadMap(map2);
    }

    public void restartGame() {
        hole = 1;
        restart();
    }

    public void nextLevel() {
        objects.clear();

        getScoreFinal();
        stroke = 0;

        hole++;
        switch(hole) {
            case 2 :
                loadMap(map2);
        }
    }

    public void getScoreFinal() {
        scoreFinal += scoreCurrent;
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
                if(green == 255) {
                    objects.add(new Hole(x * 32, y * 32, ID.hole));
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

    public double getBallX() {
        return ballX;
    }

    public double getBallY() {
        return ballY;
    }

    public void setBallMoving(boolean moving) {
        this.ballMoving = moving;
    }

    public boolean getEnd() {
        if(hole == 2) return true;
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
