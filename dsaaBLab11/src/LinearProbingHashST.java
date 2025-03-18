public class LinearProbingHashST<Key,Value> {
    private int M = 30001;
    private Value[] values = (Value []) new Object [M];
    private Key[] keys = (Key[]) new Object [M];

    private int hash(Key key){
        return (key.hashCode()&0x7fffffff);

    }

    private void put(Key key, Value value){
        int 

    }

    public Value get(Key key) {//搜索得到key对应的value
        for (int i = hash(key); keys[i] != null; i = (i + 1) % M) {
            //LinearProbing算法中，不断向右移一格，直到搜索到空为止
            if (key.equals(keys[i])) {
                return values[i];
            }
        }return null;
    }


}
