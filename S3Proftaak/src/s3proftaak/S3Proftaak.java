package s3proftaak;

import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

/**
 *
 * @author S33D
 */
public class S3Proftaak extends BasicGame {

    private TiledMap map;
    
    /**
     * constructor for the S3Proftaak class
     * @param title instance title
     */
    public S3Proftaak(String title) {
        super(title);
    }

    /**
     * Initialise method, runs once on the startup of the game
     * @param container the window for the game
     * @throws SlickException 
     */
    @Override
    public void init(GameContainer container) throws SlickException {
        this.map = new TiledMap("src/Data/TestLevel.tmx");
    }

    /**
     * Update method, runs every frame (insert logic here)
     * @param container the window for the game
     * @param delta delta value, difference in milliseconds between each frame
     * @throws SlickException 
     */
    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        
    }

    /**
     * Render method, draws every update on the screen (draw in here)
     * @param container the window for the game
     * @param g graphics of the screen
     * @throws SlickException 
     */
    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        map.render(0, 0);
    }
    
    /**
     * Main method, runs the game thread and defines the app properties
     * @param args the command line arguments
     * @throws org.newdawn.slick.SlickException
     */
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new S3Proftaak("test"));
        
        app.setShowFPS(false);
        app.setDisplayMode(1000, 1000, false);
        app.start();
    }
    
}
