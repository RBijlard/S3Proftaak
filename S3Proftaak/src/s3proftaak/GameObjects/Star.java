/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.GameObjects;

/**
 *
 * @author Stan
 */
public class Star extends GameObject {

    public Star(int posX, int posY, int width, int height, String spritePath, boolean collision, boolean trigger) {
        super(posX, posY, width, height, spritePath, collision, trigger);
    }
    
}
