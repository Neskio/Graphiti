import javax.swing.*;
import java.awt.*;


public class InterfaceFactory {

    Handler handler;
    InOutController io = new InOutController();

    int width = 900;
    int height = 660;

    InterfaceFactory(Handler handler){
        this.handler=handler;
    }


    public JPanel makeWelcomeWindow() {
        JLabel directedIcon = new JLabel(new ImageIcon(io.getIcon("images/DirectedIcon.png")));
        directedIcon.setName("Directed");
        JLabel undirectedIcon = new JLabel(new ImageIcon(io.getIcon("images/UndirectedIcon.png")));
        undirectedIcon.setName("Undirected");
        JLabel generatorIcon = new JLabel(new ImageIcon(io.getIcon("images/factoryIcon.png")));
        generatorIcon.setName("Generator");
        JLabel importIcon = new JLabel(new ImageIcon(io.getIcon("images/importIcon.png")));
        importIcon.setName("Import");

        directedIcon.addMouseListener(handler);
        undirectedIcon.addMouseListener(handler);
        generatorIcon.addMouseListener(handler);
        importIcon.addMouseListener(handler);

        JLabel directedText = new JLabel("Create a new Directed Graph");
        JLabel undirectedText = new JLabel("Create a new Undirected Graph");
        JLabel generatorText = new JLabel("Generator");
        JLabel importText = new JLabel("Import from File");


        Container cont = new Container();
        GroupLayout layout = new GroupLayout(cont);
        cont.setLayout(layout);

        cont.add(undirectedIcon);
        cont.add(directedIcon);
        cont.add(generatorIcon);
        cont.add(importIcon);


        cont.add(directedText);
        cont.add(undirectedText);
        cont.add(generatorText);
        cont.add(importText);


        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        layout.setHorizontalGroup(
                layout.createSequentialGroup()

                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(directedIcon)
                                .addComponent(directedText))
                        .addGap(140)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(undirectedIcon)
                                        .addComponent(undirectedText)
                                        .addComponent(importIcon)
                                        .addComponent(importText)
                        )
                        .addGap(140)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(generatorIcon)
                                .addComponent(generatorText))

        );


        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(60)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(directedIcon)
                                .addComponent(undirectedIcon)
                                .addComponent(generatorIcon))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(directedText)
                                .addComponent(undirectedText)
                                .addComponent(generatorText))
                        .addGap(120)
                        .addComponent(importIcon)
                        .addComponent(importText)

        );

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(cont);

        panel.setPreferredSize(new Dimension(width, height));
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);


        return panel;
    }

    public JPanel makeSelectGeneratorWindow(){


        JLabel fullConnectedIcon = new JLabel(new ImageIcon(io.getIcon("images/fullConnectedIcon.png")));
        fullConnectedIcon.setName("fullConnectedIcon");
        JLabel biPartiteIcon = new JLabel(new ImageIcon(io.getIcon("images/biPartiteIcon.png")));
        biPartiteIcon.setName("biPartiteIcon");
        JLabel pathIcon = new JLabel(new ImageIcon(io.getIcon("images/pathIcon.png")));
        pathIcon.setName("pathIcon");

        JLabel cycleIcon = new JLabel(new ImageIcon(io.getIcon("images/cycleIcon.png")));
        cycleIcon.setName("cycleIcon");
        JLabel starIcon = new JLabel(new ImageIcon(io.getIcon("images/starIcon.png")));
        starIcon.setName("starIcon");
        JLabel gridIcon = new JLabel(new ImageIcon(io.getIcon("images/gridIcon.png")));
        gridIcon.setName("gridIcon");

        JLabel treeIcon = new JLabel(new ImageIcon(io.getIcon("images/treeIcon.png")));
        treeIcon.setName("treeIcon");
        JLabel petersenIcon = new JLabel(new ImageIcon(io.getIcon("images/petersenIcon.png")));
        petersenIcon.setName("petersenIcon");
        JLabel backIcon = new JLabel(new ImageIcon(io.getIcon("images/backIcon.png")));
        backIcon.setName("backIcon");

        fullConnectedIcon.addMouseListener(handler);
        biPartiteIcon.addMouseListener(handler);
        pathIcon.addMouseListener(handler);
        cycleIcon.addMouseListener(handler);
        starIcon.addMouseListener(handler);
        gridIcon.addMouseListener(handler);
        treeIcon.addMouseListener(handler);
        petersenIcon.addMouseListener(handler);
        backIcon.addMouseListener(handler);

        JLabel fullConnectedText = new JLabel("Full Connected Graph");
        JLabel biPartiteText = new JLabel("Complete Bipartite Graph");
        JLabel pathText = new JLabel("Path Graph");

        JLabel cycleText = new JLabel("Cycle Graph");
        JLabel startText = new JLabel("Star Graph");
        JLabel gridText = new JLabel("Grid Graph");

        JLabel treeText = new JLabel("Tree Graph");
        JLabel petersenText = new JLabel("Petersen Graph");
        JLabel backText = new JLabel("Back");

        Container cont = new Container();
        GroupLayout layout = new GroupLayout(cont);
        cont.setLayout(layout);

        cont.add(fullConnectedIcon);    cont.add(biPartiteIcon);    cont.add(pathIcon);
        cont.add(cycleIcon);            cont.add(starIcon);         cont.add(gridIcon);
        cont.add(treeIcon);             cont.add(petersenIcon);     cont.add(backIcon);

        cont.add(fullConnectedText);    cont.add(biPartiteText);    cont.add(pathText);
        cont.add(cycleText);            cont.add(startText);        cont.add(gridText);
        cont.add(treeText);             cont.add(petersenText);     cont.add(backText);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);


        layout.setHorizontalGroup(
                layout.createSequentialGroup()

                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(fullConnectedIcon)
                                        .addComponent(fullConnectedText)
                                        .addComponent(cycleIcon)
                                        .addComponent(cycleText)
                                        .addComponent(treeIcon)
                                        .addComponent(treeText)
                        )
                        .addGap(120)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(biPartiteIcon)
                                        .addComponent(biPartiteText)
                                        .addComponent(starIcon)
                                        .addComponent(startText)
                                        .addComponent(petersenIcon)
                                        .addComponent(petersenText)
                        )
                        .addGap(120)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                        .addComponent(pathIcon)
                                        .addComponent(pathText)
                                        .addComponent(gridIcon)
                                        .addComponent(gridText)
                                        .addComponent(backIcon)
                                        .addComponent(backText)
                        )

        );


        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(fullConnectedIcon)
                                        .addComponent(biPartiteIcon)
                                        .addComponent(pathIcon)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(fullConnectedText)
                                        .addComponent(biPartiteText)
                                        .addComponent(pathText)
                        )
                        .addGap(40)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cycleIcon)
                                        .addComponent(starIcon)
                                        .addComponent(gridIcon)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cycleText)
                                        .addComponent(startText)
                                        .addComponent(gridText)
                        )
                        .addGap(40)

                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(treeIcon)
                                        .addComponent(petersenIcon)
                                        .addComponent(backIcon)
                        )
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(treeText)
                                        .addComponent(petersenText)
                                        .addComponent(backText)
                        )




        );

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.add(cont);

        panel.setPreferredSize(new Dimension(width, height));
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);


        return panel;
    }


}
