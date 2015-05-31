import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedOrderedSparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedSparseMultigraph;


import javax.swing.*;


public class GraphGenerator {

    Graph<MyVertex, MyEdge> graph;

    double panelWidth=900;
    double panelHeight=617;

    GraphGenerator(){
        graph = new UndirectedSparseMultigraph<MyVertex, MyEdge>();
    }

    public  Graph<MyVertex, MyEdge> getGraph(){
        return graph;
    }

    /*
    Full Connected Graph
    */

    public void option1(){
        int x = Integer.parseInt(JOptionPane.showInputDialog("Size of Graph"));
        int edgeCounter=1;

        MyVertex vertex[] = new MyVertex[x];

        for(int i=0; i<x; i++){
            vertex[i] = new MyVertex("V"+Integer.toString(i));
            graph.addVertex(vertex[i]);
        }


        for(int i=0; i<x; i++){
            for(int j=i; j<x; j++){
               if(i!=j){
                   graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), vertex[i], vertex[j]);
                   edgeCounter++;
               }
            }
        }
    }


    /*
    Complete Bipartite graph
    */
    public void option2(){
        int x = Integer.parseInt(JOptionPane.showInputDialog("Size of first set"));
        int y = Integer.parseInt(JOptionPane.showInputDialog("Size of second set"));

        int edgeCount=1;

        MyVertex firstSet[] = new MyVertex[x];
        MyVertex secondSet[] = new MyVertex[y];

        for(int i=0; i<x; i++){
            firstSet[i] = new MyVertex("U"+Integer.toString(i));
            graph.addVertex(firstSet[i]);
        }

        for(int i=0; i<y; i++){
            secondSet[i] = new MyVertex("V"+Integer.toString(i));
            graph.addVertex(secondSet[i]);
        }

        for(int i=0; i<x; i++){
            for(int j=0; j<y; j++){
               graph.addEdge(new MyEdge("E"+Integer.toString(edgeCount)), firstSet[i], secondSet[j]);
               edgeCount++;
            }

        }

        placeBipartite(firstSet, secondSet);
    }


    /*
    Path graph
    */
    public void option3(){
        int x = Integer.parseInt(JOptionPane.showInputDialog("Size of Graph"));
        int edgeCounter=1;

        MyVertex vertex[] = new MyVertex[x];

        for(int i=0; i<x; i++){
            vertex[i] = new MyVertex("V"+Integer.toString(i));
            graph.addVertex(vertex[i]);
        }

        for(int i=1; i<x; i++){
            graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), vertex[i-1], vertex[i]);
            edgeCounter++;
        }

        placePath(vertex);
    }


    /*
    Circle graph
    */
    public void option4(){
        graph = new UndirectedOrderedSparseMultigraph<MyVertex, MyEdge>();
        int x = Integer.parseInt(JOptionPane.showInputDialog("Size of Graph"));
        int edgeCounter=1;

        MyVertex vertex[] = new MyVertex[x];

        for(int i=0; i<x; i++){
            vertex[i] = new MyVertex("V"+Integer.toString(i));
            graph.addVertex(vertex[i]);
        }

        for(int i=1; i<x; i++){
            graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), vertex[i-1], vertex[i]);
            edgeCounter++;
        }

        graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), vertex[0], vertex[x-1]);


    }


    /*
    Star graph
    */
    public void option5(){
        graph = new UndirectedOrderedSparseMultigraph<MyVertex, MyEdge>();
        int x = Integer.parseInt(JOptionPane.showInputDialog("Size of Graph"));
        int edgeCounter=1;

        MyVertex vertex[] = new MyVertex[x-1];

        for(int i=0; i<x-1; i++){
            vertex[i] = new MyVertex("V"+Integer.toString(i));
            graph.addVertex(vertex[i]);
        }

        double radius = (panelHeight/2)-(double)30;
        placeCircle(vertex, radius, 0);

        MyVertex centerNode = new MyVertex("S");
        graph.addVertex(centerNode);

        centerNode.setX(panelWidth/2);
        centerNode.setY(panelHeight/2);

        for(int j=0; j<x-1; j++){
            graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), centerNode, vertex[j]);
            edgeCounter++;
        }


    }


    /*
    Grid graph
    */
    public void option6(){
        int x = Integer.parseInt(JOptionPane.showInputDialog("x Size"));
        int y = Integer.parseInt(JOptionPane.showInputDialog("y Size"));

        MyVertex vertex[][] = new MyVertex[x][y];
        int edgeCounter=1;

        for(int i=0; i<x; i++){
            for(int j=0; j<y; j++){
                vertex[i][j] = new MyVertex("V"+Integer.toString(i)+","+Integer.toString(j));
            }
        }


        for(int j=0; j<y; j++){
            for(int i=1; i<x; i++){
                graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), vertex[i-1][j], vertex[i][j]);
                edgeCounter++;
            }
        }

        for(int j=0; j<x; j++){
            for(int i=1; i<y; i++){
                graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), vertex[j][i-1], vertex[j][i]);
                edgeCounter++;
            }
        }

        placeGrid(vertex);

    }


    /*
    Perfect M-tree
    */
    public void option7(){

        graph = new UndirectedOrderedSparseMultigraph<MyVertex, MyEdge>();
        int x = Integer.parseInt(JOptionPane.showInputDialog("Tree Depth"));
        int y = Integer.parseInt(JOptionPane.showInputDialog("Number of Leaves"));
        int edgeCounter=1;

        MyVertex tree[][] = new MyVertex[x][];

        //Allocate space for each row of the tree
        for(int i=0; i<x; i++){
            int totalLeaves = (int)Math.pow(y,i);
            tree[i] = new MyVertex[totalLeaves];
        }

        //Populate the graph
        for(int i=0; i<x; i++){
            for(int j=0; j<tree[i].length; j++){
                System.out.println("i: "+i+", j: "+j);
                tree[i][j] = new MyVertex("V"+i+"."+j);
                graph.addVertex(tree[i][j]);

            }
        }

        //Connect the vertices
        for(int i=0; i<(x-1); i++){ //for every row except the last one
            for(int j=0; j<tree[i].length; j++){ //for every collumn in the row
               for(int yx=0; yx<y; yx++){               //for every y vertices
                  //  System.out.println("Connecting Edge "+i+","+j+" with Edge"+(i+1)+","+(j*i));
                    graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), tree[i][j], tree[i+1][(j*y)+yx]); //connect the current vertice with the y beneath it
                    edgeCounter++;
                   // System.out.println("Connecting Edge "+i+","+j+" with Edge"+(i+1)+","+((j*i)+1));
                   // graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), tree[i][j], tree[i+1][(j*y)+1]);
                  //  edgeCounter++;
               }
            }

        }

        placeTree(x, tree);


    }


    /*
    Petersen graph
    */
    public void option8(){
        int edgeCounter=1;

        MyVertex outerSet[] = new MyVertex[5];
        MyVertex innerSet[] = new MyVertex[5];

        for(int i=0; i<5; i++){
            outerSet[i] = new MyVertex("O"+Integer.toString(i));
            graph.addVertex(outerSet[i]);
        }

        for(int i=1; i<5; i++){
            graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), outerSet[i-1], outerSet[i]);
            edgeCounter++;
        }

        graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), outerSet[0], outerSet[4]);

        for(int i=0; i<5; i++){
            innerSet[i] = new MyVertex("I"+Integer.toString(i));
            graph.addVertex(innerSet[i]);
        }

        for(int i=0; i<5; i++){
            graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), outerSet[i], innerSet[i]);
            edgeCounter++;
        }

        graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), innerSet[1-1], innerSet[3-1]);
        graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), innerSet[3-1], innerSet[5-1]);
        graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), innerSet[5-1], innerSet[2-1]);
        graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), innerSet[2-1], innerSet[4-1]);
        graph.addEdge(new MyEdge("E"+Integer.toString(edgeCounter)), innerSet[4-1], innerSet[1-1]);

        double innerRadius = (panelHeight/2)-(double)130;
        double outerRadius = (panelHeight/2)-(double)30;

        placeCircle(innerSet,innerRadius, 60);
        placeCircle(outerSet, outerRadius,60);
    }



    /*
    Vertex Positioning Algorithms
     */

    public void placeBipartite(MyVertex firstSet[], MyVertex secondSet[]){

        double betweenDistance1 = (panelWidth)/(double)(firstSet.length+1);
        double betweenDistance2 = (panelWidth)/(double)(secondSet.length+1);

        int distanceFromTop = 100;
        int distanceFromBottom=100;


        firstSet[0].setY(distanceFromTop);
        firstSet[0].setX(betweenDistance1);

        for(int i=1; i<firstSet.length; i++){
            firstSet[i].setY(distanceFromTop);
            firstSet[i].setX(firstSet[i-1].getX()+betweenDistance1);
        }

        secondSet[0].setY(panelHeight-distanceFromBottom);
        secondSet[0].setX(betweenDistance2);

        for(int i=1; i<secondSet.length; i++){
            secondSet[i].setY(panelHeight-distanceFromBottom);
            secondSet[i].setX(secondSet[i-1].getX()+betweenDistance2);
        }


    }

    public void placePath(MyVertex vertex[]){
        double betweenDistance1 = (panelWidth)/(double)(vertex.length+1);
        double yPosition = panelHeight/2;

        vertex[0].setY(yPosition);
        vertex[0].setX(betweenDistance1);

        for(int i=1; i<vertex.length; i++){
            vertex[i].setY(yPosition);
            vertex[i].setX(vertex[i-1].getX()+betweenDistance1);
        }

    }

    public void placeGrid(MyVertex vertex[][]){

        double betweenDistance1 = (panelWidth)/(double)(vertex.length+1);
        double betweenDistance2 = (panelHeight)/(double)(vertex[0].length+1);


        //align for the x axis
        for(int i=0; i<vertex[0].length; i++){
            vertex[0][i].setX(betweenDistance1);
        }

        for(int j=0; j<vertex[0].length; j++){
            for(int i=1; i<vertex.length; i++){
                   vertex[i][j].setX(vertex[i - 1][j].getX() + betweenDistance1);
            }
        }


        //align for the y axis
        for(int i=0; i<vertex.length; i++){
            vertex[i][0].setY(betweenDistance2);
        }


        for(int i=0; i<vertex.length; i++){
            for(int j=1; j<vertex[0].length; j++){
                    vertex[i][j].setY(vertex[i][j-1].getY() + betweenDistance2);
            }
        }




    }

    public void placeTree(int depth, MyVertex tree[][]){


        double widthDistance[] = new double[depth];

        for(int i=0; i<depth; i++){
            widthDistance[i] = panelWidth/(double)(tree[i].length+1);
            System.out.println("Width Distance for row "+i+", "+widthDistance[i]);
        }

        double heightDistance = (panelHeight)/(double)(depth+1);
        System.out.println("Height Distance "+heightDistance);


        for(int x=0; x<depth; x++){
           tree[x][0].setX(widthDistance[x]);
        }

        for(int x=1; x<depth; x++){
            for(int y=1; y<tree[x].length; y++){
                System.out.println("X position for node ["+x+"]["+y+"] : "+(tree[x][y-1].getX()+ widthDistance[x]));
                tree[x][y].setX(tree[x][y-1].getX()+ widthDistance[x]);
            }
        }

         tree[0][0].setY(heightDistance);

        for(int x=1; x<depth; x++){
            for(int y=0; y<tree[x].length; y++){

                tree[x][y].setY(tree[x - 1][0].getY() + heightDistance);
            }
        }

    }

    public void placeCircle(MyVertex vertex[], double radius, double initialAngle){

        double points = 2 * Math.PI/vertex.length;
        System.out.println("Im going to place "+points+" on the circle");

       // double radius = (panelHeight/2)-(double)20;
        System.out.println("The radius is "+radius+" pixels");

        double a = panelWidth/(double)2;
        double b = panelHeight/(double)2;

        double t=initialAngle;
        System.out.println("Initial angle is "+t);


        for(int i=0; i<vertex.length; i++){
            System.out.println("Placing vertex["+i+"] at "+(a+(radius*Math.cos(t)))+","+(b + (radius * Math.sin(t))));
            vertex[i].setX(a+(radius*Math.cos(t)));
            vertex[i].setY(b + (radius * Math.sin(t)));
            t=t+points;
            System.out.println("New angle is "+t);
        }

    }

}
