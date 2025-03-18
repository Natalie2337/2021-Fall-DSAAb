import edu.princeton.cs.algs4.*;

public class BST<Key extends Comparable<Key>,Value>{

    /*public class BST {//注意因为 Key并不是基本数据类型，因此需要使用泛型<>,否则会报错
    private class Node{
        private Key key
    }
}*/
    private Node root;//要定义一个BST的根节点

    private class Node{//一个Node包含四个部分：a key and a value, a reference to left and right subtree
        private Key key;
        private Value val;
        private Node left,right;

        public Node(Key key,Value value){//有疑问！
            this.key=key;
            this.val= value;
        }
    }

    private void put(Key key, Value value){
        root= put(root,key,value);

    }

    private Node put(Node x, Key key,Value value){
        if(x==null) return new Node(key,value);
        int cmp = key.compareTo(x.key);
        if(cmp<0){
            x.left=put(x.left,key,value);//递归地调用，也就是在放置到合适位置前逐层查找
        }else if(cmp>0){
            x.right=put(x.right,key,value);
        }else x.val=value;//当根据key找到恰当的位置时，将value设成相应value

        return x;//返回的是节点
    }
    //number of compares for search/insert equals the depth of the node + 1

    private Value get(Key key){
        Node x = root;
        while(x!=null){
            int cmp = key.compareTo(key);
            if(cmp<0) x=x.left;
            else if(cmp>0) x=x.right;
            else return x.val;
        }
        return null;
    }

    public Key floor(Key key){//返回小于key的最大键
        Node x = floor(root,key);
        if(x==null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key){
        //就像put一样，要实现floor，要写两个方法。一个用来返回所需要的key,另一个返回类型为Node,目的是用来在查找时实现递归！
        if(x==null) return null;//要考虑到null的情形！
        int cmp = key.compareTo(x.key);
        if(cmp==0) return x;
        else if(cmp<0){
            //说明给定的key比当前节点的键(x.key)要小，那么，如果想找到比key还小的键，则应该在当前节点的基础上继续往左走！因此需要递归调用floor!
            return floor(x.left,key);
        }else{
            //此时cmp>0,说明当前节点的键(x.key)比给定的key要小的时候，不过还不能直接认为查找停止了
            // 因为我要找的是比key小的最大键，因此这时候我需要进一步确认比x.key稍大的x.right是不是也比key小
            Node t = floor(x.right,key);
            if(t!=null) return t;
            else return x;
            //按这样的规则一直走直到最终确定不再存在这样向右走的情况
        }
    }

    //implement rank()和 select():
    //rank:是找比它小的节点数(注意不是从根节点开始算起的！)在每个节点储存

}


