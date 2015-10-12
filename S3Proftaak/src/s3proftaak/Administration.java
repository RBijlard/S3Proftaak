/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.List;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import static s3proftaak.Game.closeGame;

/**
 *
 * @author S33D
 */
public class Administration extends BasicGame{

    private List<Account> accounts;
    private List<Lobby> lobbies;
    private Rectangle startButton;
    private int width;
    private int height;
    private Point mouse;
    private Game game;
    
    public Administration(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer container) throws SlickException {        
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        width = gd.getDisplayMode().getWidth();
        height = gd.getDisplayMode().getHeight();
        mouse = new Point(0,0);
        startButton = new Rectangle(width/2 - 175, height/2 - 100, 350, 200);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        Input input = container.getInput(); 
        
        mouse.setCenterX(container.getInput().getMouseX());
        mouse.setCenterY(container.getInput().getMouseY());     
        
        if(startButton.contains(mouse)){
            if(input.isMousePressed(0)){
                
                AppGameContainer app = new AppGameContainer(new Game("Game"));
                app.setDisplayMode(width, height, true);
                app.setTargetFrameRate(60);
                app.start();
                
            }
        }
        
        if (input.isKeyDown(Input.KEY_ESCAPE)) {
            closeGame();
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        g.setColor(Color.red);
        g.fill(startButton);
        g.setColor(Color.white);
        g.draw(startButton);
        g.drawString("Start nieuw spel", width/2 - 100, height/2 - 30);
        g.setColor(Color.yellow);
        g.fill(mouse);
    }
    
}
