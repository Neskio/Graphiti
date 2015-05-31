import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class CustomMenu implements ActionListener{

    JMenuBar menuBar;
    Handler handler;

    Color myGray = new Color(247,247,247);

    JRadioButtonMenuItem editing;
    JRadioButtonMenuItem tranforming;
    JRadioButtonMenuItem picking;


    CustomMenu(Handler handler){
        menuBar = new JMenuBar();
        this.handler=handler;

        menuBar.setBackground(myGray);
    }

    public void setAllMenus(){
        setFileMenu();
        setModeMenu();
        setAlgorithmMenu();
        setHelpMenu();
    }



    public void setFileMenu(){
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem jmMain = new JMenuItem("Main Menu");
        jmMain.setActionCommand("Main Menu");
        jmMain.addActionListener(this);
        jmMain.setOpaque(true);
        jmMain.setBackground(myGray);
        jmMain.setMnemonic(KeyEvent.VK_M);

        JMenu jmNew = new JMenu("New");
        jmNew.setActionCommand("New");
        jmNew.addActionListener(this);
        jmNew.setOpaque(true);
        jmNew.setBackground(myGray);
        jmNew.setMnemonic('N');

        JMenuItem jmDirected = new JMenuItem("Directed Graph");
        jmDirected.setActionCommand("Directed");
        jmDirected.addActionListener(this);
        jmDirected.setOpaque(true);
        jmDirected.setBackground(myGray);
        jmDirected.setMnemonic('D');

        JMenuItem jmUndirected = new JMenuItem("Undirected Graph");
        jmUndirected.setActionCommand("Undirected");
        jmUndirected.addActionListener(this);
        jmUndirected.setOpaque(true);
        jmUndirected.setBackground(myGray);
        jmUndirected.setMnemonic('U');

        jmNew.add(jmDirected);
        jmNew.add(jmUndirected);

        JMenuItem jmOpen = new JMenuItem("Open");
        jmOpen.setActionCommand("Open");
        jmOpen.addActionListener(this);
        jmOpen.setOpaque(true);
        jmOpen.setBackground(myGray);
        jmOpen.setMnemonic(KeyEvent.VK_O);

        JMenuItem jmSave = new JMenuItem("Save");
        jmSave.setActionCommand("GraphML");
        jmSave.addActionListener(this);
        jmSave.setOpaque(true);
        jmSave.setBackground(myGray);
        jmSave.setMnemonic(KeyEvent.VK_S);

        JMenuItem jmExport = new JMenuItem("Export");
        jmExport.setActionCommand("Open");
        jmExport.addActionListener(this);
        jmExport.setOpaque(true);
        jmExport.setBackground(myGray);
        jmExport.setMnemonic(KeyEvent.VK_S);



        JMenuItem jmGenerators = new JMenuItem("Select Generator");
        jmGenerators.setActionCommand("Generator");
        jmGenerators.addActionListener(this);
        jmGenerators.setOpaque(true);
        jmGenerators.setBackground(myGray);
        jmGenerators.setMnemonic('G');


        JMenuItem jmQuit = new JMenuItem("Exit");
        jmQuit.setActionCommand("Quit");
        jmQuit.addActionListener(this);
        jmQuit.setOpaque(true);
        jmQuit.setBackground(myGray);
        jmQuit.setMnemonic('E');

        fileMenu.add(jmNew);
        fileMenu.add(jmOpen);
        fileMenu.add(new MySeparator());

        fileMenu.add(jmSave);
        fileMenu.add(createExportMenu());
        fileMenu.add(new MySeparator());


        fileMenu.add(jmMain);
        fileMenu.add(jmGenerators);
        fileMenu.add(new MySeparator());



        fileMenu.add(jmQuit);
        fileMenu.setOpaque(true);
        fileMenu.setBackground(myGray);

        menuBar.add(fileMenu);
    }


    public void setModeMenu(){
        JMenu modeMenu = new JMenu("Edit");
        modeMenu.setMnemonic('E');

        JMenuItem generateAgain = new JMenuItem("Generate Again");
        generateAgain.setActionCommand("Again");
        generateAgain.addActionListener(this);
        generateAgain.setOpaque(true);
        generateAgain.setBackground(myGray);
        generateAgain.setMnemonic('A');


        editing = new JRadioButtonMenuItem("Editing");
        editing.addActionListener(this);
        editing.setActionCommand("Editing");
        editing.setBackground(myGray);

        tranforming = new JRadioButtonMenuItem("Transforming");
        tranforming.addActionListener(this);
        tranforming.setActionCommand("Transforming");
        tranforming.setBackground(myGray);

        picking = new JRadioButtonMenuItem("Picking");
        picking.addActionListener(this);
        picking.setActionCommand("Picking");
        picking.setBackground(myGray);



        ButtonGroup group = new ButtonGroup();
        group.add(editing);
        group.add(tranforming);
        group.add(picking);


        modeMenu.add(editing);
        modeMenu.add(tranforming);
        modeMenu.add(picking);
        editing.setSelected(true);

        modeMenu.add(new MySeparator());
        modeMenu.add(generateAgain);

        menuBar.add(modeMenu);
    }

    public JMenu createExportMenu(){
        JMenu exportMenu = new JMenu("Export");
        exportMenu.setMnemonic('E');
        exportMenu.setOpaque(true);
        exportMenu.setBackground(myGray);

        JMenuItem graphML = new JMenuItem("GraphML");
        graphML.setActionCommand("GraphML");
        graphML.addActionListener(this);
        graphML.setMnemonic('G');
        graphML.setBackground(myGray);


        JMenuItem toImage = new JMenuItem( "Image" );
        toImage.setActionCommand("toImage");
        toImage.addActionListener(this);
        toImage.setMnemonic('I');
        toImage.setBackground(myGray);


        JMenuItem toSVG = new JMenuItem( "SVG" );
        toSVG.setActionCommand("toSVG");
        toSVG.addActionListener(this);
        toSVG.setMnemonic('S');
        toSVG.setBackground(myGray);
        exportMenu.add(toSVG);


        JMenuItem toPS = new JMenuItem( "PostScript" );
        toPS.setActionCommand("toPS");
        toPS.addActionListener(this);
        toPS.setMnemonic('P');
        toPS.setBackground(myGray);
        exportMenu.add(toPS);

        exportMenu.add(new MySeparator());
        exportMenu.add(toImage);
        exportMenu.add(new MySeparator());

        return exportMenu;
    }

    public void setAlgorithmMenu(){
        JMenu algorithmMenu = new JMenu("Algorithms");
        algorithmMenu.setMnemonic('A');

        JMenuItem dijkstra = new JMenuItem("Dijsktra");
        dijkstra.setActionCommand("dijkstra");
        dijkstra.addActionListener(this);
        dijkstra.setMnemonic('D');
        dijkstra.setBackground(myGray);


        JMenuItem BFS = new JMenuItem("BFS");
        BFS.setActionCommand("BFS");
        BFS.addActionListener(this);
        BFS.setMnemonic('B');
        BFS.setBackground(myGray);


        JMenuItem DFS = new JMenuItem("DFS");
        DFS.setActionCommand("DFS");
        DFS.addActionListener(this);
        DFS.setMnemonic('F');
        DFS.setBackground(myGray);


        JMenuItem Kruskal = new JMenuItem("Kruskal");
        Kruskal.setActionCommand("Kruskal");
        Kruskal.addActionListener(this);
        Kruskal.setMnemonic('K');
        Kruskal.setBackground(myGray);


        JMenuItem Prim = new JMenuItem("Prim");
        Prim.setActionCommand("Prim");
        Prim.addActionListener(this);
        Prim.setMnemonic('P');
        Prim.setBackground(myGray);

        algorithmMenu.add(BFS);
        algorithmMenu.add(DFS);
        algorithmMenu.add(new MySeparator());
        algorithmMenu.add(Prim);
        algorithmMenu.add(Kruskal);
        algorithmMenu.add(new MySeparator());
        algorithmMenu.add(dijkstra);

        menuBar.add(algorithmMenu);
    }

    public void setHelpMenu(){
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic('H');

        JMenuItem controls = new JMenuItem("Controls");
        controls.setActionCommand("Controls");
        controls.addActionListener(this);
        controls.setMnemonic('C');
        controls.setBackground(myGray);
        helpMenu.add(controls);

        helpMenu.add(new MySeparator());

        JMenuItem about = new JMenuItem("About");
        about.setActionCommand("About");
        about.addActionListener(this);
        about.setBackground(myGray);
        about.setMnemonic('A');
        helpMenu.add(about);



        menuBar.add(helpMenu);
    }


    public JMenuBar getMenuBar(){
        return menuBar;
    }

    public void setSelectedModeRadioButton(String s){
        if(s.equals("Picking")){
                editing.setSelected(false);
                picking.setSelected(true);
                tranforming.setSelected(false);
        }
         if(s.equals("Editing"))    {
                editing.setSelected(true);
                picking.setSelected(false);
                tranforming.setSelected(false);
         }
         if(s.equals("Transforming")){
                editing.setSelected(false);
                picking.setSelected(false);
                tranforming.setSelected(true);
         }
    }


    public void actionPerformed(ActionEvent e){

        if("Main Menu".equals(e.getActionCommand())){
            menuBar.removeAll();
            handler.welcomeWindow();
        }
        if("Generator".equals(e.getActionCommand())){
            menuBar.removeAll();
            handler.selectGeneratorWindow();
        }
        if("Quit".equals(e.getActionCommand())){
           System.exit(1);
        }


        if("Editing".equals(e.getActionCommand())){
            handler.setMouseMode("Editing");
        }
        if("Picking".equals(e.getActionCommand())){
            handler.setMouseMode("Picking");
        }
        if("Transforming".equals(e.getActionCommand())){
            handler.setMouseMode("Transforming");
        }


        if("GraphML".equals(e.getActionCommand())){
            handler.export("GraphML");
        }
        if("toImage".equals(e.getActionCommand())){
            handler.export("toImage");
        }
        if("toSVG".equals(e.getActionCommand())){
            handler.export("toSVG");
        }
        if("toPS".equals(e.getActionCommand())){
            handler.export("toPS");
        }


        if("dijkstra".equals(e.getActionCommand())){
            handler.executeAlgorithm("Dijkstra");
        }

        if("BFS".equals(e.getActionCommand())){
            handler.executeAlgorithm("BFS");
        }

        if("DFS".equals(e.getActionCommand())){
            handler.executeAlgorithm("DFS");
        }

        if("Kruskal".equals(e.getActionCommand())){
            handler.executeAlgorithm("Kruskal");
        }

        if("Prim".equals(e.getActionCommand())){
            handler.executeAlgorithm("Prim");
        }



        if("Controls".equals(e.getActionCommand())){
           JOptionPane.showMessageDialog(handler.getMainFrame(), "Q: Graph Moving \nW: Node Moving \nE: Editing tool ", "Controls", JOptionPane.INFORMATION_MESSAGE);
        }
        if("About".equals(e.getActionCommand())){
            JOptionPane.showMessageDialog(handler.getMainFrame(), "Graphiti \n2014, Nick Deligiannis ", "About", JOptionPane.PLAIN_MESSAGE );
        }

        if("Again".equals(e.getActionCommand())){
            menuBar.removeAll();
            handler.generateAgain();
        }




        if("Directed".equals(e.getActionCommand())){
            menuBar.removeAll();
            handler.createDirected();
        }

        if("Undirected".equals(e.getActionCommand())){
            menuBar.removeAll();
            handler.createUndirected();
        }

        if("Open".equals(e.getActionCommand())){
            menuBar.removeAll();
            handler.showImportWindow();
        }


    }





}
