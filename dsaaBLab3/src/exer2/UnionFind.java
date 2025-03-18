package exer2;

public class UnionFind {
    // Define whatever data you want.
    private int[] id;

    public UnionFind( int N ) {
        // Write a constructor if necessary.
        id = new int[N];
        for (int i = 0; i < N; i++) id[i]=i;

    }

    public int find( int i ) {
        while (i!= id[i]) i=id[i];
        return i;
    }

    public boolean isConnected( int p, int q ) {
        return find(p) == find(q);
    }

    public void union( int p, int q ) {
        int i = find(p);
        int j = find(q);
        id[i] = j;
    }
}
