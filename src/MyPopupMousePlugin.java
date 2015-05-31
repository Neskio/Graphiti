import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;


public class MyPopupMousePlugin extends AbstractPopupGraphMousePlugin implements ActionListener {

    MyVertex currentVertex =null;
    MyEdge currentEdge =null;
    VisualizationViewer<MyVertex,MyEdge> currentvv=null;


    public void handlePopup(MouseEvent e){
        currentvv =  (VisualizationViewer<MyVertex,MyEdge>)e.getSource();
        Point2D p = e.getPoint();

        GraphElementAccessor<MyVertex,MyEdge> pickSupport = currentvv.getPickSupport();
        if(pickSupport != null) {
            MyVertex v = pickSupport.getVertex(currentvv.getGraphLayout(), p.getX(), p.getY());
            if(v != null) {
                currentVertex = v;
                JPopupMenu popup = new JPopupMenu();

                JMenuItem menuItem = new JMenuItem("Change ID");
                JMenuItem menuItemDelete = new JMenuItem("Delete Node");
                menuItem.setBackground(new Color(247,247,247));

                menuItem.setActionCommand("ChangeNodeID");
                menuItem.addActionListener(this);

                menuItemDelete.setActionCommand("DeleteNode");
                menuItemDelete.addActionListener(this);
                menuItemDelete.setBackground(new Color(247, 247, 247));

                popup.add(menuItem);
                popup.add(new MySeparator());
                popup.add(menuItemDelete);
                popup.show(e.getComponent(),  e.getX(), e.getY());

            } else {
                currentEdge = pickSupport.getEdge(currentvv.getGraphLayout(), p.getX(), p.getY());
                if(currentEdge != null) {
                    System.out.println("Edge  was right clicked");
                    JPopupMenu popup = new JPopupMenu();

                    JMenuItem menuItemWeight = new JMenuItem("Add weight");
                    JMenuItem menuItemId = new JMenuItem("Change ID");
                    JMenuItem menuItemDelete = new JMenuItem("Delete Edge");

                    menuItemWeight.setActionCommand("SetEdgeWeight");
                    menuItemWeight.setBackground(new Color(247, 247, 247));
                    menuItemWeight.addActionListener(this);

                    menuItemId.setActionCommand("ChangeEdgeId");
                    menuItemId.setBackground(new Color(247, 247, 247));
                    menuItemId.addActionListener(this);

                    menuItemDelete.setActionCommand("DeleteEdge");
                    menuItemDelete.setBackground(new Color(247, 247, 247));
                    menuItemDelete.addActionListener(this);

                    popup.add(menuItemId);
                    popup.add(menuItemWeight);
                    popup.add(new MySeparator());
                    popup.add(menuItemDelete);
                    popup.show(e.getComponent(),  e.getX(), e.getY());
                }
            }
        }

    }

    public void setNodeId(){
        JTextField idField = new JTextField();
        final JComponent[] inputs = new JComponent[] {  new JLabel("New ID"), idField };
        JOptionPane.showMessageDialog(null, inputs, "Set new Node ID", JOptionPane.PLAIN_MESSAGE);
        currentVertex.setId(idField.getText());
    }

    public void setEdgeId(){
        JTextField idField = new JTextField();
        final JComponent[] inputs = new JComponent[] {  new JLabel("New ID"), idField };
        JOptionPane.showMessageDialog(null, inputs, "Set new Edge ID", JOptionPane.PLAIN_MESSAGE);
        currentEdge.setId(idField.getText());
    }

    public void setEdgeWeight(){
        JTextField weightField = new JTextField();
        final JComponent[] inputs = new JComponent[] {  new JLabel("New Weight"), weightField };
        JOptionPane.showMessageDialog(null, inputs, "Add Weight", JOptionPane.PLAIN_MESSAGE);
        currentEdge.setWeight(Double.parseDouble(weightField.getText()));
    }


    public void actionPerformed(ActionEvent e){
        if("ChangeNodeID".equals(e.getActionCommand())){
            setNodeId();
            currentvv.updateUI();
        }
        if("DeleteNode".equals(e.getActionCommand())){
            currentvv.getGraphLayout().getGraph().removeVertex(currentVertex);
            currentvv.updateUI();
        }
        if("ChangeEdgeId".equals(e.getActionCommand())){
            setEdgeId();
            currentvv.updateUI();
        }
        if("SetEdgeWeight".equals(e.getActionCommand())){
            setEdgeWeight();
            currentvv.updateUI();
        }
        if("DeleteEdge".equals(e.getActionCommand())){
            currentvv.getGraphLayout().getGraph().removeEdge(currentEdge);
            currentvv.updateUI();
        }




    }
}
