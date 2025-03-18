package exer1;

import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestProblem1 {

    static long count( int arr[], int min, int max ) {
        long result = 0;
        for( int i = 0; i < arr.length; ++ i )
            for( int j = i+1; j < arr.length; ++ j )
                for( int k = j+1; k < arr.length; ++ k ) {
                    int num = arr[i]+arr[j]+arr[k];
                    if( min <= num && num <= max )
                        result ++;
                }
        return result;
    }
    
    static void test( int arr[], int min, int max, long answer ) {

        Stopwatch sw = new Stopwatch();
        long c = RangedThreeSum.count( arr, min, max );
        double t = sw.elapsedTime();

        StdOut.printf("%d data:  %s,  time:  %f\n", arr.length, (c==answer)?"right":"wrong", t);
        StdOut.printf("%s\n",  (c==answer && t <= 20 )? "Pass!" : "Fail!");
    }
    public static void main( String[] args ) {
        for( int i = 1; i <= 16; ++ i ) {
            try {
                In fin = new In("./resources/data1/"+i+"Kints.txt");
                int min = fin.readInt();
                int max = fin.readInt();
                int[] arr = fin.readAllInts();
                fin.close();

                fin = new In("./resources/ans1/"+i+".txt");
                long answer = fin.readLong();
                fin.close();

                test(arr, min, max, answer);

            } catch( IllegalArgumentException e ) {
                e.printStackTrace();
            }
        }
    }
}
