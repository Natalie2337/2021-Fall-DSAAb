import edu.princeton.cs.algs4.ST;

public class SeparateChainingHashST<Key,Value> {
    //同一个hash值的所有东西在一条链表上
    private int M = 97;// M代表链表个数
    private Node[] st = new Node[M];


    private static class Node{
        private Object key;
        private Object value;
        private Node next;
        //定义key和value为object类型的
    }

    private int hash (Key key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public Value get (Key key){
        int i = hash(key);
        for(Node x = st[i];x !=null; x=x.next)
            if(key.equals(x.key)) return (Value) x.value;
    }
}
