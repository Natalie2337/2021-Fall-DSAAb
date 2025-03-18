import java.util.Random;
import java.util.Arrays;


public class QuickSortByMyself {

    private static void swap(int[]array, int i, int j){
        int tmp = array[i];
        array[i]= array[j];
        array[j] =tmp;
    }

    private static void sort(int[] array, int low, int high, Random rand){

        if(high-low>8){
            int pivot = array[rand.nextInt(high-low+1)+low];
            int i=low-1;
            int j=low-1;
            int k=high+1;

            while(j+1<k){
                if(array[j+1]<pivot){
                    swap(array,j+1,i+1);
                    i++;
                    j++;
                }else if(array[j+1]==pivot){
                    j++;
                }else {
                    swap(array,j+1, k-1);
                    k--;
                }
            }

            sort(array, low, i, rand);
            sort(array, k, high, rand);
        }

    }

    public static void sort(int[] array){
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

   /* public static void main( String[] args ) {
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
    }*/

    public static void main(String[] args){
        int[] arr = new int [100000];
        for (int i = 0; i <100000 ; i++) {
            arr[i]=(int)(Math.random()*100000);
        }

        Boolean result = test(arr);
        System.out.println(result);
    }

}
