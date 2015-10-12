/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Berry-PC
 */
public class Main {

    public static void main(String[] arguments) {
        try {
            AppGameContainer app = new AppGameContainer(new Game("Game"));
            app.setDisplayMode(1300, 1300, false);
            app.setTargetFrameRate(60);
            app.start();
        } catch (SlickException e) {
        }
    }
}
