package s3proftaak;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Roel
 */
public class S3Proftaak extends BasicGame {

    private StaticLevel level;
    private Player player;

    /**
     * constructor for the S3Proftaak class
     *
     * @param title instance title
     */
    public S3Proftaak(String title) {
        super(title);
    }

    /**
     * Initialise method, runs once on the startup of the game
     *
     * @param container the window for the game
     * @throws SlickException
     */
    @Override
    public void init(GameContainer container) throws SlickException {
        level = new StaticLevel();
        level.init(container);

        player = new Player(level);
        player.init(container);
    }

    /**
     * Update method, runs every frame (insert logic here)
     *
     * @param container the window for the game
     * @param delta delta value, difference in milliseconds between each frame
     * @throws SlickException
     */
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        level.update(container, delta);
        player.update(container, delta);

        if (container.getInput().isKeyDown(Input.KEY_ESCAPE)) {
            Main.closeGame();
        }
    }

    /**
     * Render method, draws every update on the screen (draw in here)
     *
     * @param container the window for the game
     * @param g graphics of the screen
     * @throws SlickException
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        //drawDebugLines(g, 50);

        level.render(container, g);
        player.render(container, g);
    }

    public void drawDebugLines(Graphics g, int size) {
        int resolution = 800;
        g.setColor(Color.darkGray);
        for (int i = 0; i < resolution; i += size) {
            g.drawLine(i, 0, i, resolution);
            g.drawLine(0, i, resolution, i);
        }
    }

}
