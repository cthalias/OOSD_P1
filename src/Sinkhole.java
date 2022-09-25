import bagel.Image;
import java.awt.*;

public class Sinkhole {
    //attribute declaration
    protected double x;
    protected double y;
    protected Rectangle sinkholeRec;
    protected boolean crashed;

    //image declaration
    protected final Image sinkholeImage = new Image("res/sinkhole.png");

    //constructor
    protected Sinkhole(double x, double y){
        this.x = x;
        this.y = y;
        this.crashed = false;
        double width = sinkholeImage.getWidth();
        double height = sinkholeImage.getHeight();

        this.sinkholeRec = new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    //toString method
    public String toString(){
        return "sinkhole x= " + x + "y= " + y + "crashed" + crashed;
    }
}
