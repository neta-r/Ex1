package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WGraph_DSTest {
    WGraph_DS g1= new WGraph_DS();


    void initGraph (WGraph_DS g,int numOfNodes, int numOfEdges, int seed1){
        Random rnKeys= new Random(seed1);
        Random rnIndexes= new Random(seed1);
        while (g.nodeSize()<numOfNodes){
            int currKey=rnKeys.nextInt(numOfNodes+50);
            if (g.getNode(currKey)==null) {
                g.addNode(rnKeys.nextInt(numOfNodes+50));
            }
        }
        int[] nodes = nodes(g);
        while(g.edgeSize() < numOfEdges) {
            int rnd1=rnIndexes.nextInt(numOfNodes);
            int rnd2=rnIndexes.nextInt(numOfNodes);
            int key1 = nodes[rnd1];
            int key2 = nodes[rnd2];
            g.connect(key1,key2,(rnKeys.nextDouble())*(numOfEdges+100));
        }
    }


    private static int[] nodes(WGraph_DS g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }


    @Test
    void getNode() {
        initGraph(g1,10,12, 5);
        // existing nodes
        assertNotNull(g1.getNode(17));
        assertNotNull(g1.getNode(52));
        assertNotNull(g1.getNode(11));
        // non existing nodes
        assertNull(g1.getNode(91));
        assertNull(g1.getNode(6));
        assertNull(g1.getNode(13));
        // removed nodes
        g1.removeNode(18);
        g1.removeNode(44);
        g1.removeNode(5);
        assertNull(g1.getNode(18));
        assertNull(g1.getNode(44));
        assertNull(g1.getNode(5));
    }

    @Test
    void hasEdge() {
        initGraph(g1,10,12, 5);
        //existing nodes that are connected
        assertTrue(g1.hasEdge(48,18));
        assertTrue(g1.hasEdge(7,11));
        assertTrue(g1.hasEdge(5,18));
        //existing nodes that are not connected
        assertFalse(g1.hasEdge(7,18));
        assertFalse(g1.hasEdge(52,48));
        assertFalse(g1.hasEdge(7,7));
        //non existing nodes that are not connected
        assertFalse(g1.hasEdge(24,52));
        assertFalse(g1.hasEdge(14,6));
        assertFalse(g1.hasEdge(19,19));
    }

    @Test
    void getEdge() {
        initGraph(g1,10,12, 5);
        // existing nodes with exiting edges- right weights
        assertEquals(g1.getEdge(18,48), g1.getEdge(18,48));
        assertEquals(g1.getEdge(52,8), g1.getEdge(8,52));
        assertEquals(g1.getEdge(17,7), 35.47, 0.01);
        assertEquals(g1.getEdge(44,52), 53.07, 0.01);
        assertEquals(g1.getEdge(5,44), 22.39, 0.01);
        // existing nodes with exiting edges- wrong weights
        assertNotEquals(g1.getEdge(18,21), 2.71, 0.01);
        assertNotEquals(g1.getEdge(18,48), 6.63, 0.01);
        assertNotEquals(g1.getEdge(52,8), 53.41, 0.01);
        // existing nodes with non exiting edges
        assertEquals(g1.getEdge(48,17), -1);
        assertEquals(g1.getEdge(5,21), -1);
        assertEquals(g1.getEdge(7,18), -1);
        // non existing nodes
        assertEquals(g1.getEdge(49,17), -1);
        assertEquals(g1.getEdge(47,3), -1);
        assertEquals(g1.getEdge(8,9), -1);
    }

    @Test
    void addNode (){
        initGraph(g1,10,12, 5);
        //adding non existing nodes
        g1.addNode(19);
        assertTrue(g1.nodeSize()==11);
        g1.addNode(23);
        assertTrue(g1.nodeSize()==12);
        g1.addNode(15);
        assertTrue(g1.nodeSize()==13);
        // adding existing nodes
        g1.addNode(48);
        assertTrue(g1.nodeSize()==13);
        g1.addNode(52);
        assertTrue(g1.nodeSize()==13);
        g1.addNode(21);
        assertTrue(g1.nodeSize()==13);
    }

    @Test
    void connect() {
        initGraph(g1,10,12, 5);
        // connect existing nodes with no edges between them
        g1.connect(48,17,6);
        assertTrue(g1.edgeSize()==13);
        g1.connect(52,18,6);
        assertTrue(g1.edgeSize()==14);
        g1.connect(7,48,6);
        assertTrue(g1.edgeSize()==15);
        // connect existing nodes with exiting edge between them
        g1.connect(18,48,6);
        assertTrue(g1.edgeSize()==15);
        g1.connect(8,52,6);
        assertTrue(g1.edgeSize()==15);
        g1.connect(7,17,6);
        assertTrue(g1.edgeSize()==15);
        //connect non existing nodes
        g1.connect(19,48,6);
        assertTrue(g1.edgeSize()==15);
        g1.connect(10,52,6);
        assertTrue(g1.edgeSize()==15);
        g1.connect(14,14,6);
        assertTrue(g1.edgeSize()==15);
        // connect a node to itself
        g1.connect(7,7,6);
        assertTrue(g1.edgeSize()==15);
        g1.connect(5,5,6);
        assertTrue(g1.edgeSize()==15);
    }

    @Test
    void getV() {
        initGraph(g1,10,12, 5);
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
        assertTrue(g1.getV().containsAll(s));
    }

    @Test
    void testGetV() {
        initGraph(g1,10,12, 5);
        Collection<node_info> nei= new HashSet<>(g1.nodeSize());
        // adding 8's neighbors
        nei.add(g1.getNode(44));
        nei.add(g1.getNode(7));
        nei.add(g1.getNode(52));
        assertTrue(g1.getV(8).containsAll(nei));
        nei.clear();
        // adding 5's neighbors
        nei.add(g1.getNode(18));
        nei.add(g1.getNode(48));
        nei.add(g1.getNode(44));
        assertTrue(g1.getV(5).containsAll(nei));
        nei.clear();
        // adding 11's neighbors
        nei.add(g1.getNode(7));
        assertTrue(g1.getV(11).containsAll(nei));
    }

    @Test
    void removeNode() {
        initGraph(g1,10,12, 5);
        /// remove existing node
        assertEquals(g1.getNode(48), g1.removeNode(48)); //returns the right node
        assertEquals(g1.nodeSize(),9); //removed the node from the list of nodes
        assertEquals(g1.edgeSize(),9); //removed the node's edges from the edges list
        assertTrue(g1.getV(18).size()==2); //didn't hurt the neighbors list of neighbors
        assertTrue(g1.getV(5).size()==2);
        assertTrue(g1.getV(21).size()==1);
        assertFalse(g1.hasEdge(48,18)); //removed the node from the neighbors' neighbors list
        assertFalse(g1.hasEdge(48,5));
        assertFalse(g1.hasEdge(48,21));
        assertNull(g1.getNode(48)); //the list of nodes doesn't contains the node's key anymore
        /// remove another exiting node
        assertEquals(g1.getNode(7), g1.removeNode(7)); //returns the right node
        assertEquals(g1.nodeSize(),8); //removed the node from the list of nodes
        assertEquals(g1.edgeSize(),6); //removed the node's edges from the edges list
        assertTrue(g1.getV(11).size()==0); //didn't hurt the neighbors list of neighbors
        assertTrue(g1.getV(17).size()==0);
        assertTrue(g1.getV(8).size()==2);
        assertFalse(g1.hasEdge(11,7)); //removed the node from the neighbors' neighbors list
        assertFalse(g1.hasEdge(17,7));
        assertFalse(g1.hasEdge(8,7));
        assertNull(g1.getNode(7)); //the list of nodes doesn't contains the node's key anymore
        /// remove non exiting node
        assertEquals(g1.getNode(91), g1.removeNode(91));
        assertEquals(g1.nodeSize(),8);
        assertEquals(g1.edgeSize(),6);
        assertNull(g1.getNode(91));
        Collection<node_info> s= new HashSet<>(g1.nodeSize());
        s.add(g1.getNode(18));
        s.add(g1.getNode(17));
        s.add(g1.getNode(52));
        s.add(g1.getNode(11));
        s.add(g1.getNode(8));
        s.add(g1.getNode(5));
        s.add(g1.getNode(21));
        s.add(g1.getNode(44));
        assertTrue(s.containsAll(g1.getV())); //the two nodes has been removed from the collections of the nodes
        assertTrue(g1.getV().containsAll(s));
    }

    @Test
    void removeEdge() {
        initGraph(g1,10,12, 5);
        // remove an existing edge
        g1.removeEdge(8,52);
        assertEquals(g1.nodeSize(),10); //didn't hurt the number of nodes
        assertEquals(g1.edgeSize(),11); // removed the edge from the list of edges
        assertFalse(g1.getV(52).contains(g1.getNode(8))); //52 is not a neighbor of 8
        assertFalse(g1.getV(8).contains(g1.getNode(52))); //8 is not a neighbor of 52
        assertTrue(g1.getEdge(51,8)==-1); // the weight of the edge is -1 bc it doesn't exist
        assertEquals(g1.getV(8).size(),2);
        assertEquals(g1.getV(52).size(),1);
        // remove another existing edge
        g1.removeEdge(48,21);
        assertEquals(g1.nodeSize(),10); //didn't hurt the number of nodes
        assertEquals(g1.edgeSize(),10); // removed the edge from the list of edges
        assertFalse(g1.getV(21).contains(g1.getNode(48))); //21 is not a neighbor of 48
        assertFalse(g1.getV(48).contains(g1.getNode(21))); //48 is not a neighbor of 21
        assertTrue(g1.getEdge(21,48)==-1); // the weight of the edge is -1 bc it doesn't exist
        assertEquals(g1.getV(21).size(),1);
        assertEquals(g1.getV(48).size(),2);
        // remove a non existing edge
        g1.removeEdge(48,21);
        assertEquals(g1.nodeSize(),10); //didn't hurt the number of nodes
        assertEquals(g1.edgeSize(),10); // removed the edge from the list of edges
        // remove a non existing edge from a non existing node
        g1.removeEdge(91,21);
        assertEquals(g1.nodeSize(),10); //didn't hurt the number of nodes
        assertEquals(g1.edgeSize(),10); // removed the edge from the list of edges
    }

    @Test
    void nodeSize() {
        initGraph(g1,10,12, 5);
        assertEquals(g1.nodeSize(),10);
        //adding new nodes
        g1.addNode(6);
        assertEquals(g1.nodeSize(),11);
        g1.addNode(19);
        assertEquals(g1.nodeSize(),12);
        //remove two existing nodes
        g1.removeNode(48);
        assertEquals(g1.nodeSize(),11);
        g1.removeNode(17);
        assertEquals(g1.nodeSize(),10);
        //adding existing nodes
        g1.addNode(21);
        assertEquals(g1.nodeSize(),10);
        g1.addNode(5);
        assertEquals(g1.nodeSize(),10);
        //removing non existing nodes
        g1.removeNode(91);
        assertEquals(g1.nodeSize(),10);
        g1.removeNode(0);
        assertEquals(g1.nodeSize(),10);
    }

    @Test
    void edgeSize() {
        initGraph(g1,10,12, 5);
        assertEquals(g1.edgeSize(),12);
        //adding new nodes
        g1.connect(52,18,6);
        assertEquals(g1.edgeSize(),13);
        g1.connect(48,17,6);
        assertEquals(g1.edgeSize(),14);
        //remove two existing edges
        g1.removeEdge(21,48);
        assertEquals(g1.edgeSize(),13);
        g1.removeEdge(44,52);
        assertEquals(g1.edgeSize(),12);
        //adding existing edges
        g1.connect(8,7,6);
        assertEquals(g1.edgeSize(),12);
        g1.connect(18,48,6);
        assertEquals(g1.edgeSize(),12);
        //removing non existing edges
        g1.removeEdge(44,48);
        assertEquals(g1.edgeSize(),12);
        g1.removeEdge(7,1);
        assertEquals(g1.edgeSize(),12);

    }

    @Test
    void getMC() {
        initGraph(g1,10,12, 5);
        assertEquals(g1.getMC(),22); //adding 10 nodes and 12 edges
        //remove an existing node
        g1.removeNode(52); // +3: one node, 2 edges
        assertEquals(g1.getMC(),25);
        //remove non existing node
        g1.removeNode(91); // +0
        assertEquals(g1.getMC(),25);
        // add a non existing node
        g1.addNode(6); //+1
        assertEquals(g1.getMC(),26);
        // add an existing node
        g1.addNode(6); //+0
        assertEquals(g1.getMC(),26);
        // add a new edge
        g1.connect(6,17,8); //+1
        assertEquals(g1.getMC(),27);
        // add an existing edge
        g1.connect(6,17,8); //+0
        assertEquals(g1.getMC(),27);
        //remove an existing edge
        g1.removeEdge(18,21); //+1
        assertEquals(g1.getMC(),28);
        //remove an non existing edge
        g1.removeEdge(18,21); //+0
        assertEquals(g1.getMC(),28);
        g1.getNode(6); //+0
    }
    @Test
    public void testTimeRun(){
        long start= new Date().getTime();
        WGraph_DS g0 = new WGraph_DS();
        for (Integer i=0 ; i<1000000 ; i++){
            g0.addNode(i);

        }
        Random rnIndexes= new Random(5);
        while(g0.edgeSize() < 10000000) {
            int rnd1= (int) ((Math.random())*1000000);
            int rnd2=(int) ((Math.random())*1000000);
            g0.connect(rnd1,rnd2,0.5);
        }
        long end= new Date().getTime();
        double dt= (end-start)/1000.0;
        System.out.println(dt);
    }
}