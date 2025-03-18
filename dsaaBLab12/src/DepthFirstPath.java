import java.util.Stack;

public class DepthFirstPath {
    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    //degree:一个顶点的degree，是和它相连的边的个数
    public DepthFirstPath(Graph G, int s){
        //构建一个constructor，并且首先需要初始化这个数据结构，其次需要find vertices connected to s
        //

    }

    private void dfs(Graph G, int v){
        marked[v] = true;//凡是搜索过的就mark上
        for (int w:G.adjacency(v)) {//遍历它所有相邻的节点
            if(!marked[w]){
                dfs(G,w);//递归地调用dfs
                edgeTo[w]=v;
            }
        }
    }

    public boolean hasPathTo(int v){
        return marked[v];//如果相连通，那么就已经被marked了
    }

    public Iterable<Integer> pathTo(int v){
        if(!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x !=s ; x=edgeTo[v]) {//要特别注意这个地方，fori的语句是可以多变的！比如for(int x=v;x!=s;x=edgeTo[v])
            path.push(x);
        }
        path.push(s);//把所有临边都压栈，最后再压s
        return path;
    }
}
