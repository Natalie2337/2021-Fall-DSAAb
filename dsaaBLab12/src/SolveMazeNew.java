public class SolveMazeNew {
    private static Graph buildGraph(char[][] maze){//运用图类
        int height = maze.length;
        int width = maze[0].length;//求一个二维数组的宽度，就是它每行.length

        Graph graph = new GraphAdjList(height*width);
        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                //为格子maze[h][w]添加edges

                if(maze[h][w]=='R'&& maze[h][w+1])


            }
            
        }

    }

}
