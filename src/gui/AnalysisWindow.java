package gui;


import tree.Graph;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class AnalysisWindow extends JFrame {
    protected JPanel tablePanel, rootPanel, tablePane;
    protected JButton exportButton;
    protected JFileChooser exportWindows;
    protected JLabel[] tiles;
    protected JLabel root, table, status;
    protected JTable analysisTable;
    protected Graph g;


    private void createButton(JButton button){
        button.setPreferredSize(new Dimension(100, 35));
        button.setFocusable(false);
        button.setBackground(Color.decode("#4DB4D7"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Gill Sans", Font.PLAIN, 12));
    }


    public AnalysisWindow(Graph g){
        // it is a windows showing heuristics analysis after the tree has been processed
        this.tiles = new JLabel[16];
        this.setResizable(false);
        this.setLayout(new GridBagLayout());
        this.g = g;

        // root panel and its component
        this.rootPanel = new JPanel();
        this.rootPanel.setBackground(Color.decode("#fafafa"));
        this.rootPanel.setLayout(new GridBagLayout());
        this.rootPanel.setPreferredSize(new Dimension(350, 390));
        this.rootPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        this.root = new JLabel("Initial Position: ");
        this.root.setPreferredSize(new Dimension(350, 35));


        GridBagConstraints rootConstraint = new GridBagConstraints();
        rootConstraint.insets = new Insets(5, 5, 5, 5);

        rootConstraint.gridx = 0;
        rootConstraint.gridy = 0;
        rootConstraint.gridwidth = 4;
        rootConstraint.anchor = GridBagConstraints.LINE_START;
        this.rootPanel.add(root, rootConstraint);
        rootConstraint.gridwidth = 1;

        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                this.tiles[i*4+j] = new JLabel();
                this.tiles[i*4+j].setOpaque(true);
                if(g.getRoot().getBoard(i, j)==16){
                    this.tiles[i*4+j].setText("KOSONG");
                    this.tiles[i*4+j].setVisible(false);
                }
                else{
                    this.tiles[i*4+j].setText(String.valueOf(g.getRoot().getBoard(i, j)));
                    this.tiles[i*4+j].setVisible(true);
                }
                this.tiles[i*4+j].setFont(new Font("Roboto", 0, 25));
                this.tiles[i*4+j].setBackground(Color.lightGray);
                this.tiles[i*4+j].setHorizontalAlignment(SwingConstants.CENTER);
                this.tiles[i*4+j].setVerticalAlignment(SwingConstants.CENTER);
                this.tiles[i*4+j].setFocusable(false);
                rootConstraint.anchor = GridBagConstraints.CENTER;
                rootConstraint.gridy = i+1;
                rootConstraint.gridx = j;
                rootConstraint.weightx = 1;
                rootConstraint.weighty = 1;
                rootConstraint.fill = GridBagConstraints.BOTH;
                this.rootPanel.add(this.tiles[i*4+j], rootConstraint);
            }
        }

        // table panel and its component
        this.tablePanel = new JPanel();
        this.tablePanel.setBackground(Color.decode("#fafafa"));
        this.tablePanel.setLayout(new GridBagLayout());
        this.tablePanel.setPreferredSize(new Dimension(200, 600));

        this.table = new JLabel("Reachable Analysis: ");
        this.table.setPreferredSize(new Dimension(150, 35));

        this.status = new JLabel(g.status);
        this.status.setPreferredSize(new Dimension(150, 35));

        this.exportButton = new JButton("Export");
        createButton(exportButton);


        // tablepane and its component
        this.tablePane = new JPanel();
        this.tablePane.setLayout(new BorderLayout());
        this.tablePane.setPreferredSize(new Dimension(200, 320));

        String[] column = {"i", "KURANGI[i]"};
        this.analysisTable = new JTable(g.getRowContent(), column);

        this.tablePane.add(analysisTable, BorderLayout.CENTER);
        this.tablePane.add(analysisTable.getTableHeader(), BorderLayout.NORTH);



        GridBagConstraints tableConstraint = new GridBagConstraints();

        tableConstraint.gridx = 0;
        tableConstraint.gridy = 0;
        tableConstraint.fill = GridBagConstraints.BOTH;

        this.tablePanel.add(table, tableConstraint);

        tableConstraint.gridy = 1;
        tableConstraint.ipady = 20;
        this.tablePanel.add(tablePane, tableConstraint);

        tableConstraint.gridy = 2;
        tableConstraint.ipady = 30;
        this.tablePanel.add(status, tableConstraint);

        tableConstraint.gridy = 4;
        tableConstraint.weighty = 0.5;   //request any extra vertical space
        tableConstraint.fill = GridBagConstraints.HORIZONTAL;
        tableConstraint.ipady = 0;       //reset to default
        tableConstraint.anchor = GridBagConstraints.CENTER;
        tableConstraint.insets = new Insets(10,0,0,0);
        this.tablePanel.add(exportButton, tableConstraint);



        // panel placement
        GridBagConstraints containerConstraint = new GridBagConstraints();
        containerConstraint.gridx = 0;
        containerConstraint.gridy = 0;
        containerConstraint.weightx = 7;
        containerConstraint.weighty = 1;
        containerConstraint.fill = GridBagConstraints.BOTH;
        this.add(rootPanel, containerConstraint);

        containerConstraint.gridx = 1;
        containerConstraint.weightx = 3;
        this.add(tablePanel, containerConstraint);

        this.setSize(800, 600);
        this.setTitle("Analysis");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // button action

        // export button: to write output into text file
        this.exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportWindows = new JFileChooser();
                exportWindows.setApproveButtonText("Export");
                exportWindows.setCurrentDirectory(new File("."));
                exportWindows.showOpenDialog(null);
                if(exportWindows.getSelectedFile()!=null) {
                    String path = exportWindows.getSelectedFile().getAbsolutePath();
                    exportOutput(path);
                }
            }
        });

    }

    public void exportOutput(String path){
        // writing output
        try {
            FileWriter fstream = new FileWriter(path);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write("INITIAL POSITION:");
            out.newLine();
            for(int i=0; i<4; i++){
                for(int j=0; j<4; j++){
                    out.write(g.getRoot().getBoard(i, j) +" ");
                }
                out.newLine();
            }
            out.newLine();

            out.write("KURANGI TABLE: ");
            out.newLine();
            for(int i=1; i<=16; i++){
                out.write("KURANGI("+i+") = " + g.getKURANGI().get(i));
                out.newLine();
            }

            out.write("SUM = " + g.getSumKURANGI());
            out.newLine();
            out.write("SUM + X = " + g.getReachableCost());
            out.newLine();
            out.newLine();


            out.write("SOLUTION PATH:");
            out.newLine();
            for(int i=0; i<g.getSolutionPath().size(); i++){
                out.write("MOVE: "+i);
                out.newLine();
                out.write("DIRECTION: "+g.getSolutionPath().get(i).direction);
                out.newLine();
                for(int j=0; j<4; j++){
                    for(int k=0; k<4; k++){
                        out.write(g.getSolutionPath().get(i).getBoard(j, k) +" ");
                    }
                    out.newLine();
                }
                out.newLine();
                out.newLine();
            }
            if(g.getSolutionPath().size()==0){
                out.write("No Solution Path Exist");
                out.newLine();
            }
            out.newLine();

            out.write("STATUS: ");
            out.newLine();
            String s = g.status;
            s = s.replaceAll("<html>", "");
            s = s.replaceAll("</html>", "");
            s = s.replaceAll("<br/>", "\n");
            out.write(s);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
