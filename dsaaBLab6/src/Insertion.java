public class Insertion {
    public static void sort( int[] array, int low, int high ) {
        for( int i = low+1; i <= high; ++ i ) {
            int tmp = array[i];
            int j;
            for( j = i; j > low && array[j-1] > tmp; -- j )
                array[j] = array[j-1];
            array[j] = tmp;
        }
    }
}
