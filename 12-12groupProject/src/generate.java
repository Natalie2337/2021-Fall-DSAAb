//import edu.princeton.cs.algs4.StdIn;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.PrintStream;
//import java.util.*;
//
//public class generate {
//    public static void main(String[] args) throws FileNotFoundException {
//        Random random = new Random(System.currentTimeMillis());
//        for (int i = 0; i < 4; i++) {
//            File file = new File("./randomGenerated" + i + ".tsv");
//            PrintStream printStream = new PrintStream(new FileOutputStream(file));
//            System.setOut(printStream);
//            System.out.println("terminal");
//            int num = (int) Math.pow(10, i + 1);
//            int len = (int) Math.ceil(Math.sqrt(num));
//            System.out.println(len);
//            System.out.println(num);
//            Stack<Circle> stack = new Stack<>();
//            w:
//            while (stack.size() < num) {
//                Circle circle = new Circle();
//                circle.x = random.nextDouble(len);
//                circle.y = random.nextDouble(len);
//                circle.radius = random.nextDouble(1.9) + 0.1;
//                circle.weight = random.nextDouble(10);
//                circle.vx = random.nextDouble();
//                circle.vy = random.nextDouble();
//                if (!inSpace(circle, len))
//                    continue;
//                for (Circle value : stack) {
//                    if (con(circle, value))
//                        continue w;
//                }
//                stack.push(circle);
//            }
//
//            for (Circle e : stack) {
//                System.out.println(e.x + "\t" + e.y + "\t" + e.vx + "\t" + e.vy + "\t" +
//                        e.radius + "\t" + e.weight + "\t" + e.r + "\t" + e.g + "\t" + e.b);
//            }
//
//            int event = random.nextInt(len) + 1;
//            System.out.println(event);
//            ArrayList<Integer> arr = new ArrayList<>();
//            for (int j = 0; j < event; j++) {
//                arr.add(random.nextInt(len));
//            }
//            arr.sort(Comparator.naturalOrder());
//            for (int ele : arr) {
//                System.out.println(ele + "\t" + random.nextInt(num));
//            }
//        }
//
//    }
//
//    private static boolean inSpace(Circle c, int len) {
//        return (c.y - c.radius) > 0 && (c.y + c.radius) < len && (c.x - c.radius) > 0 && (c.x + c.radius) < len;
//    }
//
//    private static boolean con(Circle c1, Circle c2) {
//        return Math.sqrt(Math.pow(c1.x - c2.x, 2) + Math.pow(c1.y - c2.y, 2)) < (c1.radius + c2.radius);
//    }
//}
//
//
//class Circle {
//    double x;
//    double y;
//    double radius;
//    double weight;
//    double vx;
//    double vy;
//    int r = 50;
//    int g = 50;
//    int b = 50;
//}