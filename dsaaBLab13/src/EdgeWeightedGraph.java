import edu.princeton.cs.algs4.*;

public class EdgeWeightedGraph {
   private final int V;
   private int E;
   private Bag<Edge>[] adj;

   public EdgeWeightedGraph(int V){
       this.V=V;
       adj = (Bag<Edge>[]) new Bag[V];
       for (int v = 0; v < V; v++) {
           adj[v] = new Bag<Edge>();
       }
   }
   //public EdgeWeightedGraph(In in)
    public int V(){
       return V;
    }

    public int E(){
       return E;
    }


   public void addEdge(Edge edge){
       int v = edge.either(), w = edge.other(v);
       adj[v].add(edge);
       adj[w].add(edge);
   }

   public Iterable<Edge> adj(int v){//Iterable adj,找到和v相邻的所有边
       return adj[v];//找到和v相邻的所有边
   }

   public Iterable<Edge> edges(){//Iterable edges,找到图中的所有边
       Bag<Edge> b = new Bag<Edge>();
       for (int v = 0; v < V; v++) {//遍历所有节点v
           for (Edge e: adj[v]) {//找到与每个节点v相邻的所有边
               if(e.other(v)>v) b.add(e);//不太明白意思
           }
       }return b;
   }


}
