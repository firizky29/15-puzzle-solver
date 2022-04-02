package gui;

import tree.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class MainWindow extends JFrame {
    protected JButton[] tiles;
    protected JButton solveButton, randomButton, importButton, createButton, analysisButton, resetButton;
    protected JLabel outStatus, createStatus, input, output;
    protected JFileChooser inputWindows;
    protected JPanel panelOut, panelNav;
    protected JTextField pathIn;
    protected JTextArea inputText;
    protected Graph tree;


    private void createButton(JButton button){
        button.setPreferredSize(new Dimension(100, 35));
        button.setFocusable(false);
        button.setBackground(Color.decode("#4DB4D7"));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Gill Sans", Font.PLAIN, 12));
    }



    public MainWindow(){
        // GUI Main Window
        this.tiles = new JButton[16];
        this.setResizable(false);
        this.setLayout(new GridBagLayout());


        // panelOut and its component
        this.panelOut = new JPanel();
        this.panelOut.setBackground(Color.decode("#fafafa"));
        this.panelOut.setLayout(new GridBagLayout());
        this.panelOut.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints outConstraint = new GridBagConstraints();
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                if(i!=3||j!=3){
                    this.tiles[i*4+j] = new JButton(Integer.toString(i*4+j+1));
                }
                else{
                    this.tiles[i*4+j] = new JButton("KOSONG");
                    this.tiles[i*4+j].setVisible(false);
                }
                this.tiles[i*4+j].setFont(new Font("Roboto", 0, 25));
                this.tiles[i*4+j].setBackground(Color.lightGray);
                this.tiles[i*4+j].setFocusable(false);
                outConstraint.gridy = i;
                outConstraint.gridx = j;
                outConstraint.weightx = 1;
                outConstraint.weighty = 1;
                outConstraint.insets = new Insets(5, 5, 5, 5);
                outConstraint.fill = GridBagConstraints.BOTH;
                this.panelOut.add(this.tiles[i*4+j], outConstraint);
            }
        }

        // panelNav and its component
        this.panelNav = new JPanel();
        this.panelNav.setBackground(Color.decode("#fafafa"));
        this.panelNav.setLayout(new GridBagLayout());
        this.panelNav.setPreferredSize(new Dimension(300, 700));
        this.panelNav.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // component initialization
        // button
        this.solveButton = new JButton("Solve");
        createButton(solveButton);

        this.randomButton = new JButton("Randomize");
        createButton(randomButton);

        this.importButton = new JButton("Import");
        createButton(importButton);

        this.createButton = new JButton("Create");
        createButton(createButton);

        this.analysisButton = new JButton("See Analysis");
        createButton(analysisButton);
        this.analysisButton.setPreferredSize(new Dimension(160, 35));
        this.analysisButton.setVisible(false);

        this.resetButton = new JButton("Reset");
        createButton(resetButton);
        this.resetButton.setVisible(false);


        // text
        this.pathIn = new JTextField();
        this.pathIn.setPreferredSize(new Dimension(200, 35));
        this.pathIn.setFont(new Font("Gill Sans", Font.PLAIN, 16));

        this.inputText = new JTextArea();
        this.inputText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(1,1,1,1)));
        this.inputText.setPreferredSize(new Dimension(250, 300));
        this.inputText.setFont(new Font("Gill Sans", Font.PLAIN, 30));

        // label
        this.createStatus = new JLabel();
        this.createStatus.setForeground(Color.RED);
        this.outStatus = new JLabel();

        this.input = new JLabel("Input: ");
        input.setFont(new Font("Gill Sans", Font.BOLD, 14));

        this.output = new JLabel("Output: ");
        output.setFont(new Font("Gill Sans", Font.BOLD, 14));


        // Component placement

        GridBagConstraints navConstraint = new GridBagConstraints();
        navConstraint.insets = new Insets(5, 5, 5, 5);

        navConstraint.gridx = 0;
        navConstraint.gridy = 0;
        navConstraint.gridwidth = 2;
        navConstraint.anchor = GridBagConstraints.LINE_START;
        this.panelNav.add(input, navConstraint);

        navConstraint.gridx = 0;
        navConstraint.gridy = 1;
        navConstraint.gridwidth = 1;
        this.panelNav.add(importButton, navConstraint);

        navConstraint.gridx = 1;
        navConstraint.gridy = 1;
        navConstraint.gridwidth = 2;
        navConstraint.anchor = GridBagConstraints.LINE_END;
        this.panelNav.add(pathIn, navConstraint);

        navConstraint.gridx = 0;
        navConstraint.gridy = 2;
        navConstraint.gridwidth = 3;
        navConstraint.fill = GridBagConstraints.HORIZONTAL;
        this.panelNav.add(inputText, navConstraint);

        navConstraint.gridx = 0;
        navConstraint.gridy = 3;
        navConstraint.gridwidth = 1;
        navConstraint.fill = GridBagConstraints.NONE;
        navConstraint.anchor = GridBagConstraints.LINE_START;
        this.panelNav.add(createStatus, navConstraint);

        navConstraint.gridx = 2;
        navConstraint.gridy = 3;
        navConstraint.anchor = GridBagConstraints.LINE_END;
        this.panelNav.add(createButton, navConstraint);


        navConstraint.gridx = 0;
        navConstraint.gridy = 4;
        navConstraint.anchor = GridBagConstraints.LINE_START;
        this.panelNav.add(randomButton, navConstraint);

        navConstraint.gridx = 2;
        navConstraint.gridy = 4;
        navConstraint.anchor = GridBagConstraints.LINE_END;
        this.panelNav.add(solveButton, navConstraint);

        navConstraint.gridx = 1;
        navConstraint.gridy = 4;
        navConstraint.anchor = GridBagConstraints.CENTER;
        navConstraint.gridheight = 1;
        this.panelNav.add(resetButton, navConstraint);

        navConstraint.gridx = 0;
        navConstraint.gridy = 5;
        navConstraint.fill = GridBagConstraints.HORIZONTAL;
        navConstraint.gridwidth = 3;
        this.panelNav.add(output, navConstraint);


        navConstraint.gridy = 6;
        navConstraint.fill = GridBagConstraints.HORIZONTAL;
        navConstraint.gridheight = 2;
        this.panelNav.add(outStatus, navConstraint);

        navConstraint.gridy = 8;
        navConstraint.gridwidth = 2;
        this.panelNav.add(analysisButton, navConstraint);


        // Panel Placement
        GridBagConstraints containerConstraint = new GridBagConstraints();
        containerConstraint.gridx = 1;
        containerConstraint.gridy = 0;
        containerConstraint.weightx = 7;
        containerConstraint.weighty = 1;
        containerConstraint.fill = GridBagConstraints.BOTH;
        this.add(panelOut, containerConstraint);

        containerConstraint.gridx = 0;
        containerConstraint.weightx = 3;
        this.add(panelNav, containerConstraint);

        this.setSize(1000, 700);
        this.setTitle("15 Puzzle Solver");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Button Action
        // random button: to generate a matrix with random manner
        this.randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                tree = new Graph(true);
                MainWindow.this.drawBoard(tree.getRoot());
            }
        });

        // solve button: to solve current matrix
        this.solveButton.addActionListener(new ActionListener() {
            public int idx;
            @Override
            public void actionPerformed(ActionEvent e) {
                idx = 0;
                reset();
                currentTree();
                System.out.println(Arrays.deepToString(tree.getRoot().getBoard()));
                if(tree.getSolutionPath().size()==0){
                    tree.process();
                }
                new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(idx >= tree.getSolutionPath().size()){
                            outStatus.setText(tree.status);
                            analysisButton.setVisible(true);
                            resetButton.setVisible(true);
                            ((Timer)e.getSource()).stop();
                            return;
                        }
                        MainWindow.this.drawBoard(tree.getSolutionPath().get(idx));
                        idx++;
                    }
                }).start();
            }
        });

        // import button: to read input from file text
        this.importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputWindows = new JFileChooser();
                inputWindows.setFileFilter(new FileNameExtensionFilter("Text File", "txt", "text"));
                inputWindows.setCurrentDirectory(new File("."));
                inputWindows.showOpenDialog(null);
                if(inputWindows.getSelectedFile()!=null) {
                    pathIn.setText(inputWindows.getSelectedFile().getAbsolutePath());
                    File file = new File(pathIn.getText());
                    inputText.setText("");
                    try{
                        Scanner inp = new Scanner(file);
                        while(inp.hasNextLine()){
                            inputText.append(inp.nextLine() + "\n");
                        }
                    } catch (FileNotFoundException fileNotFoundException) {
                        createStatus.setText("File not found!");
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        });

        // create button: to read input from current text box to actual boards
        this.createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                try{
                    parseInput();
                    drawBoard(tree.getRoot());
                } catch(BadLocationException | IndexOutOfBoundsException e1){
                    createStatus.setText("Input format is invalid!");
                }
            }
        });


        // analysis button: to open up analysis window
        this.analysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AnalysisWindow analysisWindow = new AnalysisWindow(tree);
                analysisWindow.setVisible(true);
            }
        });

        // reset button: to reset solved state to its root
        this.resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
                if(tree == null){
                    tree = new Graph(false);
                }
                drawBoard(tree.getRoot());
            }
        });

    }

    public void drawBoard(Node n){
        // draw current GUI board with state n
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                if(n.getBoard(i, j)==16){
                    this.tiles[i*4+j].setText("KOSONG");
                    this.tiles[i*4+j].setVisible(false);
                }
                else{
                    this.tiles[i*4+j].setText(String.valueOf(n.getBoard(i, j)));
                    this.tiles[i*4+j].setVisible(true);
                }
            }
        }
        this.panelOut.repaint();
        this.panelOut.revalidate();
    }

    public void currentTree(){
        // converting GUI board into node object
        String[][] raw = new String[4][4];
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                raw[i][j] = tiles[i*4+j].getText();
                if(raw[i][j]=="KOSONG"){
                    raw[i][j] = "16";
                }
            }
        }
        if(tree!=null&&Arrays.deepToString(tree.getRoot().getBoard()).equals(Arrays.deepToString(raw))){
            return;
        }
        tree = new Graph(raw);
    }

    public void parseInput() throws BadLocationException {
        // reading input and place it into a text box to soon be processed
        int lineCount = inputText.getLineCount();
        if(lineCount<4){
            throw new ArrayIndexOutOfBoundsException();
        }
        else{
            String[][] raw_inp = new String[4][4];
            String[] lines = new String[4];
            for(int i=0; i<4; i++){
                int offset = inputText.getLineStartOffset(i);
                int offset2 = inputText.getLineEndOffset(i);
                lines[i] = inputText.getText().substring(offset, offset2);
                while(lines[i].length() > 1){
                    char c = lines[i].charAt(lines[i].length() - 1);
                    if (!(c == ' '|| c == '\n')) break;
                    lines[i] = lines[i].substring(0, lines[i].length()-1);
                }
                if(lines[i].split(" ").length!=4){
                    throw new ArrayIndexOutOfBoundsException();
                }
                else{
                    String[] raw_line = lines[i].split(" ");
                    raw_inp[i] = raw_line;
                }
            }
            this.tree = new Graph(raw_inp);
        }
    }


    public void reset(){
        // revert all component that is just temporary
        analysisButton.setVisible(false);
        resetButton.setVisible(false);
        outStatus.setText("");
        createStatus.setText("");
    }



}
