import java.util.LinkedList;

public class GraphAdjacencyNew implements GraphNew{
    //用邻接矩阵来表示无向图
    //override一定要实现父类的所有方法

    private int num;
    private boolean[][] adj;//邻接矩阵

    public GraphAdjacencyNew (int verticesNumber){//constructor
        num=verticesNumber;
        adj=new boolean[num][num];
    }

    @Override
    public int size() {
        return num;
    }

    @Override
    public void addEdge(int v1, int v2) {
        adj[v1][v2]=true;

    }

    @Override
    public Iterable<Integer> adjacency(int v) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i <num ; i++) {
            if(adj[v][i])
                list.add(i);
        }
        return list;
    }

    @Override
    public boolean hasEdge(int v1, int v2) {
        return adj[v1][v2];
    }
}
