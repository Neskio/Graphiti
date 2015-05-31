import edu.uci.ics.jung.graph.Graph;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;


public class StepPrim extends StepAlgorithm{


    MyVertex startNode;

    HashSet<MyEdge> unvisitedEdges = new HashSet<MyEdge>();
    HashSet<MyEdge> visitedEdges = new HashSet<MyEdge>();


    HashSet<MyVertex> unvisitedVertices = new HashSet<MyVertex>();
    HashSet<MyVertex> visitedVertices = new HashSet<MyVertex>();


    HashSet<MyVertex> reachableNeighbours = new HashSet<MyVertex>();


    StepPrim(Graph<MyVertex, MyEdge> graph, Handler handler, MyVertex node){
        super(graph, handler);
        startNode=node;

        Iterator i = graph.getEdges().iterator();
        while(i.hasNext()){
            MyEdge edge = (MyEdge)i.next();
            unvisitedEdges.add(edge);
        }

        Iterator v = graph.getVertices().iterator();
        while(v.hasNext()){
            MyVertex vertex = (MyVertex)v.next();
            unvisitedVertices.add(vertex);
            vertex.setColor(Color.GRAY);
        }

    }


    public MyVertex findSmallestReachableVertex(){
        MyEdge[] edges = new MyEdge[visitedEdges.size()];
        visitedEdges.toArray(edges);

        MyEdge smallestEdge=null;

        //find the smallest edge
        if(visitedEdges.size()!=0){
            smallestEdge=edges[0];
        }

        for(int i=0; i<edges.length; i++){
            if(edges[i].getWeight()<smallestEdge.getWeight()){
                smallestEdge=edges[i];
            }
        }

        visitedEdges.remove(smallestEdge);

        MyVertex first = graph.getEndpoints(smallestEdge).getFirst();
        MyVertex second = graph.getEndpoints(smallestEdge).getSecond();

        if(visitedVertices.contains(first)){
            handler.setMessageLabel("Nearest Unvisited Vertex "+second.toString());
            pause();
            smallestEdge.setColor(new Color(86,255,13));
            return second;
        }
        else{
            handler.setMessageLabel("Nearest Unvisited Vertex " + first.toString());
            pause();
            smallestEdge.setColor(new Color(86,255,13));
            return first;
        }

    }


    public void algorithm(){
        /*
        Get the current vertex
        Remove it from the unvisited set
        Paint it

        Find its neighbours
        Add them to the reachable neighbours set
        Add the weights
        Paint them and the edges blue

        Find the REACHABLE neighbour with the smallest weight
        Remove him from the reachable neighbour set
        Remove him from the unvisited vertices set

        Paint the edge connecting it to the current vertex
        set currentvertex = smallestReachableNeighbour
        Repeat
        */

        MyVertex currentVertex = startNode;
        String previousStatus = handler.getStatusLabel();
        handler.setStatusLabel("Press Space for next step");

        do {
            unvisitedVertices.remove(currentVertex);
            visitedVertices.add(currentVertex);
            currentVertex.setColor(Color.YELLOW);


            Iterator i = graph.getOutEdges(currentVertex).iterator();                           //get all the edges for the current node
            while (i.hasNext()) {
                MyEdge tempEdge = (MyEdge) i.next();                                            //get the next edge
                MyVertex tempVertex = graph.getOpposite(currentVertex, tempEdge);               //get the vertex on the other end
                if (unvisitedVertices.contains(tempVertex)) {                                   //if unvisited
                    reachableNeighbours.add(tempVertex);                                        //add it to the reachable neighbour array
                    tempVertex.setTentativeDistance(tempEdge.getWeight());
                    tempVertex.setColor(Color.CYAN);
                    tempEdge.setColor(new Color(25,178,255));
                    visitedEdges.add(tempEdge);
                }
            }



            MyVertex smallest = findSmallestReachableVertex();
           // handler.setMessageLabel("Nearest Reachable Vertex "+smallest.toString());
           // pause();

            reachableNeighbours.remove(smallest);
            unvisitedVertices.remove(smallest);

            currentVertex = smallest;
            currentVertex.setColor(Color.YELLOW);
            pause();
        }while(unvisitedVertices.size()>0);

        handler.setMessageLabel("Done. Press Space to end.");
        pause();

        handler.setStatusLabel(previousStatus);
        handler.resetGraph();
    }
}
