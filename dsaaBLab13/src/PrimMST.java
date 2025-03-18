import edu.princeton.cs.algs4.IndexMinPQ;

public class PrimMST {
    private Edge[] edgeTo;//edgeTo[v]表示将v和树连接起来的最短边
    private double[] distTo;//distTo[v]=edgeTo[v].weight()即表示该边的权重
    private boolean[] marked;
    private IndexMinPQ<Double> pq;

    public void visit(EdgeWeightedGraph G, int v){
        marked[v] = true;
        for (Edge e:G.adj(v)) {
            int w = e.other(v);
            if(marked[w]) continue;
            if(e.)
        }

    }
}
