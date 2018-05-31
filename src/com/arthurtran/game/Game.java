package com.arthurtran.game;

import com.arthurtran.Arch2D.main.AudioPlayer;
import com.arthurtran.Arch2D.main.Utilities;
import com.arthurtran.Arch2D.textures.BufferedImageLoader;
import com.arthurtran.map.FullMap;
import com.arthurtran.map.MiniMap;
import com.arthurtran.objects.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Game extends Application implements Utilities {

    // TODO: 5/22/2018
    //fix mouse input in menu
    //implement new maps

    private Camera camera;
    private BufferedImageLoader loader; //comes from a library I wrote -Arthur
    private AudioPlayer audio; //also from the library I wrote - Arthur
    private MiniMap miniMap;
    private FullMap fullMap;

    private double windowWidth, windowHeight; //the width and height of the window
    private double ballX, ballY; //used to hold the ball's position... could have used POINT but heard they were slow

    private int par; //specifies the par on the hole
    private int stroke; //keeps track of current stroke
    private int scoreCurrent = 0; //gets the score of the current hole
    private int scoreFinal = 0; //score of all holes combined
    private int hole = 1; //specifies which hole is being played
    private int strength = 100;

    private boolean shoot = false; //used to get the ball's velocity once per shot
    private boolean getVelocity = true; //used to get the velocity once per shot
    private boolean ballMoving = false; //is the ball moving or not
    private boolean canShoot = false; //if able to shoot
    private boolean addMiniMapOnce = true; //used to only create a minimap once per level

    private BufferedImage map1;
    private BufferedImage map2;
    private BufferedImage map3;
    private BufferedImage map4;
    private BufferedImage map5;
    private Image menu;
    private Image end;
    private Image controls;

    private LinkedList<Objects> objects = new LinkedList<>(); //list of all objects in the game

    public static enum ID { //Used enumerations to give unique IDs to each object
        ball, barrier, wood, aim, hole
    }

    public static enum STATE { //Used to specify the state of the game
        menu, game, end, controls
    }

    public STATE state = STATE.menu;

    public Game() {
        windowWidth = 800;
        windowHeight = 800;

        this.camera = new Camera(0, 0);

        loader = new BufferedImageLoader();
        map1 = loader.imageLoader("maps/map2.png");
        map2 = loader.imageLoader("maps/map1v2.png");
        map3 = loader.imageLoader("maps/map3.png");
        map4 = loader.imageLoader("maps/map4.png");
        map5 = loader.imageLoader("maps/map5.png");

        menu = new Image(getClass().getResourceAsStream("/gameImages/Untitled.png"));
        end = new Image(getClass().getResourceAsStream("/gameImages/endScreen.png"));
        controls = new Image(getClass().getResourceAsStream("/gameImages/controls.png"));

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

    /**
     * Draws everything
     * @param g - GraphicsContext
     */
    public void draw(GraphicsContext g) {
        if(state == STATE.menu) {
            g.drawImage(menu, 0, 0);

        } else if(state == STATE.game) {
            g.setFill(Color.rgb(0,120,0));

            g.fillRect(0, 0, windowWidth, windowHeight);

            //everything between gets translated based on camera position//
            g.translate(-camera.getX(), -camera.getY());

            for (Objects ob : objects) {
                ob.draw(g);
            }

            g.translate(camera.getX(), camera.getY());
            ///////////////////////////////////////////////////////////////////

            g.setFill(Color.rgb(0, 255, 0));
            g.fillRect(0, 150, 20, 100 * 5);
            g.setFill(Color.gray(0));
            g.fillRect(0, 150, 20, strength * 5);
            g.setStroke(Color.gray(0));
            g.strokeRect(0, 150, 20, 100 * 5);

            miniMap.draw(g);
//            fullMap.draw(g);

            g.setFont(new Font("arial", 25));
            g.setFill(Color.gray(1));
            g.fillText("Par: " + Integer.toString(par), 600, 220);
            g.fillText("Stroke: " + Integer.toString(stroke), 600, 240);

        } else if(state == STATE.end) {
            g.drawImage(end, 0, 0);
            //g.fillRect(0, 0, windowWidth, windowHeight);
            g.setFont(new Font("arial", 50));
            if(scoreFinal == 0) {
                g.fillText("Score: " + "E", windowWidth / 2 - 100, 400);
            } else {
                g.fillText("Score: " + Integer.toString(scoreFinal), windowWidth / 2 - 100, 400);
            }
        } else if(state == STATE.controls) {
            g.drawImage(controls, 0, 0);
        }
    }

    /**
     * Updates all the game objects
     */
    public void update() {
        for(int i = 0; i < objects.size(); i++) { //Goes through the list of game objects
            if (objects.get(i).getID() == ID.ball) {
                camera.update(objects.get(i)); //Updates the camera based on f position
            }
            objects.get(i).update(); //Updates the game object
        }

        strength = (int) (((Ball.speed - 3) / 7) * 100);

        getHole();

        if(addMiniMapOnce) {
            if (hole == 1) {
                this.miniMap = new MiniMap(map1, this);
                this.fullMap = new FullMap(map1, this);
            }
            if (hole == 2) {
                this.miniMap = new MiniMap(map2, this);
                this.fullMap = new FullMap(map2, this);
            }
            if (hole == 3) {
                this.miniMap = new MiniMap(map3, this);
                this.fullMap = new FullMap(map3, this);
            }
            if (hole == 4) {
                this.miniMap = new MiniMap(map4, this);
                this.fullMap = new FullMap(map4, this);
            }
            if (hole == 5) {
                this.miniMap = new MiniMap(map5, this);
                this.fullMap = new FullMap(map5, this);
            }
            addMiniMapOnce = false;
        }

        scoreCurrent = stroke - par; //calculates the score of the current hole

        updateAim();
    }

    /**
     * sets the par of each hole
     */
    public void getHole() {
        if(hole == 1) par = 3;
        if(hole == 2) par = 5;
        if(hole == 3) par = 4;
        if(hole == 4) par = 5;
        if(hole == 5) par = 4;
    }

    /**
     * Removes the aiming when shot and updates the position when stopped
     */
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

    /**
     * Keeps all the key input stuff in one method
     * @param canvas - The canvas
     */
    public void keyInput(Canvas canvas) {
        canvas.setOnKeyPressed(e -> {
            if(state == STATE.game) {
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
                if(e.getCode() == KeyCode.W) {
                    if(Ball.speed <= 2) {
                        Ball.speed = 2;
                    } else {
                        Ball.speed -= .2;
                    }
                }
            }
            if(e.getCode() == KeyCode.S) {
                if(Ball.speed >= 10) {
                    Ball.speed = 10;
                } else {
                    Ball.speed += .2;
                }
            }

            //Used for testing the states of the game
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

        canvas.setOnMouseClicked(e -> {
            System.out.println(e.getX() + " " + e.getY());

            if(state == STATE.menu) {
                if(e.getX() > 320 && e.getX() < 470) {
                    if(e.getY() > 240 && e.getY() < 280) {
                        state = STATE.game;
                    }
                }
                if(e.getX() > 273 && e.getX() < 526) {
                    if(e.getY() > 330 && e.getY() < 370) {
                        state = STATE.controls;
                    }
                }
                if(e.getX() > 262 && e.getX() < 550) {
                    if(e.getY() > 425 && e.getY() < 458) {
                        System.exit(1);
                    }
                }
            }
            if(state == STATE.controls) {
                if(e.getX() > 630 && e.getX() < 779) {
                    if(e.getY() > 25 && e.getY() < 71) {
                        state = STATE.menu;
                    }
                }
            }
        });
    }

    /**
     * Restarts the level
     */
    public void restart() {
        objects.clear();

        stroke = 0;

        if(hole == 1) loadMap(map1);
        if(hole == 2) loadMap(map2);
        if(hole == 3) loadMap(map3);
        if(hole == 4) loadMap(map4);
        if(hole == 5) loadMap(map5);
    }

    /**
     * Restarts the game
     */
    public void restartGame() {
        hole = 1;
        restart();
    }

    /**
     * Goes to next level
     */
    public void nextLevel() {
        objects.clear();

        getScoreFinal();
        stroke = 0;

        addMiniMapOnce = true;

        hole++;

        if(hole == 2) loadMap(map2);
        if(hole == 3) loadMap(map3);
        if(hole == 4) loadMap(map4);
        if(hole == 5) loadMap(map5);
    }

    /**
     * Gets the final score
     */
    public void getScoreFinal() {
        scoreFinal += scoreCurrent;
    }

    /**
     * Looks through every pixel in the buffered image and adds objects based on pixel color.
     * @param map - A BufferedImage
     */
    public void loadMap(BufferedImage map) {
        for(int y = 0; y < map.getHeight(); y++) {
            for(int x = 0; x < map.getWidth(); x++) {
                int color = map.getRGB(x, y);
                int red = (color >> 16) & 0xff; //I think this is a bitwise shift operator
                int green = (color >> 8) & 0xff;
                int blue = (color) & 0xff;

                if(red == 255 && green == 0 && blue == 0) {
                    objects.add(new Barrier(x * 32, y * 32, ID.barrier));
                }
                if(blue == 255 && red == 0 && green == 0) {
                    objects.add(new Aim(ballX, ballY, ID.aim, this));
                    objects.add(new Ball(x * 32, y * 32, ID.ball, this));
                }
                if(green == 255 && red == 0 && blue == 0) {
                    objects.add(new Hole(x * 32, y * 32, ID.hole));
                }
                if(green == 255 && red == 0 && blue == 255) {
                    objects.add(new woodBlock(x * 32, y * 32, ID.wood));
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

    /**
     * @return True if the hole is the last hole false otherwise
     */
    public boolean getEnd() {
        if(hole == 6) return true;
        return false;
    }
}
