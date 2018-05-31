package com.arthurtran.map;

import com.arthurtran.game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;

public class MiniMap {

    private BufferedImage map;
    private Game game;

    private int x = 800 - 200, y = 0;
    private int width = 200, height = 200;

    public MiniMap(BufferedImage map, Game game) {
        this.map = map;
        this.game = game;
    }

    /**
     * Goes through every pixel in the map and draws mini objects based on the pixel color
     * @param g - GraphicsContext
     */
    public void draw(GraphicsContext g) {
        g.setFill(Color.gray(0, .5));
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

                if(red == 255 && blue == 0 && green == 0) {
                    g.setFill(Color.gray(1));
                    g.fillRect((x * 4) + (800 - 200), y * 4, 4, 4);
                }
                if(green == 255 && red == 0 && blue == 0) {
                    g.setFill(Color.rgb(0, 255, 255));
                    g.fillRect((x * 4) + (800 - 200), y * 4, 4, 4);
                }
            }
        }

        g.setFill(Color.rgb(255, 0, 0));
        g.fillRect((game.getBallX() / 32) * 4 + (600), game.getBallY() / 32 * 4, 4, 4);
    }

    public void update() {

    }
}
