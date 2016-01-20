package s3proftaak.Client.GameObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Berry-PC
 */
public abstract class GameObject {

    private int id;
    private final Rectangle hitbox;
    private ArrayList<Integer> matches;
    private final List<GameObject> matchedObjects;
    private final boolean collision;

    public GameObject(float x, float y, float width, float height, boolean collision) {
        this.matches = new ArrayList<>();
        this.matchedObjects = new ArrayList<>();
        this.hitbox = new Rectangle(x, y, width, height);
        this.collision = collision;
    }

    public void addMatchedObject(GameObject match) {
        if (!this.matchedObjects.contains(match)) {
            this.matchedObjects.add(match);
        }
    }

    public List<GameObject> getMatchedObjects() {
        return Collections.unmodifiableList(this.matchedObjects);
    }

    public void setMatches(ArrayList<Integer> matches) {
        this.matches = matches;
    }

    public ArrayList<Integer> getMatches() {
        return this.matches;
    }

    public Rectangle getRect() {
        return this.hitbox;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int calculateOffset() {
        return (int) (70 - this.getRect().getHeight());
    }
    
    public boolean hasCollision(){
        return this.collision;
    }

    @Override
    public String toString() {
        return this.getRect().getX() + "," + this.getRect().getY() + " - " + this.getRect().getWidth() + " x " + this.getRect().getHeight();
    }
}
