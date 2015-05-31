import java.awt.*;

/**
 * Created by NewNeskio on 14/4/2014.
 */
public class MyEdge {

    private double weight;
    private String id;

    private Color color;

    MyEdge(){
        weight=0;
        id="null";
        color=Color.BLACK;
    }

    MyEdge(String s){
        weight=0;
        id=s;
        color=Color.BLACK;
    }

    MyEdge(double value, String s){
        weight=value;
        id=s;
        color=Color.BLACK;
    }

    public void setWeight(double value){ weight=value; }
    public void setId(String s){ id=s; }

    public double getWeight(){ return weight;}
    public String getId(){ return id; }

    public Color getColor(){
        return color;
    }
    public void setColor(Color color){
        this.color=color;
    }

    public String toString(){
        return id;
    }

}
