import java.awt.*;

/**
 * Created by NewNeskio on 14/4/2014.
 */
public class MyVertex {

    private double x;
    private double y;
    private String id;


    private double tentativeDistance;
    private boolean setTentative;   //visualize the tentative distance with the vertex ID
    private Color color;

    private MyVertex previous = null;
    private int set;

    MyVertex(){
        x=0;
        y=0;
        id="null";
        setTentative=false;
        set=-1;
        color=Color.RED;
    }

    MyVertex(String s){
        x=0;
        y=0;
        id=s;
        setTentative=false;
        set=-1;
        color=Color.RED;
    }

    MyVertex(double tx, double ty, String tid){
        x=tx;
        y=ty;
        id=tid;
        setTentative=false;
        set=-1;
        color=Color.RED;
    }

    public void setId(String s){ id=s;}
    public void setX(double value){ x=value; }
    public void setY(double value){ y=value; }
    public void setTentativeDistance(double value){ tentativeDistance=value;}
    public void setTentativeStatus(boolean value){ setTentative=value;}
    public void setPrevious(MyVertex vertex){ previous = vertex;}
    public void setSet(int value){ set=value;}
    public void setColor(Color color){
        this.color=color;
    }

    public String getId(){ return id; }
    public double getX(){ return x; }
    public double getY(){ return y; }
    public double getTentativeDistance(){ return tentativeDistance;}
    public boolean getTentativeStatus(){ return setTentative; }
    public MyVertex getPrevious() {return previous;}
    public int getSet(){ return set;}
    public Color getColor(){
        return color;
    }


    public String toString(){
        return id;
    }

}
