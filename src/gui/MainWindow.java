package gui;

import com.sun.tools.javac.Main;
import tree.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


public class MainWindow extends JFrame {
    private JButton[] tiles;
    private JButton solveButton, randomButton, importButton, createButton;
    private JLabel outStatus, createStatus;
    private JFileChooser inputWindows;
    private JPanel panelOut, panelNav;
    private JTextField pathIn;
    private JTextArea inputText;
    private Graph tree;




    public MainWindow(){
        this.tiles = new JButton[16];
        this.setResizable(false);
        this.setLayout(new GridBagLayout());


        // panelOut and its component
        this.panelOut = new JPanel();
        this.panelOut.setBackground(Color.WHITE);
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

        this.panelNav = new JPanel();
        this.panelNav.setBackground(Color.YELLOW);
        this.panelNav.setLayout(new GridBagLayout());
        this.panelNav.setPreferredSize(new Dimension(300, 700));
        this.panelNav.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        this.solveButton = new JButton("Solve");
        this.solveButton.setPreferredSize(new Dimension(100, 35));
        this.solveButton.setFocusable(false);


        this.randomButton = new JButton("Randomize");
        this.randomButton.setPreferredSize(new Dimension(100, 35));
        this.randomButton.setFocusable(false);


        this.importButton = new JButton("Import");
        this.importButton.setPreferredSize(new Dimension(100, 35));
        this.importButton.setFocusable(false);


        this.createButton = new JButton("Create");
        this.createButton.setPreferredSize(new Dimension(100, 35));
        this.createButton.setFocusable(false);



        this.pathIn = new JTextField();
        this.pathIn.setPreferredSize(new Dimension(200, 35));

        this.inputText = new JTextArea();
        this.inputText.setPreferredSize(new Dimension(250, 300));

        this.createStatus = new JLabel();
        this.outStatus = new JLabel();


        GridBagConstraints navConstraint = new GridBagConstraints();
        navConstraint.insets = new Insets(5, 5, 5, 5);

        navConstraint.gridx = 0;
        navConstraint.gridy = 0;
        navConstraint.anchor = GridBagConstraints.LINE_START;
        this.panelNav.add(importButton, navConstraint);

        navConstraint.gridx = 1;
        navConstraint.gridy = 0;
        navConstraint.anchor = GridBagConstraints.LINE_END;
        this.panelNav.add(pathIn, navConstraint);

        navConstraint.gridx = 0;
        navConstraint.gridy = 1;
        navConstraint.gridwidth = 2;
        navConstraint.fill = GridBagConstraints.HORIZONTAL;
        this.panelNav.add(inputText, navConstraint);

        navConstraint.gridx = 0;
        navConstraint.gridy = 2;
        navConstraint.gridwidth = 1;
        navConstraint.fill = GridBagConstraints.NONE;
        navConstraint.anchor = GridBagConstraints.LINE_START;
        this.panelNav.add(createStatus, navConstraint);

        navConstraint.gridx = 1;
        navConstraint.gridy = 2;
        navConstraint.anchor = GridBagConstraints.LINE_END;
        this.panelNav.add(createButton, navConstraint);



        navConstraint.gridx = 0;
        navConstraint.gridy = 3;
        navConstraint.anchor = GridBagConstraints.LINE_START;
        this.panelNav.add(solveButton, navConstraint);

        navConstraint.gridx = 1;
        navConstraint.gridy = 3;
        navConstraint.anchor = GridBagConstraints.LINE_END;
        this.panelNav.add(randomButton, navConstraint);


        navConstraint.gridx = 0;
        navConstraint.gridy = 4;
        navConstraint.fill = GridBagConstraints.HORIZONTAL;
        navConstraint.gridwidth = 2;
        this.panelNav.add(outStatus, navConstraint);


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



        this.randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tree = new Graph();
                MainWindow.this.drawBoard(tree.getRoot());
            }
        });

        this.solveButton.addActionListener(new ActionListener() {
            public int idx;
            @Override
            public void actionPerformed(ActionEvent e) {
                idx = 0;
                currentTree();
                System.out.println(Arrays.deepToString(tree.getRoot().getBoard()));
                if(tree.getSolutionPath().size()==0){
                    tree.process();
                }
                new Timer(500, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(idx >= tree.getSolutionPath().size()){
                            ((Timer)e.getSource()).stop();
                            return;
                        }
                        MainWindow.this.drawBoard(tree.getSolutionPath().get(idx));
                        idx++;
                    }
                }).start();
            }
        });
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
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        });

        this.createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    parseInput();
                    drawBoard(tree.getRoot());
                } catch(BadLocationException e1){
                    
                } catch (IndexOutOfBoundsException e1){
                    
                }
            }
        });

    }

    public void drawBoard(Node n){
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
        String[][] raw = new String[4][4];
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                raw[i][j] = tiles[i*4+j].getText();
            }
        }
        if(tree!=null&&Arrays.deepToString(tree.getRoot().getBoard()).equals(Arrays.deepToString(raw))){
            return;
        }
        tree = new Graph(raw);
    }

    public void parseInput() throws BadLocationException {
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


}
