import java.util.ArrayList;
import java.util.LinkedList;

public class HashTable <Key,Value>{//hashtable也是通过key,value对来存储的

    private int capacity;
    class Node<Key,Value>{//不是很懂这里
        Key key;
        Value value;

        Node(Key k, Value v){
            key=k;
            value=v;
        }
    }

    //private Node<Key,Value>[] allData;//不用root，但用一个数组来存储所有的数据
    //为了让它separate chaining, 不要把多个元素放在同一个，改为
    //private LinkedList<Node<Key,Value>> [] allData;


    //当两个hash码相同的时候，为了不让之后的覆盖之前的，需要进行一些调整。
    //如何处理hash码相同的问题：连成链表，separate chaining.
    //
    private ArrayList<LinkedList<Node>> allData;
    public HashTable(int capacity){
        this.capacity=capacity;
        allData = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            allData.add(new LinkedList<>());
        }
    }

    private int hash(Key key){
        int hashcode=key.hashCode();
        return Math.abs(hashcode)% capacity;
    }

    public void put(Key key, Value value){
        int hashCode = hash(key);
        allData.get(hashCode).add(new Node(key,value));
    }


    public Value get (Key key) {
        int hashcode = hash(key);
        for (Node node : allData.get(hashcode)) {
            if (node.key.equals(key))
                return (Value) node.value;
            return null;
        }
    }

    public Value delete (Key key){
        int hashcode = hash(key);
        Iterator
    }


}
