import edu.princeton.cs.algs4.Queue;


public class BreadthFirstPaths {
    private boolean[] marked;
    private int[] edgeTo;
    private int[] distTo;

    private void bfs (Graph G,int s){
        Queue<Integer> q = new Queue<Integer>();
        //用FIFO先进先出的queue来实现的目的是因为我需要分层来处理，第一层进去的就先处理
        q.enqueue(s);
        marked[s] = true;
        distTo[s] = 0;

        while(!q.isEmpty()){
            int v = q.dequeue();//在queue还未被弹空的情况下，挨个弹出、处理这些元素
            for (int w:G.adjacency(v)) {//遍历与节点v相邻的所有元素
                if(!marked[w]){
                    q.enqueue(w);//凡是被加入queue的都会被marked
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v]+1;
                }
            }

        }
    }

}
