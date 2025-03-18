package exer1;

import java.util.Arrays;

import edu.princeton.cs.algs4.BinarySearch;


public class RangedThreeSum {

    private RangedThreeSum() {
    }

    public static long count(int[] a, int min, int max) {
        int n = a.length;
        long count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int t = min; t <= max; t++) {
                    int[] k = BinaryRangeSearch.binarySearch(a, t - (a[i] + a[j]), j + 1, n - 1);
                    if (k[0] != -1) count += k[1] - k[0] + 1;
                }
            }
        }

        return count;
    }
}
