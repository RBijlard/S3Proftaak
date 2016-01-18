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
public class MoveableBlockPosition extends BasicPosition {

    private final int dx;

    public MoveableBlockPosition(float x, float y, int dx) {
        super(x, y);
        this.dx = dx;
    }

    public int getDx() {
        return dx;
    }
}
