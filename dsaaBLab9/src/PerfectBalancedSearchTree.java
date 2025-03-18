import edu.princeton.cs.algs4.BST;

import java.util.Arrays;

public class PerfectBalancedSearchTree {
    //类似于二分，让最中间的作根，左边最中间的作第一个左子节点，以此类推保证刚好平衡。
    private static void sort (BST bst, String[] a){
        Arrays.sort(a);
    }

}
