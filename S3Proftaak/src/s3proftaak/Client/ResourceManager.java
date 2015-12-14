/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Client;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Stan
 */
public class ResourceManager {

    public enum Images {

        STAR("star.png"),
        WEIGHTCHAINED("weightChained.png"),
        CHAIN("chain.png"),
        DOOR_OPENMID("door_openMid.png"),
        DOOR_OPENTOP("door_openTop.png"),
        DOOR_CLOSEDMID("door_closedMid.png"),
        DOOR_CLOSEDTOP("door_closedTop.png"),
        BOXITEM("boxItem.png"),
        SWITCHLEFT("switchLeft.png"),
        SWITCHRIGHT("switchRight.png"),
        BUTTONRED_PRESSED("buttonRed_pressed.png"),
        BUTTONRED("buttonRed.png");

        private Image image;

        Images(String path) {
            if (image == null){
                try {
                    image = new Image(getClass().getResource("/Resources/Levels/" + path).getPath().replace("%20", " "));
                } catch (SlickException ex) {
                    System.out.println(ex);
                }
            }
        }
        
        public Image getImage(){
            return image;
        }
    }

    public static Image getImage(Images i) {
        return i != null ? i.getImage() : null;
    }
}
