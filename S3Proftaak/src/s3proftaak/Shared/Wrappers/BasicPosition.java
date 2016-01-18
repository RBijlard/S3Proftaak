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
public abstract class BasicPosition implements Serializable {

    private final float x;
    private final float y;

    public BasicPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
