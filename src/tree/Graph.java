package tree;

import java.util.*;

public class Graph {
    private Node root;
    private List<Node> solutionPath;
    private int cntNode;

    public Graph(){
        this.root = new Node();
        this.solutionPath = new ArrayList<>();
        this.cntNode = 1;
    }

    public Graph(String[][] root_raw){
        this.root = new Node(root_raw);
        this.solutionPath = new ArrayList<>();
        this.cntNode = 1;
    }

    public List<Node> getSolutionPath() {
        return solutionPath;
    }

    public void process(){
        if(!root.isReachableGoal()){
            return;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getCost));
        //HashMap<Node, Node> parent = new HashMap<>();
        HashMap<String, Boolean> visited = new HashMap<>();
        Node to = null;
        pq.add(root);
        //parent.put(root, root);
        visited.put(Arrays.deepToString(root.getBoard()), Boolean.TRUE);
        while(!pq.isEmpty()){
            Node p = pq.remove();
            //p.print();
            //System.out.println(p.depth);
            if(p.isGoal()){
                to = p;
                break;
            }
            for(Node u: p.getAdj()){
                if(visited.containsKey(Arrays.deepToString(u.getBoard()))){
                    continue;
                }
                this.cntNode++;
                //System.out.println(this.cntNode);
                //parent.put(u, p);
                visited.put(Arrays.deepToString(u.getBoard()), Boolean.TRUE);
                pq.add(u);
            }
        }
        for(Node u = to; u.parent!=null; u=u.parent){
            this.solutionPath.add(0, u);
        }
        this.solutionPath.add(0, root);
    }

    public void print(){
        for(int i=0; i<this.solutionPath.size(); i++){
            this.solutionPath.get(i).print();
            System.out.println();
        }
    }

    public int getCntNode(){
        return this.cntNode;
    }

    public Node getRoot(){
        return this.root;
    }

}
