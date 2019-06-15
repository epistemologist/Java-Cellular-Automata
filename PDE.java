import java.util.*;
import java.lang.*;

/**
 * Class that implements the "PDE Automata"
 * Code by: Husnain Raza
 */
public class PDE
{
    public static final int LENGTH = 200;
    public static final int WIDTH = 200;
    public static final int PERIOD = 256;
    public int[][] board;
    public ArrayList<int[][]> history;
    public PDE() {
        board = new int[LENGTH][WIDTH];
        history = new ArrayList<>();
    }

    public void setInitState(int[][] initState) {
        board = initState;
        history.add(board);
    }

    public void setInitStateExample() {
        int centerX = LENGTH/2;
        int centerY = WIDTH/2;
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                double dist = Math.sqrt(Math.pow(i-centerX,2)+Math.pow(j-centerY,2));
                board[i][j] = (dist<75?250:0);
                if (i == 0 || i == LENGTH - 1 || j == 0 || j == WIDTH - 1) {
                    board[i][j] = 0;
                }
            }
        }
    }

    public int getNeighborsAverage(int x, int y) {
        int[] out = new int[4];
        int[][] neighbors = new int[][]{
                {x ,y+1},
                {x-1,y  }         ,{x+1,y  }
            ,{x ,y-1}};
        for (int i = 0; i < 4; i++) {
            int currentX = neighbors[i][0];
            int currentY = neighbors[i][1];
            if (0 <= currentX && currentX < LENGTH && 0 <= currentY && currentY < WIDTH) {
                out[i] = board[currentX][currentY];
            } else {
                out[i] = 0;
            }
        }
        return (int) Math.round((out[0]+out[1]+out[2]+out[3])*0.25);
    }

    public void next() {
        int[][] nextBoard = new int[LENGTH][WIDTH];
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || i == LENGTH - 1 || j == 0 || j == WIDTH - 1) {
                    nextBoard[i][j] = board[i][j];
                } else {
                    nextBoard[i][j] = (1 + getNeighborsAverage(i,j)) % PERIOD;
                }
            }
        }
        history.add(nextBoard);
        board = nextBoard;
    }

    public int[][] convertArrToIm(int[][] in) {
        int[][] out = new int[in.length][in[0].length];
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                // out[i][j] = Color.hot(in[i][j]);
                out[i][j] = Colour.prism(in[i][j],PERIOD);
            }
        }
        return out;
    }

    public void genImageSequence() {
        for (int i = 0; i < history.size(); i++) {
            System.out.println("generation "+i+"/"+history.size());
            Image.saveImageFromArr(i+".png", convertArrToIm(history.get(i)));
        }
    }
}
