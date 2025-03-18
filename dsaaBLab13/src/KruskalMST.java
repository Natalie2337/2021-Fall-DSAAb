import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;

import java.util.Comparator;


public class KruskalMST {
    private Queue<Edge> mst = new Queue<Edge>();
    public KruskalMST(EdgeWeightedGraph G){
        MinPQ<Edge> pq = new MinPQ<Edge>((Comparator<Edge>) G.edges());//??
        UF uf = new UF(G.V());
        while(!pq.isEmpty()&& mst.size()< G.V()-1){
            Edge edge = pq.delMin();
            int v= edge.either(), w=edge.other(v);
            if(uf.connected(v,w)) continue;//??
            uf.union(v,w);
            mst.enqueue(edge);
        }
    }

    public Iterable<Edge> edges(){
        return mst;
    }
    public double weight;


}
