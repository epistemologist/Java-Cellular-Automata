/**
 * Class that implements a Cyclic Cellular Automata
 * Code by: Husnain Raza
 */
import java.util.ArrayList;
public class CyclicCellularAutomata
{
    public int[][] board;
    public int length;
    public int width;
    public int maxStates;
    public int threshold;
    public int range;
    public char neighborhoodType;
    public ArrayList<int[][]> history;
    public CyclicCellularAutomata(int length, int width, int range, int threshold, int maxStates, char neighborhood) {
        this.length = length;
        this.width = width;
        this.board = new int[length][width];
        this.maxStates = maxStates;
        this.threshold = threshold;
        this.range = range;
        this.neighborhoodType = neighborhoodType;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j< width; j++) {
                board[i][j] = (int) (Math.random() * maxStates);
            }
        }
        history = new ArrayList<>();
        history.add(board);
    }                      

    public void next() {
        int[][] tempBoard = new int[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                int currentCell = board[i][j];
                int neighborCount;
                if (neighborhoodType == 'v') {
                    neighborCount = Array.count(Array.vonNeumannNeighborhood(board,i,j,range),(currentCell + 1) % maxStates);
                } else {
                    neighborCount = Array.count(Array.mooreNeighborhood(board,i,j,range),(currentCell + 1) % maxStates);
                }
                if (neighborCount >= threshold) {
                    currentCell++;
                    currentCell %= maxStates;
                }
                tempBoard[i][j] = currentCell;
            }
        }
        board = tempBoard;
        history.add(board);
    }

    public int[][] convertArrToIm(int[][] in, int[] colormap) {
        int[][] out = new int[in.length][in[0].length];
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                out[i][j] = colormap[in[i][j]];
            }
        }
        return out;
    }

    public void genImageSequence(int[] colormap) {
        for (int i = 0; i < history.size(); i++) {
            System.out.println("generation "+i+"/"+history.size());
            Image.saveImageFromArr(i+".png", convertArrToIm(history.get(i),colormap));
        }
    }
}
