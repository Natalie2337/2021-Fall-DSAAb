
import edu.princeton.cs.algs4.Bag;

public class Digraph {
    private final int V;
    private final Bag<Integer>[] adj;

    public Digraph(int V){
        this.V=V;
        adj=(Bag<Integer>[]) new Bag;
    }

}
