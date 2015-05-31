import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.GraphMLWriter;
import edu.uci.ics.jung.io.graphml.*;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import org.apache.commons.collections15.Transformer;
import org.freehep.graphics2d.VectorGraphics;
import org.freehep.graphicsio.ps.PSGraphics2D;
import org.freehep.graphicsio.svg.SVGGraphics2D;
import org.freehep.util.export.ExportDialog;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class InOutController {

    InOutController(){
        edgeCount=0;
    }
    int edgeCount;

    public String edgeFactory(){
        String s;
        s="E"+edgeCount++;
        return s;
    }



    public void toPS(VisualizationViewer<MyVertex,MyEdge> view, String path ) throws Exception{
        Properties p = new Properties();
        p.setProperty("PageSize","A5");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path);
        stringBuilder.append(".ps");
        path=stringBuilder.toString();


        VectorGraphics g = new PSGraphics2D(new File(path), new Dimension(900,617));
        g.setProperties(p);
        g.startExport();
        view.print(g);
        g.endExport();
    }

    public void toSVG(VisualizationViewer<MyVertex,MyEdge> view, String path ) throws Exception{
        Properties p = new Properties();
        p.setProperty("PageSize","A5");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(path);
        stringBuilder.append(".svg");
        path=stringBuilder.toString();

        VectorGraphics g = new SVGGraphics2D(new File(path), new Dimension(900,617));
        g.setProperties(p);
        g.startExport();
        view.print(g);
        g.endExport();
    }

    public void toGraphML(final AbstractLayout layout, edu.uci.ics.jung.graph.Graph<MyVertex, MyEdge> graph, String filename) throws IOException{

        GraphMLWriter<MyVertex, MyEdge> graphWriter = new GraphMLWriter<MyVertex, MyEdge> ();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(filename);
        stringBuilder.append(".graphml");

        PrintWriter out = new PrintWriter( new BufferedWriter( new FileWriter(stringBuilder.toString())));


        graphWriter.addVertexData("x", null, "0", new Transformer<MyVertex, String>() {
                    public String transform(MyVertex v) {
                        return Double.toString(layout.getX(v));
                    }
                }
        );


        graphWriter.addVertexData("y", null, "0", new Transformer<MyVertex, String>() {
                    public String transform(MyVertex v) {
                        return Double.toString(layout.getY(v));
                    }
                }
        );


        graphWriter.addEdgeData("weight", null, "0", new Transformer<MyEdge, String>() {
                    public String transform(MyEdge edge) {
                        return Double.toString(edge.getWeight());
                    }
        }
        );


        graphWriter.save(graph, out);

    }

    public void exportGraph(String exportAs, GraphCreator customGraph){
        if("GraphML".equals(exportAs)){
            try{
                JFileChooser c = new JFileChooser();
                int userSelection = c.showSaveDialog(new JPanel());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = c.getSelectedFile();
                    toGraphML(customGraph.getLayout(), customGraph.getGraph(), fileToSave.getAbsolutePath());
                }
            }
            catch(Exception eIO){
                eIO.printStackTrace();
            }
        }

        if("toImage".equals(exportAs)){
            try {
                ExportDialog export = new ExportDialog();
                export.showExportDialog(customGraph.getView(), "Export view as ...", customGraph.getView(), "export");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if("toSVG".equals(exportAs)){
            try {
                JFileChooser c = new JFileChooser();
                int userSelection = c.showSaveDialog(new JPanel());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = c.getSelectedFile();
                    toSVG(customGraph.getView(), fileToSave.getAbsolutePath());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if("toPS".equals(exportAs)){
            try {
                JFileChooser c = new JFileChooser();
                int userSelection = c.showSaveDialog(new JPanel());

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = c.getSelectedFile();
                    toPS(customGraph.getView(), fileToSave.getAbsolutePath());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public BufferedImage getIcon(String path){

        BufferedImage image = null;
        try {
            URL myurl = this.getClass().getResource(path);
              image=ImageIO.read(myurl);
        }catch(Exception e){
            e.printStackTrace();
        }

        return image;
    }

    public edu.uci.ics.jung.graph.Graph<MyVertex, MyEdge> readGraphML(String filename) throws IOException, GraphIOException{

        edu.uci.ics.jung.graph.Graph graph;

        InputStream inputstream = new FileInputStream(filename);
        InputStreamReader reader = new InputStreamReader(inputstream);


        Transformer<GraphMetadata, edu.uci.ics.jung.graph.Graph<MyVertex, MyEdge>> graphTransformer = new Transformer<GraphMetadata,   edu.uci.ics.jung.graph.Graph<MyVertex, MyEdge>>() {
            public edu.uci.ics.jung.graph.Graph<MyVertex, MyEdge>
            transform(GraphMetadata metadata) {
                    return new SparseGraph<MyVertex, MyEdge>();
            }
        };


        Transformer<NodeMetadata, MyVertex> vertexTransformer   = new Transformer<NodeMetadata, MyVertex>() {
            public MyVertex transform(NodeMetadata metadata) {
                MyVertex vertex = new MyVertex(metadata.getId());
                if(metadata.getProperty("x") != null){
                    vertex.setX(Double.parseDouble(
                            metadata.getProperty("x")));
                    vertex.setY(Double.parseDouble(
                            metadata.getProperty("y")));
                }
                return vertex;
            }
        };

        Transformer<EdgeMetadata, MyEdge> edgeTransformer =  new Transformer<EdgeMetadata, MyEdge>() {
            public MyEdge transform(EdgeMetadata metadata) {
                MyEdge edge = new MyEdge(edgeFactory());
                if(metadata.getProperty("weight") != null){
                    edge.setWeight(Double.parseDouble(metadata.getProperty("weight")));
                }
                return edge;
            }
        };

        Transformer<HyperEdgeMetadata, MyEdge> hyperEdgeTransformer = new Transformer<HyperEdgeMetadata, MyEdge>() {
            public MyEdge transform(HyperEdgeMetadata metadata) {
                String e = metadata.toString();
                MyEdge edge = new MyEdge(e);
                return edge;
            }
        };


        GraphMLReader2<edu.uci.ics.jung.graph.Graph<MyVertex, MyEdge>, MyVertex, MyEdge>  graphReader = new GraphMLReader2<edu.uci.ics.jung.graph.Graph<MyVertex, MyEdge>, MyVertex, MyEdge> (reader, graphTransformer, vertexTransformer,  edgeTransformer, hyperEdgeTransformer);


        graph = graphReader.readGraph();

        return graph;

    }



}
