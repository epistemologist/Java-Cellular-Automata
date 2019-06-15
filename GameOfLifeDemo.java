import java.io.*;
import java.util.*;

/**
 * Runner class to demonstrate implementation of Conway's Game of Life
 * Code by: Husnain Raza
 */
public class GameOfLifeDemo
{
    public static void block(int iterations) {
        int[][] pattern = {{1,1},{1,2},{2,1},{2,2}};
        GameOfLife board = new GameOfLife(4,4,pattern);
        board.printHistory(iterations);
    }

    public static void blinker(int iterations) {
        int[][] pattern = {{1,2},{2,2},{3,2}};
        GameOfLife board = new GameOfLife(5,5,pattern);
        board.printHistory(iterations);
    }

    public static void beacon(int iterations) {
        int[][] pattern = {{1,1},{1,2},{2,1},{2,2},{3,3},{3,4},{4,3},{4,4}};
        GameOfLife board = new GameOfLife(6,6,pattern);
        board.printHistory(iterations);
    }

    public static void glider(int iterations) {
        int[][] pattern = {{1,2},{2,3},{3,1},{3,2},{3,3}};
        GameOfLife board = new GameOfLife(20,20,pattern);
        board.printHistory(iterations);
    }

    public static int[][] patternFromString(String s) {
        String[] rows = s.split("-");
        System.out.println(rows.length);
        ArrayList<int[]> out = new ArrayList<>();
        for (String i:rows) {
            int[] temp = new int[i.length()];
            for (int j = 0; j < i.length(); j++) {
                temp[j] = (i.charAt(j)=='O') ? 1 : 0;
            }
            out.add(temp);
        }
        int[][] outArray = new int[rows[0].length()][rows.length];
        for (int i = 0; i < rows[0].length(); i++) {
            for (int j = 0; j < rows.length; j++) {
                outArray[i][j] = out.get(j)[i];
            }
        }
        return outArray;
    }

    public static void testPattern(String p, int iterations, int delay) throws InterruptedException {
        int[][] pattern = patternFromString(p);
        GameOfLife board = new GameOfLife(pattern);
        board.printHistoryDelay(iterations, delay);
    }

    public static void printPatternFromURL(String url, int iterations, int delay) throws Exception {
        int[][] pattern = ImportLifePattern.getPatternFromURL(url);
        GameOfLife board = new GameOfLife(pattern);
        board.printHistoryDelay(iterations,delay);
    }
    
    public static void printPatternFromURL(String url) throws Exception {
        int[][] pattern = ImportLifePattern.getPatternFromURL(url);
        GameOfLife board = new GameOfLife(pattern);
        board.printHistoryDelay(400,500);
    }
    
    public static void imageSeqFromURL(String url, int iterations) throws Exception {
        int[][] pattern = ImportLifePattern.getPatternFromURL(url);
        GameOfLife board = new GameOfLife(pattern);
        board.genImageSequence(iterations);
    }
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter URL: ");
        String url = in.nextLine();
        imageSeqFromURL(url, 400);
    }

}
