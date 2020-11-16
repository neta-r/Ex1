package tests;

import ex1.WGraph_Algo;
import ex1.WGraph_DS;
import ex1.node_info;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_AlgoTest {
    WGraph_DS g1 = new WGraph_DS();
    WGraph_Algo g= new WGraph_Algo();


    void initGraph(WGraph_DS g, int numOfNodes, int numOfEdges, int seed1) {
        Random rnKeys = new Random(seed1);
        Random rnIndexes = new Random(seed1);
        while (g.nodeSize() < numOfNodes) {
            int currKey = rnKeys.nextInt(numOfNodes + 50);
            if (g.getNode(currKey) == null) {
                g.addNode(rnKeys.nextInt(numOfNodes + 50));
            }
        }
        int[] nodes = nodes(g);
        while (g.edgeSize() < numOfEdges) {
            int rnd1 = rnIndexes.nextInt(numOfNodes);
            int rnd2 = rnIndexes.nextInt(numOfNodes);
            int key1 = nodes[rnd1];
            int key2 = nodes[rnd2];
            g.connect(key1, key2, (rnKeys.nextDouble()) * (numOfEdges + 100));
        }
    }


    private static int[] nodes(WGraph_DS g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = nodes[i].getKey();
        }
        Arrays.sort(ans);
        return ans;
    }


    @Test
    void init() {
        initGraph(g1,10,12, 5);
        g.init(g1);
        assertEquals(g1.nodeSize(),10);
        assertEquals(g1.edgeSize(),12);
        Collection<node_info> s= new HashSet<>(g1.nodeSize());
        s.add(g1.getNode(18));
        s.add(g1.getNode(48));
        s.add(g1.getNode(17));
        s.add(g1.getNode(52));
        s.add(g1.getNode(11));
        s.add(g1.getNode(7));
        s.add(g1.getNode(8));
        s.add(g1.getNode(5));
        s.add(g1.getNode(21));
        s.add(g1.getNode(44));
        assertTrue(s.containsAll(g1.getV()));
        assertTrue(g1.getV().containsAll(s)); //all the nodes exist
    }

    @Test
    void getGraph() {
        initGraph(g1,10,12, 5);
        g.init(g1);
        WGraph_DS h= (WGraph_DS) g.getGraph();
        g1.removeNode(17);
        assertNull(h.getNode(17));
        h.addNode(91);
        assertNotNull(g1.getNode(91));
    }

    @Test
    void copy() {
        initGraph(g1, 10, 12, 5);
        g.init(g1);
        WGraph_Algo H= new WGraph_Algo();
        WGraph_DS h =(WGraph_DS) g.copy();
        H.init(h);
        //check if the the nodes has been copied by reference or by value
        assertFalse(g1.getNode(5)==h.getNode(5));
        assertFalse(g1.getNode(17)==h.getNode(17));
        assertFalse(g1.getNode(48)==h.getNode(48));
        // this function is checking deep copy by implementing equals in each class
        assertTrue(H.equals(g));
        H.getGraph().removeNode(17);
        assertNotNull(g1.getNode(17));
        H.getGraph().addNode(2);
        assertNull(g1.getNode(2));
        H.getGraph().removeEdge(18,21);
        assertTrue(g1.hasEdge(18,21));
        H.getGraph().connect(7,52,6);
        assertFalse(g1.hasEdge(7,52));
        g1.removeNode(48);
        assertNotNull(H.getGraph().getNode(48));
        g1.addNode(54);
        assertNull(H.getGraph().getNode(54));
        g1.removeEdge(8,52);
        assertTrue(H.getGraph().hasEdge(8,52));
        g1.connect(21,44,6);
        assertFalse(H.getGraph().hasEdge(21,44));
    }
    @Test
    void isConnected() {
        initGraph(g1, 10, 12, 5);
        g.init(g1);
        assertTrue(g.isConnected());
        //removing a node with one neighbor
        g.getGraph().removeNode(11);
        assertTrue(g.isConnected());
        // removing a edges from the middle
        g.getGraph().removeEdge(44,52);
        g.getGraph().removeEdge(44,5);
        g.getGraph().removeEdge(8,52);
        assertFalse(g.isConnected());
        //connecting the graph again
        g.getGraph().connect(18,8,5);
        g.getGraph().connect(52,17,5);
        assertTrue(g.isConnected());
        //removing all nodes
        g.getGraph().removeNode(48);
        g.getGraph().removeNode(7);
        g.getGraph().removeNode(17);
        g.getGraph().removeNode(5);
        g.getGraph().removeNode(21);
        g.getGraph().removeNode(8);
        g.getGraph().removeNode(52);
        g.getGraph().removeNode(18);
        assertTrue(g.isConnected());
        g.getGraph().removeNode(44);
        assertTrue(g.isConnected());

    }

    @Test
    void shortestPathDist() {
        initGraph(g1, 10, 12, 5);
        g.init(g1);
        assertEquals(g.shortestPathDist(11,7),74.47,0.01);
        g.getGraph().connect(5,21,100);
        assertEquals(g.shortestPathDist(21,5),55.79,0.01);
        g.getGraph().connect(17,11,150);
        assertEquals(g.shortestPathDist(11,17),109.95,0.01);
        g.getGraph().connect(44,18,0);
        g.getGraph().connect(52,18,80);
        assertEquals(g.shortestPathDist(8,18),35.62,0.01);
        g.getGraph().removeNode(44);
        g.getGraph().removeEdge(52,8);
        assertEquals(g.shortestPathDist(48,7),-1.0,0.01); //not connected
        assertEquals(g.shortestPathDist(18,11),-1.0,0.01); //not connected
        assertEquals(g.shortestPathDist(11,11),0,0.01); //no path between a node to itself

    }

    @Test
    void shortestPath() {
        initGraph(g1, 10, 12, 5);
        g.init(g1);
        List<node_info> res= new ArrayList<>();
        res.add(g.getGraph().getNode(11));
        res.add(g.getGraph().getNode(7));
        assertEquals(g.shortestPath(11,7),res);
        res.clear();
        res.add(g.getGraph().getNode(21));
        res.add(g.getGraph().getNode(8));
        res.add(g.getGraph().getNode(5));
        g.getGraph().connect(21,5,100);
        res.clear();
        g.getGraph().connect(17,11,150);
        res.add(g.getGraph().getNode(11));
        res.add(g.getGraph().getNode(7));
        res.add(g.getGraph().getNode(17));
        assertEquals(g.shortestPath(11,17),res);
        res.clear();
        g.getGraph().connect(44,18,0);
        g.getGraph().connect(52,18,80);
        res.add(g.getGraph().getNode(8));
        res.add(g.getGraph().getNode(44));
        res.add(g.getGraph().getNode(18));
        assertEquals(g.shortestPath(8,18),res);
        res.clear();
        g.getGraph().removeNode(44);
        g.getGraph().removeEdge(52,8);
        assertEquals(g.shortestPath(48,7),res); //not connected
        assertEquals(g.shortestPath(18,11),res); //not connected
        assertEquals(g.shortestPath(11,11),res); //no path between a node to itself
    }

    @Test
    void saveAndLoad() {
        initGraph(g1, 10, 12, 5);
        g.init(g1);
        g.save("graph.txt");
        WGraph_Algo k= new WGraph_Algo();
        k.load("graph.txt");
        //this function go over all og the graph and check if the
        //other graph and this graph are equals by:
        //number of nodes, number of edges, list of nodes by:
        //key, list of neighbors by key, and list of edges by key and weight
        assertTrue(g.equals(k));
    }
}