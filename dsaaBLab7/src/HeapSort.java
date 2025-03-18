public class HeapSort {

    private static void sink( int[] pq, int idx, int count ) {
        int value = pq[idx];
        while( idx*2 <= count ) {
            int id2 = (idx*2+1 <= count && pq[idx*2]<pq[idx*2+1]) ? (idx*2+1) : (idx*2);
            if( pq[id2] > value ) {
                pq[idx] = pq[id2];
                idx = id2;
            } else
                break;
        }
        pq[idx] = value;
    }

    private void swim( int[]pq, int idx ) {
        int value = pq[idx];
        while( idx > 1 && pq[idx/2] < value ) {
            pq[idx] = pq[idx/2];
            idx /= 2;
        }
        pq[idx] = value;
    }

    private static void buildHeap(int[] array){
        int count = array.length;

        for (int i = 1; i <count ; ++i) {
            swim(array,i);
        }//自下而上上浮建堆，时间复杂度nlogn
        //或者
        for (int i = count/2; i >=1 ; --i) {
            sink(array,i,count);
        }//自上而下，时间复杂度n
    }
    //在所有涉及数组的地方-1，因为堆是从1开始排序的
    public static void sort(int[] array){

    }
}
