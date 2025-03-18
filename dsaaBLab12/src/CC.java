import edu.princeton.cs.algs4.Graph;

public class CC {//finding connected components
    private boolean[] marked;
    private int[] id;//用来找到v所在部分的序号
    private int count;

    public CC(Graph G){//constructor里面的参数一般是Graph 和 Vertex
        marked = new boolean[G.V()];
        id = new int[G.V()];//这就是所谓的初始化吧
        for (int v = 0; v <G.V() ; v++) {
            if(!marked[v]){
                dfs(G,v);//利用depthfirstsearch来判断是否为连通图
                count++;
            }
        }
    }

    public int count(){
        return count;

    }

    public int id(int v){
        return id[v];
    }

    public boolean connected(int v, int w){
        return id[v]==id[w];//通过v和w的部分编号id是否相同来判断它们是否联通！
    }

    private void dfs(Graph G, int v){
        marked[v]=true;
        id[v]=count;
        for (int w:G.adj(v)) {
            if(!marked[w]){
                dfs(G,w);

            }
        }

    }





}
