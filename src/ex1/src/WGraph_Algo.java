package ex1.src;

import java.io.*;
import java.util.*;


public class WGraph_Algo implements weighted_graph_algorithms, Serializable {
    WGraph_DS graph;

    @Override
    public void init(weighted_graph g) {
        this.graph = (WGraph_DS) g;
    }

    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    @Override
    public weighted_graph copy() {
        WGraph_DS newGraph = new WGraph_DS((WGraph_DS) this.getGraph()); //goes to copy constructor of WGraph_DS
        return newGraph;
    }

    @Override
    public boolean isConnected() {
        if ((this.graph.nodeSize() == 0) || (this.graph.nodeSize() == 1))
            return true; //if the graph has no nodes or only one node it's connected
        Iterator<node_info> it = graph.getV().iterator();
        node_info n = it.next();
        return bfs(n, graph);
    }

    private boolean bfs(node_info start, WGraph_DS g) {
        reSet(g); //deleting all the data left from previous running
        int numOfNodes = 1; //we have start node
        Queue<node_info> queue = new LinkedList<>();
        queue.add(start);
        start.setTag(1); //visited
        while (!queue.isEmpty()) {
            start = queue.poll();
            for (node_info nei : g.getV(start.getKey())) {
                if (nei.getTag() == 0) {
                    nei.setTag(1);
                    queue.add(nei);
                    nei.setInfo(start.getInfo() + "," + nei.getKey());
                    numOfNodes++;
                }
            }
        }
        return (g.nodeSize() == numOfNodes);
    }

    private void reSet(WGraph_DS g) {
        for (node_info a : g.getV()) {
            a.setTag(0);
            a.setInfo(String.valueOf(a.getKey()));
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        if (src == dest) {
            return 0;
        }
        Dijkstra a = new Dijkstra(graph, graph.getNode(src), graph.getNode(dest)); //creating a new variable of Dijkstra type
        return a.getDist();
    }

    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (src == dest) {
            List<node_info> a = new ArrayList<>();
            return a;

        }
        Dijkstra a = new Dijkstra(graph, graph.getNode(src), graph.getNode(dest)); //creating a new variable of Dijkstra type
        return a.getPath();
    }

    @Override
    public boolean save(String file) {
        try {
            FileOutputStream myFile = new FileOutputStream(file);
            ObjectOutputStream grp = new ObjectOutputStream(myFile);
            grp.writeObject(graph);
            grp.close();
            myFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean load(String file) {
        try {
            FileInputStream myFile = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(myFile);
            this.graph = (WGraph_DS) ois.readObject();
            ois.close();
            myFile.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean equals (Object other){
        if (!(other instanceof WGraph_Algo)) {
            return false;
        }
        WGraph_Algo O= (WGraph_Algo)other;
        if (this.graph.nodeSize() != O.graph.nodeSize()) return false;
        if (this.graph.edgeSize() !=  O.graph.edgeSize()) return false;
        if (graph.equals(O.getGraph())) {
            return true;
        }
        return false;
    }


    private class Dijkstra {
        private int dist; //counters of the number of node that connected to the given node and the total distance
        WGraph_DS g;
        private String path; //a string representing the path between scr and dest
        node_info start, end; //scr and dest

        //constructor - used only for the shortest Path functions
        public Dijkstra(WGraph_DS g, node_info start, node_info end) {
            this.g = g;
            path = "";
            dist = 0;
            this.start = start;
            this.end = end;
        }


        //resetting all variables from previous runs
        public void reSet() {
            for (node_info a : g.getV()) {
                a.setTag(Integer.MAX_VALUE);
                a.setInfo(String.valueOf(a.getKey()));
            }
        }

        //tag=distance
        private void dijkstra(node_info start) {

            reSet(); //deleting all the data left from previous running
            start.setTag(0);
            start.setInfo(String.valueOf(start.getKey()));
            PriorityQueue<node_info> queue = new PriorityQueue<>(g.getV());
            //the distance between node start to himself is o
            while (!queue.isEmpty()) {
                start = queue.poll();
                for (node_info nei : g.getV(start.getKey())) {
                    if (nei.getInfo().charAt(0) != 'T') { //was not visited
                        double t = start.getTag() + g.getEdge(start.getKey(), nei.getKey());
                        if (nei.getTag() > t) {
                            nei.setTag(t);
                            nei.setInfo(start.getInfo() + "," + nei.getKey());
                            //decrease Key
                            queue.remove(nei);
                            queue.add(nei);
                        }
                    }
                }
                start.setInfo("T" + start.getInfo()); //visited
            }
            for (node_info a : g.getV()) {
                if (a.getTag() == Integer.MAX_VALUE) {
                    a.setTag(-1);
                }
            }
        }

        //this function is cutting the string from the bfs to relevant string- the one that ends in the given end node
        //assuming there is a path
        private void cut() {
            int index = path.indexOf(String.valueOf(end.getKey()));
            path = path.substring(0, index - 1);
            path = path + "," + end.getKey();

        }

        //this function is converting the path string into a list of nodes
        //assuming there is a path
        private List<node_info> stringToList() {
            List<node_info> list = new ArrayList<>();
            String[] sArray = path.split(",");
            dist = sArray.length;
            for (String s : sArray) { //resetting the tags for the not visited nodes
                int key;
                key = Integer.parseInt(s);
                list.add(g.getNode(key));
            }
            return list;
        }

        //this function is calling all relevant function in order to get the distance between 2 given nodes
        public double getDist() {
            dijkstra(this.start);
            return end.getTag();
        }

        //this function is calling all relevant function in order to get the path between two given nodes
        public List<node_info> getPath() {
            dijkstra(this.start);
            if (end.getTag() == -1) {
                return new ArrayList<>(); //there is no path between the two given nodes so we return an empty list
            }
            path = end.getInfo().substring(1);
            cut();
            return stringToList();
        }
    }
}

