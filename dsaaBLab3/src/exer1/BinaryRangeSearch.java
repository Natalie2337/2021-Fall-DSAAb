package exer1;

public class BinaryRangeSearch {

    public static int[] binarySearch( int[] array, int target ) {
        return binarySearch(array, target, 0, array.length-1);
    }

    public static int[] binarySearch( int[] array, int target, int low, int high ) {
        int left = -1;
        int right = -1;

        int lo = low;
        int hi = high;

        while( lo <= hi ) {
            int mid = lo + (hi-lo)/2;
            if( array[mid] < target ) {
                lo = mid+1;
            } else if( array[mid] > target ) {
                hi = mid-1;
            } else if( mid == lo || array[mid-1] < target ) {
                left = mid;
                break;
            } else {
                hi = mid-1;
            }
        }

        if( left == -1 )
            return new int[]{-1,-1};

        lo = left;
        hi = high;

        while( lo <= hi ) {
            int mid = lo + (hi-lo)/2;
            if( array[mid] > target ) {
                hi = mid-1;
            } else if( mid == hi || array[mid+1] > target ) {
                right = mid;
                break;
            } else {
                lo = mid+1;
            }
        }
        return new int[]{left, right};
    }

}
