import edu.uci.ics.jung.graph.Graph;

import java.awt.*;
import java.util.*;


public class StepDFS extends StepAlgorithm {

    MyVertex startVertex;
    MyVertex endVertex;

    Stack stack = new Stack();
    HashSet<MyVertex> visited = new HashSet<MyVertex>();


    //This constructor is used if we want to transverse all the vertices in the graph
    StepDFS(Graph<MyVertex, MyEdge> graph, MyVertex startVertex, Handler handler){
        super(graph, handler);
        this.startVertex = startVertex;
    }

    //This constructor is used if we want to find a vertex using the algorithm
    StepDFS(Graph<MyVertex, MyEdge> graph, MyVertex startVertex, MyVertex endVertex, Handler handler){
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

    public void stackVisualizer(String s, boolean add){

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

        The DFS algorithm uses the stack data structure.

        Pseudocode
            1. Push the root vertex to the stack

            2. Pop a vertex
                -Examine it (optional - if we are looking for a vertex and this is it, stop)
                -If an unvisited neighbour exists, push it to the stack

            3. If stack is empty, stop
                -If not, go to 2

            This DFS implementation also keeps track of the visited vertices using a HashSet.
        */

        handler.setStatusLabel("Press Space to advance");
        System.out.println("Inside stepDFS");


        MyVertex graphNodes[] = graph.getVertices().toArray(new MyVertex[0]);
        for(int i=0; i<graphNodes.length; i++){
            graphNodes[i].setColor(Color.GRAY);
        }


        stack.push(startVertex);                                    //Push the start vertex
        visited.add(startVertex);
        startVertex.setColor(Color.YELLOW);

        handler.setMessageLabel("Stack: "+startVertex.toString());


        pause();

        while(!stack.isEmpty()) {                                   //Loop while the stack is not empty

            MyVertex vertex = (MyVertex)stack.peek();               //Pop a vertex
            vertex.setColor(Color.CYAN);
            pause();

            if(vertex == endVertex){                                //If this is the vertex we are looking for, stop
                vertex.setColor(Color.GREEN);
                handler.setMessageLabel("Node Found.");
                pause();
                break;
            }


            MyVertex neighbour= getUnvisitedNeighbourVertex(vertex);

            if(neighbour!=null){                                    //If an unvisited neighbour vertex exists
                visited.add(neighbour);
                System.out.println(neighbour.getId());
                neighbour.setColor(Color.YELLOW);
                stack.push(neighbour);                              //Push it to the stack
                stackVisualizer(neighbour.toString(), true);
                pause();
            }
            else{
                stack.pop();
                stackVisualizer("",false);
            }

            vertex.setColor(Color.RED);

        }

        handler.setMessageLabel("Done. Press Space to end.");
        pause();

        handler.setStatusLabel(previousStatusMessage);
        handler.resetGraph();
    }
}
