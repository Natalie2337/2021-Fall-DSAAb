public class NaturalMergeSort {
    //决定从左到右，两两进行merge。


    //首先截出两段，两个分段点pointer1与pointer2
    private static void findTwoPointers(int[] array){
        int pointer1=0;
        int pointer2;
        while((array[pointer1]<=array[pointer1+1])&&(pointer1<= array.length)){
            pointer1++;
            }
        pointer2=pointer1;
        while((array[pointer2]<=array[pointer2+1])&&(pointer2<= array.length)){
            pointer2++;
        }
        // 123451234123456,那么得到的pointer1=5,pointer2=9对应array[pointer1]=1,array[pointer2]=1
    }


    //仿照一分为二的merge，进行merge.
    private static void merge(int[] array,int pointer1 ,int pointer2,int[] aux){
        System.arraycopy(array,0,aux,0,pointer2-1);//把从array[0]到array[pointer2-1]全复制到aux[]中
        int i,j1,j2;
        for(i=0,j1=0,j2=pointer1;j1<=pointer1&&j2<= array.length;){
            if(aux[j1]<=aux[j2])
                array[i++]=aux[j1++];
            else
                array[i++]=aux[j2++];
        }
        while(j1<=pointer1)
            array[i++]=aux[j1++];
        while(j2<=pointer2)
            array[i++]=aux[j2++];
    }//注意还不是最终版本，最终应该使用 bttom up 的版本

    private static void insertion( int[] array, int low, int high ) {
        for( int i = low+1; i <= high; ++ i ) {
            int tmp = array[i];
            int j;
            for( j = i; j > low && array[j-1] > tmp; -- j )
                array[j] = array[j-1];
            array[j] = tmp;
        }
    }

    private static void sort( int[] array, int low, int high, int[] aux ) {
        if( high - low < 10 ) {
            insertion(array, low, high);
            return;
        }
        int mid = low + (high-low)/2;
        sort( array, low, mid, aux );
        sort( array, mid+1, high, aux );
        merge(array, low, mid, high, aux);
    }




}
