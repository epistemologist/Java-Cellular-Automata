import java.util.*;
import java.lang.*;

/**
 * Class that implements Conway's Game of Life
 * Code by: Husnain Raza
 */
public class GameOfLife {
    public TwoDimensional board;
    public GameOfLife(int length, int width, int[][] coords) {
        board = new TwoDimensional(TwoDimensional.generateRules(
                new ArrayList<>(Arrays.asList(3)), 
                new ArrayList<>(Arrays.asList(2,3))),length,width);
        board.setInitStatePairs(coords);
    }

    public GameOfLife(int[][] initState) {
        int length = initState.length;
        int width = initState[0].length;
        board = new TwoDimensional(TwoDimensional.generateRules(
                new ArrayList<>(Arrays.asList(3)), 
                new ArrayList<>(Arrays.asList(2,3))),length,width);
        board.setInitState(initState);
    }

    public void printHistory(int iterations) {
        for (int i = 0; i < iterations; i++) {
            board.next();
        }
        board.printHistory();
    }

    public void printHistoryDelay(int iterations, int delay) throws InterruptedException{
        for (int i = 0; i < iterations; i++) {
            board.next();
        }
        board.printHistoryDelay(delay);
    }

    public void genImageSequence(int iterations) {
        for (int i = 0; i < iterations; i++) {
            System.out.println(i+"/"+iterations);
            board.next();
        }
        board.genImageSequence();
    }
}
