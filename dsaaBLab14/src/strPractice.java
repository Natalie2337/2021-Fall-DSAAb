public class strPractice {
    public static String reverse(String s){
        String rev=" ";
        for (int i = s.length(); i >=0 ; i--) {
            rev += s.charAt(i);
        }
        return rev;
    }

    public static String reverse2(String s){
        StringBuilder rev2 = new StringBuilder();
        for (int i = s.length(); i>=0 ; i--) {
            rev2.append(s.charAt(i));
        }
        return rev2.toString();
    }
    //直接用String,时间复杂度为O(N^2),而用StringBuilder时间复杂度为O(N)
    
    public static void sortString(){
        String arr[] = new String[10];
        int N = arr.length;
        int count[] = new int[arr.length+1];
        for (int i = 0; i < N; i++) {
             count[arr[i] + 1]++;

        }
    }
}
