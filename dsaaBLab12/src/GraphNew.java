public interface GraphNew {
    //一个interface里面是没有具体的方法
    int size();
    void addEdge(int v1,int v2);
    Iterable<Integer> adjacency (int v);//return all vertices that vertex v is connected to
    boolean hasEdge(int v1,int v2);
}
