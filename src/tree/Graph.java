package tree;

import java.util.*;

public class Graph {
    private Node root; // root state of the graph
    private List<Node> solutionPath; // solution path
    private int cntNode; // number of nodes generated
    public String status; // status of processed graph
    private List<Integer> KURANGI; // KURANGI List

    public Graph(boolean randomize){
        // Random Graph Constructor
        this.root = new Node(randomize);
        this.solutionPath = new ArrayList<>();
        this.cntNode = 1;
        this.KURANGI = new ArrayList<>();
        for(int i=0; i<17; i++){
            this.KURANGI.add(0);
        }
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                int ref = root.getBoard(i, j);
                int cur = 0;
                for(int k=i; k<4; k++){
                    for(int l=0; l<4; l++){
                        if(k==i && l<=j){
                            continue;
                        }
                        if(root.getBoard(k, l)<ref){
                            cur++;
                        }
                    }
                }
                this.KURANGI.set(ref, cur);
            }
        }
    }

    public Graph(String[][] root_raw){
        // Input-based graph constructor
        this.root = new Node(root_raw);
        this.solutionPath = new ArrayList<>();
        this.cntNode = 1;
        this.KURANGI = new ArrayList<>();
        for(int i=0; i<17; i++){
            this.KURANGI.add(0);
        }
        for(int i=0; i<4; i++){
            for(int j=0; j<4; j++){
                int ref = root.getBoard(i, j);
                int cur = 0;
                for(int k=i; k<4; k++){
                    for(int l=0; l<4; l++){
                        if(k==i && l<=j){
                            continue;
                        }
                        if(root.getBoard(k, l)<ref){
                            cur++;
                        }
                    }
                }
                this.KURANGI.set(ref, cur);
            }
        }
    }

    public void process(){
        // B&B process
        long time = System.nanoTime();
        if(!isReachableGoal()){
            this.status = "Goal is not reachable";
            return;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        HashMap<String, Boolean> visited = new HashMap<>();
        Node to = null;
        pq.add(root);
        visited.put(Arrays.deepToString(root.getBoard()), Boolean.TRUE);
        while(!pq.isEmpty()){
            Node p = pq.remove();
            if(p.isGoal()){
                to = p;
                break;
            }
            for(Node u: p.getAdj()){
                if(visited.containsKey(Arrays.deepToString(u.getBoard()))){
                    continue;
                }
                this.cntNode++;
                visited.put(Arrays.deepToString(u.getBoard()), Boolean.TRUE);
                pq.add(u);
            }
        }
        for(Node u = to; u.parent!=null; u=u.parent){
            this.solutionPath.add(0, u);
        }
        this.solutionPath.add(0, root);

        Double elapsed = (System.nanoTime() - time) / 1e9;
        this.status = String.format("<html>Goal is Reachable!<br/>Time elapsed: %.4f s<br/>Number of moves: %d moves<br/>Generated nodes: %d nodes</html>", elapsed, solutionPath.size()-1, cntNode);
    }


    // getter
    public List<Node> getSolutionPath() {
        return this.solutionPath;
    }

    public List<Integer> getKURANGI(){
        return this.KURANGI;
    }
    public int getSumKURANGI(){
        // sum
        return this.KURANGI.stream().reduce(0, (x, y)->x+y);
    }

    public int getReachableCost(){
        // sum + x
        return this.getSumKURANGI()+(root.getR()+root.getC())%2;
    }

    public Object[][] getRowContent(){
        // content of KURANGI list
        Object[][] data = new Object[18][2];
        for(int i=1; i<=16; i++){
            data[i-1][0] = Integer.toString(i);
            data[i-1][1] = this.KURANGI.get(i);
        }
        data[16][0] = "Sum";
        data[16][1] = getSumKURANGI();
        data[17][0] = "Sum + X";
        data[17][1] = getReachableCost();
        return data;
    }

    public Node getRoot(){
        return this.root;
    }

    // predicate
    private boolean isReachableGoal(){
        return (this.getReachableCost()%2==0);
    }

    // helper
    public void print(){
        for(int i=0; i<this.solutionPath.size(); i++){
            this.solutionPath.get(i).print();
            System.out.println();
        }
    }


}
