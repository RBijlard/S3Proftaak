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
    public static void main(String[] args) throws SlickException{
		//System.setProperty("org.lwjgl.librarypath", new File( new File( System.getProperty("user.dir") , "native") , LWJGLUtil.getPlatformName() ).getAbsolutePath() );
                AppGameContainer app = new AppGameContainer( new S3Proftaak("Game") );
                //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                //double width = screenSize.getWidth();
                //double height = screenSize.getHeight();
                GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
                int width = gd.getDisplayMode().getWidth();
                int height = gd.getDisplayMode().getHeight();
                System.out.println(width);
                System.out.println(height);
                app.setDisplayMode(width, height, true);
                app.setShowFPS(false);
                app.setVSync(true);
                app.start();
	}
}
