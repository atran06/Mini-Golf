package com.arthurtran.map;

import com.arthurtran.game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class FullMap {

    private BufferedImage map;
    private Game game;

    private int x = 0, y = 0;
    private int width = 800, height = 800;

    public FullMap(BufferedImage map, Game game) {
        this.map = map;
        this.game = game;
    }

    /**
     * Goes through every pixel in the map and draws mini objects based on the pixel color
     * @param g - GraphicsContext
     */
    public void draw(GraphicsContext g) {
        g.setFill(Color.gray(1, .5));
        g.fillRect(x, y, width, height);
        g.setStroke(Color.gray(0));
        g.setLineWidth(1);
        g.strokeRect(x, y, width, height);

        for(int x = 0; x < map.getWidth(); x++) {
            for(int y = 0; y < map.getHeight(); y++) {
                int color = map.getRGB(x, y);
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = (color) & 0xff;

                if(red == 255) {
                    g.setFill(Color.gray(0));
                    g.fillRect(x * 16, y * 16, 16, 16);
                }
                if(green == 255) {
                    g.setFill(Color.rgb(0, 255, 255));
                    g.fillRect(x * 16, y * 16, 16, 16);
                }
            }
        }

        g.setFill(Color.rgb(255, 0, 0));
        g.fillRect((game.getBallX() / 32) * 16, game.getBallY() / 32 * 16, 16, 16);
    }

    public void update() {

    }
}
