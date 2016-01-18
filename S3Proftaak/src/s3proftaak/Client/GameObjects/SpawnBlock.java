package s3proftaak.Client.GameObjects;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import s3proftaak.Client.GameObjects.Interfaces.IRenderable;
import s3proftaak.Client.GameObjects.Interfaces.IStateChangeable;
import s3proftaak.Client.ResourceManager;

/**
 *
 * @author Berry-PC
 */
public class SpawnBlock extends GameObject implements IStateChangeable, IRenderable {

    private boolean isActive;
    private Image sprite;

    public SpawnBlock(float x, float y, float width, float height) {
        super(x, y, width, height);
        sprite = ResourceManager.Images.BOXITEM.getImage();
        this.setActive(false);
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        //render door animation/img
        if (isActive) {
            sprite.draw(this.getRect().getX(), this.getRect().getY() - calculateOffset());
        }
    }

    public int calculateOffset() {
        return (int) (70 - this.getRect().getHeight());
    }

    @Override
    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public void setActive(boolean active) {
        if(active){
            this.getRect().setWidth(70);
            this.getRect().setHeight(70);
        } else {
            this.getRect().setWidth(0);
            this.getRect().setHeight(0);            
        }
        this.isActive = active;
    }

    @Override
    public String toString() {
        return super.toString() + " -- SPAWNBLOCK " + this.getMatches();
    }

}
