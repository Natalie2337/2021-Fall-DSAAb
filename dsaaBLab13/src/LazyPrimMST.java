import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;


public class LazyPrimMST {
    private boolean[] marked;//通过添加边生成MST，就相当于添加顶点。如果添加了就认为是标记过(marked)了。
    private Queue<Edge> mst;//最小生成树的边
    private MinPQ<Edge> pq;//所有被加入的cut，包括已经变为无效的cut

    public LazyPrimMST(EdgeWeightedGraph G){
        pq = new MinPQ<Edge>();
        marked = new boolean[G.V()];
        mst = new Queue<Edge>() ;

        visit(G,0);//怎么假设G是联通的呀？
        while(!pq.isEmpty()){
            Edge edge = pq.delMin();
            int v = edge.either(), w = edge.other(v);
            if(marked[v]&& marked[w]) continue;
                //如何判断边是否已经失效：如果这时取出来的边对应的两个顶点是已经标记过的v和w，则说明失效.对于失效的边continue 跳过
            mst.enqueue(edge);
            if(!marked[v]) visit(G,v);//记得更新标记记录
            if(!marked[w]) visit(G,w);
        }

    }

    private void visit(EdgeWeightedGraph G, int v){
        //visit方法包括：标价(mark)顶点，并将所有连接v和未被标记的顶点的边(新的有效的cut)加入pq;
        marked[v] = true;//改为已标记
        for (Edge e:G.adj(v)) {//遍历总图G中与v相连的边
            if(!marked[e.other(v)]) pq.insert(e);//这里other()派上用场了！
        }
    }

    public Iterable<Edge> edges(){return mst;}

}
