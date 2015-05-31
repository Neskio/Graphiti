import edu.uci.ics.jung.graph.Graph;

/**
    *   This superclass represents the general structure of a step by step algorithm.
    *   Every subclass overrides the algorithm() method accordingly.
    *
    *   In order to pause the algorithm without interrupting the main process, a new separate thread is created solely for the algorithm.
    *
    *   The run() method must be called in order to start the new thread (and the execution of the algorithm).
    *   The pause() method puts the thread to sleep in every iteration. This prevents cycle stealing, keeping the CPU utilisation low during the pause.
*/



public class StepAlgorithm extends Thread {

    Graph<MyVertex, MyEdge> graph;
    Handler handler;
    boolean resume=false;
    String previousStatusMessage;

    StepAlgorithm(Graph<MyVertex, MyEdge> graph, Handler handler){
        this.graph=graph;
        this.handler=handler;
        previousStatusMessage=handler.getStatusLabel();
    }

    public void pause(){
        resume=false;
        handler.refreshGUI();
        do{
            try{
                System.out.println("Inside pause");
                Thread.sleep(200);
            }catch (Exception e){}
        }while(!resume);
    }

    public void resumeAlgorithm(){
        resume=true;
    }

    public void run(){
        algorithm();
    }

    public void algorithm(){}

}
