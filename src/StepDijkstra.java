import edu.uci.ics.jung.graph.Graph;

import java.awt.*;
import java.util.*;



public class StepDijkstra extends StepAlgorithm{

    MyVertex[] endpoints = new MyVertex[2];

    HashSet<MyVertex> unvisitedSet = new HashSet<MyVertex>();
    HashSet<MyVertex> visitedSet = new HashSet<MyVertex>();


    StepDijkstra(Graph<MyVertex, MyEdge> graph, MyVertex[] endpoints, Handler handler){
        super(graph,handler);
        this.endpoints = endpoints;
    }


    public MyVertex findNearestVertex(HashSet<MyVertex> unvisitedVertices){
        /*
            This method returns the vertex that has the smallest tentative distance from any of the already visited vertices.
            If no such vertex exists, it returns a null vertex.
        */
        MyVertex smallest=null;

        MyVertex[] unvisited = new MyVertex[unvisitedVertices.size()];
        unvisitedVertices.toArray(unvisited);

        if(unvisited.length!=0) {
            smallest = unvisited[0];
        }

        for(int i=0; i<unvisited.length; i++){
            System.out.println("Unvisited node " +unvisited[i].getId() +" tentative distance ="+unvisited[i].getTentativeDistance());
            if(unvisited[i].getTentativeDistance()<smallest.getTentativeDistance()){
                smallest = unvisited[i];
                System.out.println("New smallest neighbour "+smallest.getId()+" with tentative distance "+smallest.getTentativeDistance());
            }
            unvisited[i].setColor(Color.GRAY);
        }

        return smallest;
    }


    public HashSet<MyVertex> getUnvisitedNeighboursOf(MyVertex currentVertex){
        /*
            This method returns a hashSet containing every UNVISITED neighbour of the vertex passed as the parameter.
            The hashSet is empty if no unvisited vertices exist.
        */
        HashSet<MyVertex> unvisitedNeighbours = new HashSet<MyVertex>();

        Iterator i = graph.getOutEdges(currentVertex).iterator();                           //get all the edges for the current node
        while (i.hasNext()) {
            MyEdge tempEdge = (MyEdge) i.next();                                            //for the current edge
            MyVertex tempVertex = graph.getOpposite(currentVertex, tempEdge);               //get the vertex on the other end
            if (!visitedSet.contains(tempVertex)) {                                         //if not already visited
                unvisitedNeighbours.add(tempVertex);                                        //add it to the set
                System.out.println("Added "+ tempVertex.getId() + " to neighbours array");
            }
        }

        return unvisitedNeighbours;
    }


    public void calculateDistances(HashSet<MyVertex> neighbours, MyVertex currentVertex){
        /*
            This method calculates new distances for the current vertex's neighbours.
         */

        Iterator n = neighbours.iterator();

        while (n.hasNext()) {
            MyVertex currentNeighbour = (MyVertex) n.next();                                //get the next neighbour
            currentNeighbour.setColor(Color.CYAN);

            System.out.println("Inspecting node " + currentNeighbour.getId());
            double newTentative = currentVertex.getTentativeDistance() + graph.findEdge(currentVertex, currentNeighbour).getWeight();   //calculate the new tentative distance
            handler.setMessageLabel("Inspecting neighbour "+currentNeighbour.getId());
            pause();

            if (newTentative < currentNeighbour.getTentativeDistance()) {                   //if the calculated tentative distance is smaller than the neighbours current tentative
                handler.setMessageLabel(newTentative+ " smaller than "+currentNeighbour.getTentativeDistance()+". New tentative distance for node "+currentNeighbour.getId()+" is "+newTentative);
                currentNeighbour.setTentativeDistance(newTentative);                        //set the calculated tentative distance as the neighbours new tentative distance
                currentNeighbour.setPrevious(currentVertex);                                //also set the current node as the previous node to the current neighbour
                System.out.println("New tentative distance for " + currentNeighbour.getId() + " " + newTentative);
                System.out.println("Set node " +currentVertex.getId()+" as previous node to the node "+ currentNeighbour.getId());
            }else if(newTentative > currentNeighbour.getTentativeDistance()){               //
                handler.setMessageLabel("New calculated distance "+newTentative+" is bigger than "+currentNeighbour.getId()+"s current tentative distance "+currentNeighbour.getTentativeDistance());
            }
            else{
                handler.setMessageLabel("New calculated distance "+newTentative+" is equal to "+currentNeighbour.getId()+"s current tentative distance "+currentNeighbour.getTentativeDistance());
            }
            pause();

        }
    }


    public void paintUnvisited(){
        /*
            This method paints gray all the unvisited vertices.
            This is necessary for the step-by-step visualization process.
        */

        Iterator e = unvisitedSet.iterator();
        while(e.hasNext()){
            MyVertex unvisited = (MyVertex) e.next();
            unvisited.setColor(Color.GRAY);
        }
        handler.refreshGUI();
    }


    public void paintPath(){
        /*
            This method paints the nodes in the shortest path.
            It does that by traversing from the end vertex to the start vertex.
        */

        System.out.println("---------------------------");

        MyVertex vertex = endpoints[1];
        do{
            vertex.setColor(Color.YELLOW);
            System.out.println(vertex.getId());
           try{
            MyEdge edge = graph.findEdge(vertex, vertex.getPrevious());
            edge.setColor(new Color(25,178,255));
           }catch (Exception e){
               e.printStackTrace();
           }
               vertex=vertex.getPrevious();
        }while(!(vertex==null));

    }




    public void algorithm(){
        /*
            Dijkstra's algorithm

            1. Set all distances for all vertices to infinity. Mark them as unvisited. Set the distance for our start vertex to zero.
               Set the start vertex as the current vertex.

            2. Find all the vertices connected to the current vertex.

            3. Calculate the distance for each one. Distance = Current vertex's distance + edge weight.
               If the calculated distance is smaller than the neighbors' current tentative distance, set it as it's new distance.
               If it is bigger than the neighbors' current tentative distance, do nothing.

            4. When distances for all neighbours are calculated, set the current vertex as visited.

            5. Find the vertex with the smallest tentative distance.
               Set it as the new current vertex.

            6. Go to 2 and repeat until no unvisited vertex exists.
        */


        /**
         * Initialization
         */

        handler.setMessageLabel("");
        String previousStatus = handler.getStatusLabel();
        handler.setStatusLabel("Press Space for next step");

        MyVertex currentVertex = endpoints[0];
        MyVertex graphNodes[] = graph.getVertices().toArray(new MyVertex[0]);


        for(int i=0; i<graphNodes.length; i++){
            unvisitedSet.add(graphNodes[i]);                                            //dump all the nodes in the unvisited set
            graphNodes[i].setColor(Color.GRAY);
        }

        unvisitedSet.remove(currentVertex);                                             //remove the start node from the unvisited set
        visitedSet.add(currentVertex);                                                  //add it to the visited set

        endpoints[0].setColor(Color.ORANGE);
        endpoints[1].setColor(Color.ORANGE);


        for(int i=0; i<graphNodes.length; i++){
            graphNodes[i].setTentativeDistance(Double.POSITIVE_INFINITY);               //set the tentative distances for all nodes to infinity
            graphNodes[i].setTentativeStatus(true);
        }
        endpoints[0].setTentativeDistance(0);                                           //set the tentative distance for start node to 0


        pause();

        endpoints[1].setColor(Color.GRAY);


        /**
         * Main Loop
         */


        do {
            System.out.println("Examining node "+currentVertex.getId());
            currentVertex.setColor(Color.YELLOW);
            handler.setMessageLabel("The current node is node "+currentVertex.getId());
            pause();


            HashSet<MyVertex> currentNeighbours = getUnvisitedNeighboursOf(currentVertex);    //get all the unvisited neighbour vertices

            calculateDistances(currentNeighbours, currentVertex);                             //calculate the distances


            currentVertex.setColor(Color.RED);

            MyVertex next = findNearestVertex(unvisitedSet);                                  //find the nearest vertex

            unvisitedSet.remove(next);
            visitedSet.add(next);
            currentVertex = next;

            if(currentVertex!=null) {
                currentVertex.setColor(Color.GREEN);
                handler.setMessageLabel("The node with the smallest tentative distance is node " + currentVertex.getId());
            }

            pause();
            paintUnvisited();

            System.out.println("Visited set size " + visitedSet.size() + ", All vertices " + graphNodes.length);

        }while(visitedSet.size()<=graphNodes.length);


        paintPath();


        handler.setMessageLabel("Done.");
        pause();

        handler.setStatusLabel(previousStatus);
        handler.resetGraph();
    }


}
