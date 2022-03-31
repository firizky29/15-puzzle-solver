package tree;

import java.util.*;

public class Graph {
    private Node root;
    private List<Node> solutionPath;
    public int cntNode;

    public Graph(){
        this.root = new Node();
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
        HashMap<Node, Node> parent = new HashMap<>();
        HashMap<Integer, Boolean> visited = new HashMap<>();
        Node to = null;
        pq.add(root);
        parent.put(root, root);
        visited.put(root.hash(), Boolean.TRUE);
        while(!pq.isEmpty()){
            if(this.cntNode>100) {
                break;
            }
            Node p = pq.remove();
            p.print();
            System.out.println();
            if(p.isGoal()){
                to = p;
                break;
            }
            for(Node u: p.getAdj()){
                if(visited.containsKey(u.hash())){
                    continue;
                }
                this.cntNode++;
                parent.put(u, p);
                visited.put(u.hash(), Boolean.TRUE);
                pq.add(u);
            }
        }
        for(Node u = to; u!=parent.get(u); u=parent.get(u)){
            this.solutionPath.add(u);
        }
        this.solutionPath.add(root);
        Collections.reverse(this.solutionPath);
    }

    public void print(){
        for(int i=0; i<this.solutionPath.size(); i++){
            this.solutionPath.get(i).print();
            System.out.println();
        }
    }

}
