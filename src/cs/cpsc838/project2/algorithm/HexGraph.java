package cs.cpsc838.project2.algorithm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author emmanuj
 * @param <N> node type
 * @param <E> edge type
 */
public class HexGraph<N, E> {

    private final int V;
    private int E;
    private HashMap<N, Set<N>> adj_list; //represent the adjacency set (no loops or parallel edges)
    /**
     * Create a graph with no edge or vertices - an empty graph
     */
    public HexGraph() {
        this.V = 84; // 14 hexes each with 6 vertices
        this.E =0;
        
    }

    /**
     *
     * @param V Create a V-vertex graph with no edges
     */
    public HexGraph(int V) {
        this.V = V;
        this.E =0;
        
        adj_list = new HashMap<>();
    }

    /**
     *
     * @return number of vertices in this graph
     */
    public int vertices() {
        return V;
    }

    /**
     * @return number of total edges in the graph
     */
    public int edges() {
        return E;
    }

    /**
     *
     * Order not important since this is an undirected graph
     *
     * @param v vertex from
     * @param u vertex to
     *
     */
    public void addEdge(N v, N u) {
        
        //add u to v's list
        if(adj_list.containsKey(v)){
            adj_list.get(v).add(u);
        }else{
            Set<N> bag = new HashSet<>();
            bag.add(u);
            adj_list.put(v,bag);
        }
        
        //add v to u's list
        if(adj_list.containsKey(u)){
            adj_list.get(u).add(v);
        }else{
            Set<N> bag = new HashSet<>();
            bag.add(v);
            adj_list.put(u,bag);
        }
    }

    /**
     *
     * @param v vertex
     * @return neighbors of the vertex v
     */
    public Iterable<N> neighbors(int v) {
        return null;
    }

    /**
     * @return string representation of the graph
     */
    @Override
    public String toString() {
        String s = V + " vertices, " + E + " edges\n";
        for (int v = 0; v < V; v++) {
            s += v + ": ";
            for (N w : this.neighbors(v)) {
                s += w + " ";
            }
            s += "\n";
        }
        return s;
    }
}
