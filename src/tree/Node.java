package tree;

import enums.Direction;

import java.util.*;

public class Node {
    private int[][] board;
    private int r, c;
    private List<Direction> adj;
    private List<Integer> KURANGI;
    public int depth;
    public Node parent;


    public Node(){
        List<Integer> a = new ArrayList();
        a.add(5);
        a.add(2);
        a.add(8);
        a.add(10);
        a.add(1);
        a.add(11);
        a.add(6);
        a.add(4);
        a.add(7);
        a.add(9);
        a.add(16);
        a.add(3);
        a.add(13);
        a.add(14);
        a.add(15);
        a.add(12);
//        for(int i=1; i<=16; i++){
//            a.add(i);
//        }
//        Collections.shuffle(a);
        this.board = new int[4][4];
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                this.board[i][j] = a.get(i*4 + j);
                if(this.board[i][j]==16){
                    this.r = i;
                    this.c = j;
                }
            }
        }
        this.adj = new ArrayList<>();
        if(this.r != 0){
            this.adj.add(Direction.UP);
        }
        if(this.r != 3){
            this.adj.add(Direction.DOWN);
        }
        if(this.c != 0){
            this.adj.add(Direction.LEFT);
        }
        if(this.c != 3){
            this.adj.add(Direction.RIGHT);
        }
        this.KURANGI = new ArrayList<>();
        for(int i=0; i<17; i++){
            this.KURANGI.add(0);
        }
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                int ref = this.board[i][j];
                int cur = 0;
                for(int k=i; k<4; k++){
                    for(int l=0; l<4; l++){
                        if(k==i && l<=j){
                            continue;
                        }
                        if(this.board[k][l]<ref){
                            cur++;
                        }
                    }
                }
                this.KURANGI.set(ref, cur);
            }
        }
        this.depth = 0;
        this.parent = null;
    }

    public Node(String[][] board){
        this.board = new int[4][4];
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                int ref;
                try{
                    ref = Integer.parseInt(board[i][j]);
                    if(!(1<=ref&&ref<=15)){
                        ref = 16;
                        this.r = i;
                        this.c = j;
                    }
                } catch(NumberFormatException e){
                    this.r = i;
                    this.c = j;
                    ref = 16;
                }
                this.board[i][j] = ref;
            }
        }

        this.KURANGI = new ArrayList<>();
        for(int i=0; i<17; i++){
            this.KURANGI.add(0);
        }
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                int ref = this.board[i][j];
                int cur = 0;
                for(int k=i; k<4; k++){
                    for(int l=0; l<4; l++){
                        if(k==i && l<=j){
                            continue;
                        }
                        if(this.board[k][l]<ref){
                            cur++;
                        }
                    }
                }
                this.KURANGI.set(ref, cur);
            }
        }

        this.adj = new ArrayList<>();
        if(this.r != 0){
            this.adj.add(Direction.UP);
        }
        if(this.r != 3){
            this.adj.add(Direction.DOWN);
        }
        if(this.c != 0){
            this.adj.add(Direction.LEFT);
        }
        if(this.c != 3){
            this.adj.add(Direction.RIGHT);
        }

        this.depth = 0;
        this.parent = null;
    }

    private Node(int[][] board, int r, int c, int depth, Direction d, Node n){
        this.r = r;
        this.c = c;
        this.board = board;
        this.adj = new ArrayList<>();
        if(this.r != 0 && !d.equals(Direction.DOWN)){
            this.adj.add(Direction.UP);
        }
        if(this.r != 3 && !d.equals(Direction.UP)){
            this.adj.add(Direction.DOWN);
        }
        if(this.c != 0 && !d.equals(Direction.RIGHT)){
            this.adj.add(Direction.LEFT);
        }
        if(this.c != 3 && !d.equals(Direction.LEFT)){
            this.adj.add(Direction.RIGHT);
        }
        this.depth = depth;
        this.parent = n;
    }

    public List<Node> getAdj(){
        List<Node> res = new ArrayList<>();
        for(Direction d: this.adj){
            int r_new = this.r + d.dr;
            int c_new = this.c + d.dc;
            int[][] board_new = this.drag(r_new, c_new);
            int depth_new = this.depth + 1;
            res.add(new Node(board_new, r_new, c_new, depth_new, d, this));
        }
        return res;
    }

    public int[][] getBoard(){
        return this.board;
    }

    private int[][] drag(int r_new, int c_new){
        int[][] board_new = this.board.clone();
        for(int i=0; i<4; i++){
            board_new[i] = board_new[i].clone();
        }
        int tmp = board_new[this.r][this.c];
        board_new[this.r][this.c] = board_new[r_new][c_new];
        board_new[r_new][c_new] = tmp;
        return board_new;
    }

    public List<Integer> getKURANGI(){
        return this.KURANGI;
    }

    public int getSumKURANGI(){
        return this.KURANGI.stream().reduce(0, (x, y)->x+y);
    }

    public int getReachableCost(){
        return this.getSumKURANGI()+(this.r+this.c)%2;
    }

    public boolean isReachableGoal(){
        return (this.getReachableCost()%2==0);
    }

    public boolean isGoal(){
        boolean res = true;
        int x = 1;
        for(int i=0; i<4&&res; i++){
            for(int j=0; j<4&&res; j++){
                if(this.board[i][j]!=x){
                    res = false;
                }
                x++;
            }
        }
        return res;
    }

    public int getCost(){
        int cost = this.depth;
        int g = 0;
        int x = 1;
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                if(this.board[i][j]!=x&&x!=16){
                    g++;
                }
                x++;
            }
        }
        return cost+g;
    }

    public Node clone(){
        Node res = new Node();
        res.r = this.r;
        res.c = this.c;
        res.board = this.board.clone();
        for(int i=0; i<4; i++){
            res.board[i] = res.board[i].clone();
        }
        res.KURANGI = new ArrayList<>();
        res.KURANGI.addAll(this.KURANGI);
        res.adj = new ArrayList<>();
        res.adj.addAll(this.adj);
        res.depth = this.depth;
        return res;
    }

    public void print(){
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
