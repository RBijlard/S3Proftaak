/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared.Wrappers;

/**
 *
 * @author Stan
 */
public class PlatformPosition extends BasicPosition {

    private final float diffX;
    private final float diffY;
    private final float speed;
    private final boolean isGoingDown;
    
    public PlatformPosition(float x, float y){
        super(x, y);
        this.diffX = 0;
        this.diffY = 0;
        this.speed = 0;
        this.isGoingDown = false;
    }
    
    public PlatformPosition(float x, float y, float diffX, float diffY, float speed, boolean isGoingDown) {
        super(x, y);
        this.diffX = diffX;
        this.diffY = diffY;
        this.speed = speed;
        this.isGoingDown = isGoingDown;
    }

    public float getDiffX() {
        return diffX;
    }

    public float getDiffY() {
        return diffY;
    }

    public float getSpeed() {
        return speed;
    }
    
    public boolean isGoingDown(){
        return isGoingDown;
    }
    
}
