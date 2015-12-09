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
    private final float vX;
    private final float vY;
    private final boolean crouch;

    public PlayerPosition(float X, float Y, float vX, float vY, boolean crouch) {
        this.X = X;
        this.Y = Y;
        this.vX = vX;
        this.vY = vY;
        this.crouch = crouch;
    }

    public float getX() {
        return this.X;
    }

    public float getY() {
        return this.Y;
    }

    public float getvX() {
        return this.vX;
    }

    public float getvY() {
        return this.vY;
    }

    public boolean isCrouch() {
        return this.crouch;
    }

    @Override
    public String toString() {
        return "PlayerPosition{" + "X=" + X + ", Y=" + Y + ", vX=" + vX + ", vY=" + vY + ", crouch=" + crouch + '}';
    }
    
}
