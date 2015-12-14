package s3proftaak.Client.GameObjects;

/**
 *
 * @author Berry-PC
 */
public class Spike extends GameObject {

    public Spike(float x, float y, float width, float height) {
        super(x, y, width, height);
    }
    
    @Override
    public String toString(){
        return super.toString() + " -- SPIKE";
    }
    
}
