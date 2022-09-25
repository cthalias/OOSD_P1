import bagel.Image;
import java.awt.*;

public class Wall {
    //image declaration
    protected final Image wallImage = new Image("res/wall.png");

    //attributes declaration
    protected Rectangle wallRec;
    protected final double x;
    protected final double y;

    //constructor
    protected Wall(double x, double y){
        this.x = x;
        this.y = y;

        double width = wallImage.getWidth();
        double height = wallImage.getHeight();

        this.wallRec = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    //toString method
    public String toString() { return "wall x= " + x + "y= " + y ; }

}
