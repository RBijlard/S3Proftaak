package s3proftaak.Client.GameObjects;


import java.util.ArrayList;
import java.util.List;
import org.newdawn.slick.geom.Rectangle;

/**
 *
 * @author Berry-PC
 */
public abstract class GameObject {
    private int id;
    private ArrayList<Integer> matches;
    private Rectangle hitbox;
    private List<GameObject> matchedObjects;
    
    public GameObject(float x, float y, float width, float height){
        this.matches = new ArrayList<>();
        this.matchedObjects = new ArrayList<>();
        this.hitbox = new Rectangle(x, y, width, height);
    }
    
    public void addMatchedObject(GameObject match){
        if(!this.matchedObjects.contains(match)){
            this.matchedObjects.add(match);
        }
    } 
    
    public List<GameObject> getMatchedObjects(){
        return this.matchedObjects;
    }
    
    public void setMatches(ArrayList<Integer> matches){
        this.matches = matches;
    }
    
    public ArrayList<Integer> getMatches(){
        return this.matches;
    }
    
    public Rectangle getRect(){
        return this.hitbox;
    }
    
    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    @Override
    public String toString(){
        return this.getRect().getX() + "," + this.getRect().getY() + " - " + this.getRect().getWidth() + " x " + this.getRect().getHeight();
    }
}
