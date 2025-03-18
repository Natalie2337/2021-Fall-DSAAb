import java.util.LinkedList;

public class GraphAdjListNew implements GraphNew{
    private int num;
    private LinkedList<Integer> [] adjList;//注意泛型里面要用Integer,而不能用int

    @SuppressWarnings(value="unckecked")//告诉编译器忽略unchecked的信息
    public GraphAdjListNew(int verticesNumber){
        num=verticesNumber;
        adjList = new LinkedList[num];

        //邻接链表的建造，就是从数组adjList中的每个元素伸出一条邻接链表
        for (int i = 0; i < adjList.length; i++) {
            adjList[i]= new LinkedList<>();
        }
    }


    @Override
    public int size() {//如果是override的话，括号内参数个数与参数顺序都不能改变
        return num;
    }

    @Override
    public void addEdge(int v1, int v2) {
        if(adjList[v1].contains(v2)) return;
        adjList[v1].add(v2);

    }

    @Override
    public Iterable<Integer> adjacency(int v) {
        return (LinkedList<Integer>)adjList[v].clone();
    }

    @Override
    public boolean hasEdge(int v1, int v2) {
        return adjList[v1].contains(v2);
    }
}
