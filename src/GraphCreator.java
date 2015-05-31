import edu.uci.ics.jung.algorithms.layout.*;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.MouseListenerTranslator;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.MapIterator;
import org.apache.commons.collections15.Transformer;
import org.freehep.util.export.ExportDialog;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.Iterator;


public class GraphCreator{

    Graph<MyVertex, MyEdge> graph;
    AbstractLayout<MyVertex, MyEdge> layout;
    VisualizationViewer<MyVertex,MyEdge> view;

    Factory<MyVertex> vertexFactory;
    Factory<MyEdge> edgeFactory;

    EditingModalGraphMouse mouse;

    int nodeCount, edgeCount;


    int width = 900;
    int height = 638;


    public GraphCreator(edu.uci.ics.jung.graph.Graph<MyVertex, MyEdge> graph, String layoutStyle){

        this.graph=graph;

        if("Circle".equals(layoutStyle)){
            layout = new CircleLayout<MyVertex, MyEdge>(graph);
        }
        if("Spring".equals(layoutStyle)){
            layout = new SpringLayout<MyVertex, MyEdge>(graph);
        }
        if("Star".equals(layoutStyle)){
            layout = new FRLayout2<MyVertex, MyEdge>(graph);
        }
        if("Test".equals(layoutStyle)){
            layout = new ISOMLayout<MyVertex, MyEdge>(graph);
        }
        if("Cartesian".equals(layoutStyle)){
            layout = new StaticLayout<MyVertex, MyEdge>(graph);
        }
        if("Cartesian".equals(layoutStyle)){
            placeNodes();
        }

        initialize();

    }

    public GraphCreator(boolean directed){

        if(directed){ graph = new DirectedSparseMultigraph<MyVertex, MyEdge>(); }
        else { graph = new SparseMultigraph<MyVertex, MyEdge>(); }

        layout = new StaticLayout(graph);

        initialize();
    }


    public void initialize(){

        layout.setSize(new Dimension(width,height));

        view = new VisualizationViewer<MyVertex, MyEdge>(layout);
        view.setPreferredSize(new Dimension(width,height));

        view.getRenderContext().setVertexLabelTransformer(
                new Transformer<MyVertex, String>() {
                    public String transform(MyVertex vertex) {
                        String s = null;
                        if(vertex.getTentativeStatus()){

                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(vertex.getId());
                            stringBuilder.append("   ");
                            stringBuilder.append(vertex.getTentativeDistance());
                            s=stringBuilder.toString();
                        }
                        else{
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(vertex.getId());
                            s=stringBuilder.toString();
                        }
                        return s;
                    }
                }

        );
        view.getRenderContext().setEdgeLabelTransformer(
                new Transformer<MyEdge, String>() {
                    public String transform(MyEdge edge) {
                        String s = null;
                        if(edge.getWeight()==0){
                            s="";
                        }
                        else{
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(edge.getWeight());
                            s=stringBuilder.toString();
                        }
                        return s;
                    }
                }
        );

        view.getRenderContext().setVertexFillPaintTransformer(
                new Transformer<MyVertex, Paint>() {
                    public Paint transform(MyVertex vertex) {
                        return vertex.getColor();
                    }
                }
        );
        view.getRenderContext().setEdgeDrawPaintTransformer(
                new Transformer<MyEdge, Paint>() {
                    public Paint transform(MyEdge edge) {
                        return edge.getColor();
                    }
                }
        );
        view.setBackground(Color.WHITE);

        vertexFactory = new Factory<MyVertex>() {
            public MyVertex create() {
                return new MyVertex("N"+Integer.toString(++nodeCount));
            }
        };

        edgeFactory = new Factory<MyEdge>() {
            public MyEdge create() {
                return new MyEdge("E"+Integer.toString(++edgeCount));
            }
        };


        mouse = new EditingModalGraphMouse(view.getRenderContext(), vertexFactory, edgeFactory);
        view.setGraphMouse(mouse);
        mouse.remove(mouse.getPopupEditingPlugin());

        MyPopupMousePlugin popupPlugin = new MyPopupMousePlugin();
        mouse.add(popupPlugin);
        mouse.setMode(ModalGraphMouse.Mode.EDITING);

    }

    public void placeNodes(){
        Iterator i = graph.getVertices().iterator();
        while(i.hasNext()){
            MyVertex tempv = (MyVertex)i.next();
            layout.setLocation(tempv, tempv.getX(), tempv.getY());
        }
    }

    public void refresh(){
        view.repaint();
    }

    public void setMouseMode(String mode){
        if("Editing".equals(mode)){
            mouse.setMode(ModalGraphMouse.Mode.EDITING);
        }
        if("Picking".equals(mode)){
            mouse.setMode(ModalGraphMouse.Mode.PICKING);
        }
        if("Transforming".equals(mode)){
            mouse.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        }
    }

    public VisualizationViewer<MyVertex,MyEdge> getView(){ return view; }
    public AbstractLayout<MyVertex, MyEdge> getLayout() { return layout;}
    public Graph<MyVertex, MyEdge> getGraph() { return graph; }


}
