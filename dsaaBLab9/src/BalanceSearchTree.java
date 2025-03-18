import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.TreeMap;
import java.util.Map.Entry;

public class BalanceSearchTree<Key extends Comparable<Key>,Value> {

    private TreeNode<Key,Value> root = null;
    private int size =0;

    public int size(){
        return size;
    }

    public Value get (Key key){
        if(key==null)
            return null;
        TreeNode<Key,Value> node = root;
        while(node!=null){
            int compare = node.key.compareTo(key);
            if(compare<0)
                node=node.right;
            else if (compare>0)
                node=node.left;
            else
                return node.value;
        }
        return null;
    }

    public Value put(Key key, Value value){

        if(key==null||value==null)
            return null;

        if(root==null){
            root = new TreeNode<Key,Value>(key,value);
            return null;
        }

        TreeNode<Key,Value> node =root;
        while(node!=null){
            int compare = node.key.compareTo(key);
            if(compare<0){
                if(node.right==null){
                    node.right=new TreeNode<Key,Value>(key,value);
                    node.right.parent =node;
                    size++;
                    return null;
                }else
                    node=node.right;
            }else if(compare>0){
                if(node.left==null){
                    node.left=new TreeNode<Key,Value>(key,value);
                    node.left.parent =node;
                    size++;
                    return null;
                }else
                    node=node.left;
            }else {
                Value ret = node.value;
                node.value=value;
                return ret;
            }
        }
        return null;
    }





}
