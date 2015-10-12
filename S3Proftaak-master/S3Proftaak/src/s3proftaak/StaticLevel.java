/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Berry-PC
 */
public class StaticLevel {

    private Shape levelBase, platform;

    /**
     * Initialise method, runs once on the startup of the game
     *
     * @param container the window for the game
     * @throws SlickException
     */
    public void init(GameContainer container) throws SlickException {

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();

        float[] polygonPoint = new float[]{
            0, 0,
            (width / 16), 0,
            (width / 16), (width - 5 * (width / 16)),
            (width - width / 16), (width - 5 * (width / 16)),
            (width - width / 16), 7 * (width / 16),
            (width - 4 * (width / 16)), 7 * (width / 16),
            (width - 4 * (width / 16)), 6 * (width / 16),
            (width - width / 16), 6 * (width / 16),
            (width - width / 16), 0,
            width, 0,
            width, height,
            0, height
        };
        levelBase = new Polygon(polygonPoint);
        platform = new Rectangle(10 * (width / 16), 8 * (width / 16), 2 * (width / 16), (width / 16));
    }

    /**
     * Update method, runs every frame (insert logic here)
     *
     * @param container the window for the game
     * @param delta delta value, difference in milliseconds between each frame
     * @throws SlickException
     */
    public void update(GameContainer container, int delta) throws SlickException {

    }

    /**
     * Render method, draws every update on the screen (draw in here)
     *
     * @param container the window for the game
     * @param g graphics of the screen
     * @throws SlickException
     */
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.green);

        g.draw(levelBase);
        g.draw(platform);
    }

    public boolean collidesWith(Shape s) {
        return levelBase.intersects(s) || platform.intersects(s);
    }
}
