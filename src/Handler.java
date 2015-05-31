import org.freehep.util.export.ExportDialog;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Iterator;



public class Handler implements MouseListener{

    JFrame mainFrame;


    GraphCreator customGraph;
    CustomMenu mainMenu;
    GraphGenerator graphGenerator;
    ClickListener listener=null;

    JLabel messageLabel = new JLabel("Message");
    JLabel statusLabel = new JLabel("status");

    int width = 900;
    int height = 660;

    InOutController io = new InOutController();
    int lastOption=-1;

    InterfaceFactory factory;



    Handler(){

        mainFrame = new JFrame("Graphiti");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel testPanel = new JPanel();
        testPanel.setPreferredSize(new Dimension(width, height));
        mainFrame.setContentPane(testPanel);


        GraphicsConfiguration gc = mainFrame.getGraphicsConfiguration();
        Rectangle bounds = gc.getBounds();
        Dimension size = mainFrame.getPreferredSize();

        mainFrame.setLocation((int) ((bounds.width / 2) - (size.getWidth() / 2)), (int) ((bounds.height / 2) - (size.getHeight() / 2)));
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);

        mainMenu = new CustomMenu(this);

        try {
            mainFrame.setIconImage(new ImageIcon(io.getIcon("images/iconsmall.png")).getImage());
        }catch(Exception e){ e.printStackTrace();}

        factory = new InterfaceFactory(this);

        welcomeWindow();
      //  mainFrame.setVisible(true);
      //  createDirected();

    }

    public void welcomeWindow(){
        mainFrame.setContentPane(factory.makeWelcomeWindow());
        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    public void selectGeneratorWindow(){
        graphGenerator = new GraphGenerator();

        mainFrame.setContentPane(factory.makeSelectGeneratorWindow());
        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    public JFrame getMainFrame(){
        return mainFrame;
    }



    ///NA PAEI AUTO STON IOCONTROLLER?
    public void showImportWindow(){
        String filename=null;
        String extension=null;
        String path = null;

        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(mainFrame);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            filename = file.getName();
            path=file.getAbsolutePath();
            extension = filename.substring(filename.length()-7, filename.length());
        }

        if(extension.equals("graphml")){
               InOutController IO = new InOutController();
            try{
                mainMenu.setAllMenus();
                mainFrame.setJMenuBar(mainMenu.getMenuBar());

                customGraph = new GraphCreator(IO.readGraphML(path), "Cartesian");
                finalTouch();

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }


    public void setMouseMode(String s){
        customGraph.setMouseMode(s);
        mainMenu.setSelectedModeRadioButton(s);
        setStatusLabel(s);
    }


    public void export(String exportAs){
        InOutController IO = new InOutController();
        IO.exportGraph(exportAs, customGraph);
    }



    public void executeAlgorithm(String algorithm){
        setMouseMode("Picking");

        if(algorithm.equals("Dijkstra")){
            setMessageLabel("Select the Start & End nodes");
            listener = new ClickListener(customGraph.getView(), this, "firstNode", algorithm);
        }

        if(algorithm.equals("BFS")){
            System.out.println("Handler, new ClickListener");
            setMessageLabel("Select the Root node");
            listener = new ClickListener(customGraph.getView(), this, "oneNode", algorithm);
        }

        if(algorithm.equals("DFS")){
            setMessageLabel("Select the Root node");
            listener = new ClickListener(customGraph.getView(), this, "oneNode", algorithm);
        }

        if(algorithm.equals("Kruskal")){
            executeStepKruskal();
        }

        if(algorithm.equals("Prim")){
            setMessageLabel("Pick a Random node");
            listener = new ClickListener(customGraph.getView(), this, "oneNode", algorithm);
        }
    }

    public void executeStepDijkstra(MyVertex[] nodes){
        final StepAlgorithm dijkstra = new StepDijkstra(customGraph.getGraph(), nodes, this);
        assignResumeKey(dijkstra);
        dijkstra.start();
    }

    public void executeStepBFS(MyVertex startNode){
        final StepAlgorithm bfs = new StepBFS(customGraph.getGraph(), startNode, this);
        assignResumeKey(bfs);
        bfs.start();
    }

    public void executeStepDFS(MyVertex startNode){
        final StepAlgorithm dfs = new StepDFS(customGraph.getGraph(), startNode, this);
        assignResumeKey(dfs);
        dfs.start();
    }

    public void executeStepKruskal(){
        final StepAlgorithm kruskal = new StepKruskal(customGraph.getGraph(), this);
        assignResumeKey(kruskal);
        kruskal.start();
    }

    public void executeStepPrim(MyVertex startNode){
        final StepAlgorithm prim = new StepPrim(customGraph.getGraph(), this, startNode);
        assignResumeKey(prim);
        prim.start();
    }

    public void assignResumeKey(final StepAlgorithm algorithm){
        Action actionListener = new AbstractAction() {
            public void actionPerformed(ActionEvent actionEvent) {
                algorithm.resumeAlgorithm();
            }
        };

        InputMap im = customGraph.getView().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = customGraph.getView().getActionMap();
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0), "resume");
        am.put("resume", actionListener);
    }




    public void setMessageLabel(String s){
        messageLabel.setText(s);
    }

    public void setStatusLabel(String s){
        statusLabel.setText(s);
    }

    public String getStatusLabel(){
        return statusLabel.getText();
    }

    public String getMessageLabel(){
        return messageLabel.getText();
    }


    public void setMenu(){
        mainMenu.setAllMenus();
        mainFrame.setJMenuBar(mainMenu.getMenuBar());
    }

    public void finalTouch(){
        mainFrame.setContentPane(customGraph.getView());

        Container contentPane = mainFrame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel statusPanel = new JPanel();
        statusPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(204,204,204)));
        statusPanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 20));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS ));
        statusPanel.setBackground(new Color(247,247,247));


        statusPanel.add(Box.createRigidArea(new Dimension(5,0)));
        statusPanel.add(messageLabel);
        statusPanel.add(Box.createHorizontalGlue());
        statusPanel.add(statusLabel);
        statusPanel.add(Box.createRigidArea(new Dimension(5,0)));

        contentPane.add(statusPanel, BorderLayout.SOUTH);
        setKeys();

        setMessageLabel("");
        setStatusLabel("Editing");

        mainFrame.pack();
    }

    public void setKeys(){
        Action setMode = new AbstractAction() {
            public void actionPerformed(ActionEvent e){
                if("E".equals(e.getActionCommand())||"e".equals(e.getActionCommand())){
                    setMouseMode("Editing");
                    setStatusLabel("Editing");
                    customGraph.getView().setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }
                if("W".equals(e.getActionCommand())||"w".equals(e.getActionCommand())){
                    setMouseMode("Picking");
                    setStatusLabel("Picking");
                    customGraph.getView().setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                if("Q".equals(e.getActionCommand())||"q".equals(e.getActionCommand())){
                    setMouseMode("Transforming");
                    setStatusLabel("Transforming");
                    System.out.println("Q");
                    customGraph.getView().setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }

            }
        };

        InputMap im = customGraph.getView().getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = customGraph.getView().getActionMap();

        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E,0), "mode");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W,0), "mode");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Q,0), "mode");

        am.put("mode", setMode);

    }

    public void resetGraph(){
        Iterator v = customGraph.getGraph().getVertices().iterator();
        while(v.hasNext()){
            MyVertex vertex = (MyVertex)v.next();
            vertex.setTentativeStatus(false);
            vertex.setSet(-1);
            vertex.setColor(Color.RED);
            vertex.setTentativeDistance(0);
        }

        Iterator e = customGraph.getGraph().getEdges().iterator();
        while(e.hasNext()){
            MyEdge edge = (MyEdge)e.next();
            edge.setColor(Color.BLACK);
        }
        if(listener!=null) {
            listener.reset();
        }

        setMessageLabel("");
        refreshGUI();
    }

    public void generateAgain(){
        GraphGenerator ngenerator = new GraphGenerator();
        switch (lastOption){
            case -1 :   setMenu(); break;
            case 1 :    ngenerator.option1();setMenu(); customGraph = new GraphCreator(ngenerator.getGraph(), "Circle"); finalTouch();break;
            case 2 :    ngenerator.option2(); setMenu();customGraph = new GraphCreator(ngenerator.getGraph(), "Cartesian"); finalTouch();break;
            case 3 :    ngenerator.option3();setMenu(); customGraph = new GraphCreator(ngenerator.getGraph(), "Cartesian"); finalTouch();break;
            case 4 :    ngenerator.option4();setMenu(); customGraph = new GraphCreator(ngenerator.getGraph(), "Circle"); finalTouch();break;
            case 5 :    ngenerator.option5();setMenu(); customGraph = new GraphCreator(ngenerator.getGraph(), "Cartesian"); finalTouch();break;
            case 6 :    ngenerator.option6();setMenu(); customGraph = new GraphCreator(ngenerator.getGraph(), "Cartesian"); finalTouch();break;
            case 7 :    ngenerator.option7();setMenu(); customGraph = new GraphCreator(ngenerator.getGraph(), "Cartesian"); finalTouch();break;
            case 8 :    ngenerator.option8();setMenu(); customGraph = new GraphCreator(ngenerator.getGraph(), "Cartesian"); finalTouch();break;
        }
    }


    public void refreshGUI(){
        customGraph.refresh();
    }

    public void createDirected(){
        lastOption=-1;
        mainMenu.setAllMenus();

        mainFrame.setJMenuBar(mainMenu.getMenuBar());
        customGraph = new GraphCreator(true);
        finalTouch();
    }

    public void createUndirected(){
        mainMenu.setAllMenus(); lastOption=-1;

        mainFrame.setJMenuBar(mainMenu.getMenuBar());
        customGraph = new GraphCreator(false);
        finalTouch();
    }


    public void mousePressed(MouseEvent e) {
        JLabel label = (JLabel)e.getSource();
        if(label.getName().equals("Directed")){
            label.setIcon(new ImageIcon(io.getIcon("images/DirectedIconPressed.png")));
        }
        if(label.getName().equals("Undirected")){
            label.setIcon(new ImageIcon(io.getIcon("images/UndirectedIconPressed.png")));
        }
        if(label.getName().equals("Generator")){
            label.setIcon(new ImageIcon(io.getIcon("images/factoryIconPressed.png")));
        }
        if(label.getName().equals("Import")){
            label.setIcon(new ImageIcon(io.getIcon("images/importIconPressed.png")));
        }

        if(label.getName().equals("fullConnectedIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/fullConnectedPressed.png")));
        }
        if(label.getName().equals("biPartiteIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/bipartitePressed.png")));
        }
        if(label.getName().equals("pathIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/pathPressed.png")));
        }

        if(label.getName().equals("cycleIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/cyclePressed.png")));
        }
        if(label.getName().equals("starIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/starPressed.png")));
        }
        if(label.getName().equals("gridIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/gridPressed.png")));
        }

        if(label.getName().equals("treeIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/treePressed.png")));
        }
        if(label.getName().equals("petersenIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/petersenPressed.png")));
        }
        if(label.getName().equals("backIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/backPressed.png")));
        }

    }

    public void mouseReleased(MouseEvent e) {
        JLabel label = (JLabel)e.getSource();

        if(label.getName().equals("Directed")){
            label.setIcon(new ImageIcon(io.getIcon("images/DirectedIcon.png")));
        }
        if(label.getName().equals("Undirected")){
            label.setIcon(new ImageIcon(io.getIcon("images/UndirectedIcon.png")));
        }
        if(label.getName().equals("Generator")){
            label.setIcon(new ImageIcon(io.getIcon("images/factoryIcon.png")));
        }
        if(label.getName().equals("Import")){
            label.setIcon(new ImageIcon(io.getIcon("images/importIcon.png")));
        }


        if(label.getName().equals("fullConnectedIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/fullConnectedIcon.png")));
        }
        if(label.getName().equals("biPartiteIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/biPartiteIcon.png")));
        }
        if(label.getName().equals("pathIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/pathIcon.png")));
        }

        if(label.getName().equals("cycleIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/cycleIcon.png")));
        }
        if(label.getName().equals("starIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/starIcon.png")));
        }
        if(label.getName().equals("gridIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/gridIcon.png")));
        }

        if(label.getName().equals("treeIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/treeIcon.png")));
        }
        if(label.getName().equals("petersenIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/petersenIcon.png")));
        }
        if(label.getName().equals("backIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/backIcon.png")));
        }
    }

    public void mouseEntered(MouseEvent e) {
        JLabel label = (JLabel)e.getSource();
        if(label.getName().equals("Directed")){
            label.setIcon(new ImageIcon(io.getIcon("images/DirectedIconHovered.png")));
        }
        if(label.getName().equals("Undirected")){
            label.setIcon(new ImageIcon(io.getIcon("images/UndirectedIconHovered.png")));
        }
        if(label.getName().equals("Generator")){
            label.setIcon(new ImageIcon(io.getIcon("images/factoryIconHovered.png")));
        }
        if(label.getName().equals("Import")){
            label.setIcon(new ImageIcon(io.getIcon("images/importIconHovered.png")));
        }

        if(label.getName().equals("fullConnectedIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/fullConnectedHovered.png")));
        }
        if(label.getName().equals("biPartiteIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/bipartiteHovered.png")));
        }
        if(label.getName().equals("pathIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/pathHovered.png")));
        }

        if(label.getName().equals("cycleIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/cycleHovered.png")));
        }
        if(label.getName().equals("starIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/starHovered.png")));
        }
        if(label.getName().equals("gridIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/gridHovered.png")));
        }

        if(label.getName().equals("treeIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/treeHovered.png")));
        }
        if(label.getName().equals("petersenIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/petersenHovered.png")));
        }
        if(label.getName().equals("backIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/backHovered.png")));
        }

    }

    public void mouseExited(MouseEvent e) {
        JLabel label = (JLabel)e.getSource();
        if(label.getName().equals("Directed")){
            label.setIcon(new ImageIcon(io.getIcon("images/DirectedIcon.png")));
        }
        if(label.getName().equals("Undirected")){
            label.setIcon(new ImageIcon(io.getIcon("images/UndirectedIcon.png")));
        }
        if(label.getName().equals("Generator")){
            label.setIcon(new ImageIcon(io.getIcon("images/factoryIcon.png")));
        }
        if(label.getName().equals("Import")){
            label.setIcon(new ImageIcon(io.getIcon("images/importIcon.png")));
        }


        if(label.getName().equals("fullConnectedIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/fullConnectedIcon.png")));
        }
        if(label.getName().equals("biPartiteIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/biPartiteIcon.png")));
        }
        if(label.getName().equals("pathIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/pathIcon.png")));
        }

        if(label.getName().equals("cycleIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/cycleIcon.png")));
        }
        if(label.getName().equals("starIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/starIcon.png")));
        }
        if(label.getName().equals("gridIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/gridIcon.png")));
        }

        if(label.getName().equals("treeIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/treeIcon.png")));
        }
        if(label.getName().equals("petersenIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/petersenIcon.png")));
        }
        if(label.getName().equals("backIcon")){
            label.setIcon(new ImageIcon(io.getIcon("images/backIcon.png")));
        }

    }

    public void mouseClicked(MouseEvent e) {
        JLabel label = (JLabel)e.getSource();

        if(label.getName().equals("Directed")){
            createDirected();
        }
        if(label.getName().equals("Undirected")){
            createUndirected();
        }
        if(label.getName().equals("Generator")){
            selectGeneratorWindow();
        }
        if(label.getName().equals("Import")){
           showImportWindow();
        }

        if(label.getName().equals("fullConnectedIcon")){
            graphGenerator.option1(); lastOption=1;
            setMenu();

            customGraph = new GraphCreator(graphGenerator.getGraph(), "Circle");
            finalTouch();
        }

        if(label.getName().equals("biPartiteIcon")){
            graphGenerator.option2(); lastOption=2;
            setMenu();

            customGraph = new GraphCreator(graphGenerator.getGraph(), "Cartesian");
            finalTouch();
        }
        if(label.getName().equals("pathIcon")){
            graphGenerator.option3(); lastOption=3;
            setMenu();

            customGraph = new GraphCreator(graphGenerator.getGraph(), "Cartesian");
            finalTouch();
        }

        if(label.getName().equals("cycleIcon")){

            graphGenerator.option4(); lastOption=4;
            setMenu();

            customGraph = new GraphCreator(graphGenerator.getGraph(), "Circle");
            finalTouch();

        }
        if(label.getName().equals("starIcon")){
            graphGenerator.option5(); lastOption=5;
            setMenu();

            customGraph = new GraphCreator(graphGenerator.getGraph(), "Cartesian");
            finalTouch();
        }
        if(label.getName().equals("gridIcon")){
            graphGenerator.option6(); lastOption=6;
            setMenu();

            customGraph = new GraphCreator(graphGenerator.getGraph(), "Cartesian");
            finalTouch();
        }

        if(label.getName().equals("treeIcon")){
            graphGenerator.option7(); lastOption=7;
            setMenu();

            customGraph = new GraphCreator(graphGenerator.getGraph(), "Cartesian");
            finalTouch();
        }
        if(label.getName().equals("petersenIcon")){
            graphGenerator.option8(); lastOption=8;
            setMenu();

            customGraph = new GraphCreator(graphGenerator.getGraph(), "Cartesian");
            finalTouch();
        }
        if(label.getName().equals("backIcon")){
            welcomeWindow();
        }

    }


}
