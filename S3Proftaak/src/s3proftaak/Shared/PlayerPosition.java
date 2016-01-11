/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared;

import java.io.Serializable;

/**
 *
 * @author Stan
 */
public class PlayerPosition implements Serializable {
    private final float X;
    private final float Y;
    private final float vY;
    private final int walkingDirection;
    private final int oldWakingDirection;
    private final boolean crouch;
    private final boolean walking;

    public PlayerPosition(float X, float Y, float vY, int walkingDirection, int oldWalkingDirection, boolean crouch, boolean walking) {
        this.X = X;
        this.Y = Y;
        this.vY = vY;
        this.walkingDirection = walkingDirection;
        this.oldWakingDirection = oldWalkingDirection;
        this.crouch = crouch;
        this.walking = walking;
    }

    public float getX() {
        return this.X;
    }

    public float getY() {
        return this.Y;
    }

    public float getvY() {
        return this.vY;
    }
    
    public int getWalkingDirection(){
        return this.walkingDirection;
    }
    
    public int getOldWalkingDirection(){
        return this.oldWakingDirection;
    }

    public boolean isCrouch() {
        return this.crouch;
    }
    
    public boolean isWalking(){
        return this.walking;
    }
}
