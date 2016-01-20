package s3proftaak.Client.GameObjects;

/**
 *
 * @author Stan
 */
public class Point extends GameObject {

    public Point(float x, float y, float width, float height) {
        super(x, y, width, height, false);
    }

    @Override
    public String toString() {
        return super.toString() + " -- POINT " + this.getMatches();
    }
}
