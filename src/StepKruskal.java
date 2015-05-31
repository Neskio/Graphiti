import edu.uci.ics.jung.graph.Graph;

import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;



public class StepKruskal extends StepAlgorithm {


    HashSet<MyEdge> visitedEdges = new HashSet<MyEdge>();
    HashSet<MyEdge> unvisited = new HashSet<MyEdge>();
    HashSet<MyVertex> visitedVertices = new HashSet<MyVertex>();


    int totalVertexNumber; //number of edges, to be fixed
    int set=0;


    StepKruskal(Graph<MyVertex, MyEdge> graph, Handler handler){
        super(graph, handler);

        Iterator i = graph.getEdges().iterator();
        while(i.hasNext()){
            MyEdge edge = (MyEdge)i.next();
            unvisited.add(edge);
        }

        Iterator v = graph.getVertices().iterator();
        while(v.hasNext()){
            MyVertex vertex = (MyVertex)v.next();
            vertex.setColor(Color.GRAY);
        }


        totalVertexNumber = graph.getVertexCount();
    }

    public MyEdge findSmallestEdge(){
        /*
            This method returns the edge with the smallest weight.
        */

        MyEdge[] edges = new MyEdge[unvisited.size()];  //create a new MyEdge array the size of the unvisited vertices set
        unvisited.toArray(edges);                       //dump the set into the array

        MyEdge smallest = null;

        if(!(edges.length==0)){                         //if the array is not empty, set the first edge as the smallest
            smallest=edges[0];
        }

        for(int i=0; i<edges.length; i++){
            if(edges[i].getWeight()<smallest.getWeight()){
                smallest=edges[i];
            }
        }


        return smallest;
    }

    public void putInSet(int setTwo, int setOne){
        /*
            This method puts the vertices from setOne into setTwo
        */

        Iterator v = graph.getVertices().iterator();
        while(v.hasNext()){
            MyVertex tempVertex = (MyVertex)v.next();
            if(tempVertex.getSet()==setTwo){
                tempVertex.setSet(setOne);
            }
        }
    }

   public boolean resumeIteration(){
       /*
          This method checks if all the edges belong to the same set, and returns a boolean value accordingly.
          If all the edges belong to the same seat means that the algorithm has finished.
       */

       MyVertex graphNodes[] = graph.getVertices().toArray(new MyVertex[0]);
       int set=-2;
       boolean resume = false;

       if(graphNodes.length!=0){
           set=graphNodes[0].getSet();
       }

       for(int i=0; i<graphNodes.length; i++){
           if(graphNodes[i].getSet()!=set){
                resume=true;
           }
       }

       return resume;
   }


    public void algorithm(){
        /*
            The algorithm picks the edge with the smallest weight, and then checks the vertices it is connected to.

            The algorithm puts vertices into sets, in order to determine if cycles appear.
            We can think of the sets as distinct trees inside the forest=graph.

            Initially no vertex belongs to any set.
        */

        MyVertex firstVertex=null;
        MyVertex secondVertex=null;

        String previousStatus = handler.getStatusLabel();
        handler.setStatusLabel("Press Space for next step");


        do {
            pause();
            MyEdge currentEdge = findSmallestEdge();
            System.out.println("Current smallest edge is edge "+currentEdge.getId()+" with a weight of "+currentEdge.getWeight());
            firstVertex=graph.getEndpoints(currentEdge).getFirst();
            secondVertex=graph.getEndpoints(currentEdge).getSecond();


            /*
            If both vertices don't belong to a set, it means that no cycle is imminent.
            We make a new set for those vertices and put them in.

                set++
                put them in the same set
                put them into the visited array
                put the edge into the visited edge array
                paint them and the edge
            */
            if(firstVertex.getSet()==-1 && secondVertex.getSet()==-1){
                System.out.println("if both nodes don't belong to a set");
                set++;
                firstVertex.setSet(set); secondVertex.setSet(set);
                visitedVertices.add(firstVertex); visitedVertices.add(secondVertex);
                visitedEdges.add(currentEdge);
                unvisited.remove(currentEdge);

                currentEdge.setColor(new Color(25,178,255));
                firstVertex.setColor(Color.YELLOW);
                secondVertex.setColor(Color.YELLOW);
            }



            /*
            If one vertex of the two doesn't belong to a set, it means that no cycle is imminent.
            Put the one that doesn't belong to a set into the other's.

                put the other node into the opposite ones set
                put it into the visited array
                put the edge into the visited edge array
                paint it and also the edge
            */
            if(firstVertex.getSet()==-1 || secondVertex.getSet()==-1){
                System.out.println("one node of the two doesn't belong to a set");
                if(firstVertex.getSet()==-1){  //if the first one doesn't belong to a set
                    firstVertex.setSet(secondVertex.getSet());
                    visitedVertices.add(firstVertex);
                    visitedEdges.add(currentEdge);
                    unvisited.remove(currentEdge);

                    firstVertex.setColor(Color.YELLOW);
                    currentEdge.setColor(new Color(25,178,255));
                }
                else //if the second vertex doesn't belong to a set
                {
                    secondVertex.setSet(firstVertex.getSet());
                    visitedVertices.add(secondVertex);
                    visitedEdges.add(currentEdge);
                    unvisited.remove(currentEdge);

                    secondVertex.setColor(Color.YELLOW);
                    currentEdge.setColor(new Color(25,178,255));
                }
            }


            /*
            If both vertices belong to the same set, it means that a CYCLE is created

                DO NOTHING
                put the edge into the visited edge array
                do not paint anything
                if they belong to a set they are visited too, so nothing to be done here
            */
            if(firstVertex.getSet()==secondVertex.getSet()){
                System.out.println("both nodes belong to the same set");
                visitedEdges.add(currentEdge);
                unvisited.remove(currentEdge);
            }




           /*
            If the vertices belong to different sets, it means that no cycle is imminent.

                put the edge into the visited edge array
                paint the edge
           */
            if( (firstVertex.getSet()>=0&&secondVertex.getSet()>=0) && (firstVertex.getSet()!=secondVertex.getSet())){
                //if both belong to a set ( getSet>=0) and the sets are not equal

                putInSet(secondVertex.getSet(), firstVertex.getSet());

                System.out.println("the nodes belong to different sets");
                visitedEdges.add(currentEdge);
                unvisited.remove(currentEdge);
                currentEdge.setColor(new Color(25,178,255));

            }


        System.out.println("Visited vertices are "+visitedVertices.size()+" total vertex number is " + totalVertexNumber);
            handler.setMessageLabel("Press Space to for next step...");

        }while(resumeIteration());
        //when all vertices belong to the same set, stop

        System.out.println("Stopped");

        handler.setMessageLabel("Done.");
        pause();

        handler.setStatusLabel(previousStatus);
        handler.resetGraph();

    }




}
