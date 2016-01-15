/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package s3proftaak.Shared.Wrappers;

import java.io.Serializable;

/**
 *
 * @author Stan
 */
public class MoveableBlockPosition implements Serializable {

    private final float x;
    private final float y;
    private final int dx;

    public MoveableBlockPosition(float x, float y, int dx) {
        this.x = x;
        this.y = y;
        this.dx = dx;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }
}
