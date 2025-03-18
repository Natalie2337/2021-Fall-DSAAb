import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    private static void swap( int[] array, int i1, int i2 ) {
        int tmp = array[i1];
        array[i1] = array[i2];
        array[i2] = tmp;
    }

    private static void sort(int[] array, int low, int high, Random rand ) {
        //sort[low,high] in array
        //if (high - low < 10) {

            //insertion(array, low, high);
            //return;
        //}

        int pivot = array[rand.nextInt(high-low)+low];
        //将数组分成4段，小于、等于、未知、大于主元
        int i = low-1;
        int j = low-1;
        int k = high+1;
        // Do partitioning.
        // [low, i] are elements that is smaller than pivot.
        // [i+1, j] are elements that is equal to pivot.
        // [j+1, k-1] are elements that is waiting to be processed.未知大小的部分
        // [k, high] are elements that is larger than pivot.
        //下面对未知的部分进行检验
        while( j + 1 < k ) {
            if( array[j+1] < pivot ) {
                //若未知比主元小，和1区域的最后一个进行交换，交换后分界点i和j均向右移动
                swap( array, j+1, i+1 );
                j++;
                i++;
            } else if( array[j+1] == pivot ) {
                j++;
            } else { // array[i+1] > pivot
                swap( array, j+1, k-1);
                k --;
            }
        }
        sort( array, low, i, rand );
        sort( array, k, high, rand );
    }

    public static void sort(int[] array) {
        sort( array, 0, array.length-1, new Random() );
        Insertion.sort(array,0,array.length-1);
    }

    private static boolean test( int[] array ) {
        int[] array2 = new int[array.length];
        System.arraycopy(array, 0, array2, 0, array.length);
        Arrays.sort(array);
        sort(array2);
        return Arrays.equals(array, array2);
    }

    public static void main( String[] args ) {
        Random rand = new Random();
        boolean result = test(new int[]{1, 2, 3, 4, 5});
        result = result && test(new int[100000]);
        result = result && test(new int[0]);
        int[] array = new int[1000];
        for( int ite = 0; ite < 1000; ++ ite ) {
            for( int i = 0; i < array.length; ++ i )
                array[i] = rand.nextInt();
            result = result && test(array);
        }
        System.out.println(result);
    }
}
