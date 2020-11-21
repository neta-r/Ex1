package ex1.src;


import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, NodeInfo> nodes;
    private HashSet<node_info> set;
    private int numOfEdges, ModeCount;

    public WGraph_DS() {
        this.nodes = new HashMap<>();
        this.set = new HashSet<>();
        this.numOfEdges = 0;
        this.ModeCount = 0;
    }

    public WGraph_DS(WGraph_DS other) {
        this.nodes = new HashMap<>();
        this.set = new HashSet<>();
        this.numOfEdges = other.edgeSize();
        this.ModeCount = other.getMC();
        for (NodeInfo node : other.nodes.values()) {
            NodeInfo temp = new NodeInfo(node); //goes to copy constructor in NodeInfo
            this.nodes.put(node.getKey(), (NodeInfo) temp);
        }
        this.set.addAll(other.set);
    }

    @Override
    public node_info getNode(int key) {
        return this.nodes.get(key);
    }

    @Override
    public boolean hasEdge(int node1, int node2) {
        if ((this.nodes.get(node1) != null) && (this.nodes.get(node2) != null)) { //input check
            return hasNi(this.nodes.get(node1),this.nodes.get(node2));
        }
        return false;
    }

    @Override
    public double getEdge(int node1, int node2) {
        if ((this.nodes.get(node1) == null) || (this.nodes.get(node2) == null)) return -1;
        NodeInfo a = (NodeInfo) getNode(node1);
        NodeInfo b=this.nodes.get(node2);
        if (!hasNi(a,b)) return -1;
        return a.neighbors.get(b);
    }

    @Override
    public void addNode(int key) {
        if (this.nodes.containsKey(key)) return;
        NodeInfo n = new NodeInfo(key);
        this.nodes.put(key, (n));
        this.set.add(n);
        ModeCount++;
    }

    @Override
    public void connect(int node1, int node2, double w) {
        if (node1 == node2) return; //we can't connect the node to itself
        if ((this.nodes.get(node1) == null) || (this.nodes.get(node2) == null)) { //input check
            return;
        }
        if (!hasEdge(node1, node2)) { //checking if they already connected
            NodeInfo a = this.nodes.get(node1);
            NodeInfo b = this.nodes.get(node2);
            a.addNi(b, w);
            this.numOfEdges++;
            ModeCount++;
        }
    }

    @Override
    public Collection<node_info> getV() {
        return this.set;
    }

    @Override
    public Collection<node_info> getV(int node_id) {
        if (this.nodes.get(node_id) != null) return this.nodes.get(node_id).getNi(); //input check
        return null;
    }

    @Override
    public node_info removeNode(int key) {
        NodeInfo a = this.nodes.get(key);
        if (a != null) { //input check
            while (a.getNi().size() != 0) { //checking if the given node have neighbors
             //   node_info help = a.getNi().stream().findFirst().get();
                node_info help=getV(key).stream().findFirst().get();
                this.removeEdge(help.getKey(), a.getKey());
            }
            this.nodes.remove(key);
            this.set.remove(a);
            ModeCount++;
        }
        return a;
    }

    @Override
    public void removeEdge(int node1, int node2) {
        NodeInfo a = this.nodes.get(node1);
        NodeInfo b = this.nodes.get(node2);
        if ((this.nodes.get(node1) != null) && (this.nodes.get(node2) != null)) { //input check
            if (hasNi(nodes.get(node1),nodes.get(node2))) { //checking if there is an edge to remove
                a.removeNode(b);
                this.numOfEdges--;
                ModeCount++;
            }
        }
    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.numOfEdges;
    }

    @Override
    public int getMC() {
        return ModeCount;
    }



    public boolean hasNi(node_info a, node_info b) {
        if (a==null||b==null) return false; //input check
        return ((NodeInfo)a).neighbors.containsKey(b);  //if the two nodes are neighbors- each one will appear in each ni
    }

    @Override
    public boolean equals (Object other){
        if (!(other instanceof WGraph_DS)) {
            return false;
        }
        WGraph_DS c = (WGraph_DS) other;
        for (node_info n : c.getV()) {
            if (!this.nodes.containsKey(n.getKey())) return false; //g has a node this doesn't
            NodeInfo thisNode = this.nodes.get(n.getKey());
            if (!thisNode.equals(n)) return false; //equals of NodeInfo
            //      NodeInfo otherNode = c.nodes.get(n.getKey());
        }
        return true;
    }

    //////////////////////////////////////////////////////////////////////////
    ////////////////////////////////NEW CLASS/////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    private class NodeInfo implements node_info, Comparable<node_info>, Serializable {
        private int key;
        private String info;
        private double tag;
        //private HashMap<Integer, node_info> ni;
        private HashMap<node_info, Double> neighbors;

        private NodeInfo(int key) {
            this.info = "";
            this.tag = Integer.MAX_VALUE;
            this.key = key;
            this.neighbors = new HashMap<>();
        }

        private NodeInfo(NodeInfo other) {
            this.info = other.getInfo();
            this.tag = other.getTag();
            this.key = other.getKey();
            this.neighbors = new HashMap<>();
            for (node_info nei : other.neighbors.keySet()) {
                this.neighbors.put(nei,other.neighbors.get(nei));
            }
        }


        @Override
        public int getKey() {
            return this.key;
        }

        @Override
        public String getInfo() {
            return this.info;
        }

        @Override
        public void setInfo(String s) {
            this.info = s;
        }

        @Override
        public double getTag() {
            return this.tag;
        }

        @Override
        public double setTag(double t) {
            this.tag = t;
            return t;
        }

        public Collection<node_info> getNi() {
            return this.neighbors.keySet();
        }


        public void addNi(NodeInfo t, double w) {
            if (!this.neighbors.containsKey(t)) { //checking if the two nodes are already connected
                this.neighbors.put(t,w); //adding the first to the second's list of neighbors
                t.neighbors.put(this,w);
            }
        }

        public void removeNode(NodeInfo node) {
            if (node == null) return; //input check
            if (!this.neighbors.containsKey(node)) return; //checking if the two nodes even connected
            this.neighbors.remove(node);  //removing the first from the second's list of neighbors
            node.removeNode(this); //removing the second from the first's list of neighbors
        }


        public String toString() {
            return String.valueOf(this.key);
        }

        @Override
        public int compareTo(node_info o) {
            if (this.getTag() > o.getTag()) return 1;
            else if (this.getTag() < o.getTag()) return -1;
            return 0;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            NodeInfo other = (NodeInfo) obj;
            return key == other.getKey();
        }
        @Override
        public int hashCode() {
            return Objects.hashCode(getKey());
        }
    }
}
