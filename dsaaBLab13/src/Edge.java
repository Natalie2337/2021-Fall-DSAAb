import edu.princeton.cs.algs4.*;

public class Edge implements Comparable<Edge>{

    private final int v,w;
    private final double weight;

    public Edge(int v,int w, int weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either(){
        return v;//还是不是很明白这个地方??
    }

    public int other(int vertex){
        if(vertex==v) return w;
        else return v;//这个地方也不是很明白
    }

    public int compareTo(Edge that){//要利用Comparable 抽象类来使用接口，一定要实现方法compareTo
        if(this.weight<that.weight) return -1;
        else if(this.weight>that.weight) return +1;
        else return 0;
    }

}
