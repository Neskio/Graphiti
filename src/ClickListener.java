import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

/**
 * Created by NewNeskio on 11/5/2014.
 */
public class ClickListener implements MouseListener{

    VisualizationViewer<MyVertex,MyEdge> currentvv=null;
    Handler handler;

    MyVertex[] endNodes = new MyVertex[2];

    MyVertex startNode = null;
    MyVertex endNode = null;
    String s = "none";
    String algorithm;

    ClickListener(  VisualizationViewer<MyVertex,MyEdge> vv, Handler handler, String mode, String algorithm){
        this.handler=handler;
        currentvv=vv;
        currentvv.addMouseListener(this);
        s=mode;
        this.algorithm=algorithm;
    }

    public void ready(){
        System.out.println("Inside clicklistener ready");
        if(algorithm.equals("Dijkstra")){
            endNodes[0]=startNode;
            endNodes[1]=endNode;
            handler.executeStepDijkstra(endNodes);
        }
        if(algorithm.equals("BFS")){
            handler.executeStepBFS(startNode);
        }
        if(algorithm.equals("DFS")){
            handler.executeStepDFS(startNode);
        }
        if(algorithm.equals("Prim")){
            handler.executeStepPrim(startNode);
        }
    }


    public void reset(){
        if(startNode!=null){
        startNode=null;}
        if(endNode!=null){
        endNode=null;}
        s="none";
    }

    public void mousePressed(MouseEvent e) {    }

    public void mouseReleased(MouseEvent e) {    }

    public void mouseEntered(MouseEvent e) {    }

    public void mouseExited(MouseEvent e) {    }

    public void mouseClicked(MouseEvent e) {
        try{

            currentvv =  (VisualizationViewer<MyVertex,MyEdge>)e.getSource();
            Point2D p = e.getPoint();


            GraphElementAccessor<MyVertex,MyEdge> pickSupport = currentvv.getPickSupport();

            if(pickSupport != null) {

                MyVertex v = pickSupport.getVertex(currentvv.getGraphLayout(), p.getX(), p.getY());
                if(v != null) {
                    if (s.equals("oneNode")){
                        startNode = v;
                        System.out.println("Start node selected");
                        s="ready";
                    }
                    if(s.equals("secondNode")){
                        endNode=v;
                        System.out.println("End node selected");
                        s="ready";
                    }
                    if (s.equals("firstNode")){
                        startNode = v;
                        System.out.println("Start node selected");
                        s="secondNode";
                    }
                    if (s.equals("ready")){
                        ready();
                    }
                }
            }

        }
        catch(Exception ex){ ex.printStackTrace();}
    }


}
