import edu.uci.ics.jung.graph.Graph;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;


public class StepBFS extends StepAlgorithm {

    MyVertex startVertex;
    MyVertex endVertex;

    Queue queue = new LinkedList();
    HashSet<MyVertex> visited = new HashSet<MyVertex>();


    //This constructor is used if we want to transverse all the vertices in the graph
    StepBFS(Graph<MyVertex, MyEdge> graph, MyVertex startVertex, Handler handler){
        super(graph, handler);
        this.startVertex = startVertex;
    }

    //This constructor is used if we want to find a vertex using the algorithm
    StepBFS(Graph<MyVertex, MyEdge> graph, MyVertex startVertex, MyVertex endVertex, Handler handler){
        super(graph, handler);
        this.startVertex = startVertex;
        this.endVertex = endVertex;
    }


    public MyVertex getUnvisitedNeighbourVertex(MyVertex vertex){
        /*
            This method returns an unvisited random neighbour vertex to the vertex passed as parameter.
            The vertex returned is random because of the nature of the Iterator data structure.
            If such a vertex doesn't exist, a null vertex is returned instead.
        */

        MyVertex neighbour = null;
        Iterator i = graph.getOutEdges(vertex).iterator();                  //get a collection containing all the edges connected to the given vertex

        while (i.hasNext()) {

            MyEdge tempEdge = (MyEdge) i.next();                            //get the current edge
            MyVertex tempVertex = graph.getOpposite(vertex, tempEdge);      //get the vertex on the other end

            if (!visited.contains(tempVertex)) {                            //if the opposite vertex is not already visited
                return tempVertex;                                          //return it
            }
        }

        return neighbour;                                                   //return null if no unvisited vertices exist
    }

    public void queueVisualizer(String s, boolean add){

        StringBuilder builder = new StringBuilder();

        if(add){
            builder.append(handler.getMessageLabel());
            builder.append(", "+s);
            String stack = builder.toString();
            handler.setMessageLabel(stack);
        }
        else{
            String currentStack = handler.getMessageLabel();
            builder.append(currentStack);


            try{ builder.delete(currentStack.lastIndexOf(", ") ,currentStack.length());
            }catch (Exception e){
                handler.setMessageLabel("Stack Empty");
            }
            handler.setMessageLabel(builder.toString());

        }
    }

    public void algorithm(){
        /*

        The BFS algorithm uses the queue data structure.

        Pseudocode
            1. Enqueue the root vertex

            2. Dequeue a vertex
                -Examine it (optional - if we are looking for a vertex and this is it, stop)
                -If an unvisited neighbour exists, enqueue it

            3. If queue is empty, stop
                -If not, go to 2

            This BFS implementation also keeps track of the visited vertices using a HashSet.
        */

        System.out.println("Inside stepBFS");
        handler.setStatusLabel("Press Space to advance");


        MyVertex graphNodes[] = graph.getVertices().toArray(new MyVertex[0]);
        for(int i=0; i<graphNodes.length; i++){
                graphNodes[i].setColor(Color.GRAY);
        }

        queue.add(startVertex);                                                     //Enqueue the root vertex
        visited.add(startVertex);
        startVertex.setColor(Color.YELLOW);

        pause();

        while(!queue.isEmpty()) {

            MyVertex vertex = (MyVertex)queue.remove();                             //Dequeue a vertex
            vertex.setColor(Color.CYAN);
            pause();

            if(vertex == endVertex){                                                //If this is the vertex we are looking for, stop
                vertex.setColor(Color.GREEN);
                handler.setMessageLabel("Node Found.");
                pause();
                break;
            }


            MyVertex neighbour=null;

            //Find all the neighbour vertices and add them to the queue
            while(   ( neighbour= getUnvisitedNeighbourVertex(vertex))!=null) {      //While an unvisited neighbour vertex exists
                visited.add(neighbour);
                neighbour.setColor(Color.YELLOW);
                queue.add(neighbour);                                               //Add it to the queue
                pause();
            }

            vertex.setColor(Color.RED);
            pause();

        }

        handler.setMessageLabel("Done.");
        pause();
        handler.setStatusLabel(previousStatusMessage);
        handler.resetGraph();

    }
}
