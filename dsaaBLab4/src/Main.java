import java.util.Scanner;
import edu.princeton.cs.algs4.Stack;
public class Main {

    public static void main( String[] args ) {
        // write your code here
        //遇到左括号，左括号进栈。遇到右括号，对比栈顶是否为对应的左括号：若栈顶不是对应的左括号则输入不合法，若栈顶是对应地左括号则左括号出栈。
        //若出现栈顶为空则输入不合法。若所有元素检查完后栈内还有元素，则说明左括号数量多于右括号，输入依然不合法
        Stack<Character> leftBrackets =new Stack<>();
        Scanner in  = new Scanner(System.in);
        String str = in.nextLine();
        boolean valid =true;

        for (int i = 0; i < str.length(); i++) {
            char bracket= str.charAt(i);

            if ((bracket=='(')||(bracket=='[')||(bracket=='{')){
                leftBrackets.push(bracket);
            }

            else{
                if((bracket==')') && (leftBrackets.pop()!='(')) valid= false;
                else if((bracket=='}') && (leftBrackets.pop()!='{')) valid = false;
                else if((bracket==']') && (leftBrackets.pop()!='[')) valid =false;
            }
        }


        if(!leftBrackets.isEmpty()) {
            valid =false;
        }

        if(valid) System.out.println("1");
        if(!valid) System.out.println("0");


    }
}
