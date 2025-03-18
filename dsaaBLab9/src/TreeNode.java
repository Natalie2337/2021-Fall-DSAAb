
public class TreeNode <Key extends Comparable<Key>, Value> {

    public Key key;
    public Value value;

    public TreeNode<Key, Value> left = null;
    public TreeNode<Key, Value> right = null;
    public TreeNode<Key, Value> parent = null;


    public TreeNode( Key key, Value value ) {
        this.key = key;
        this.value = value;
    }
}
