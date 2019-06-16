import java.util.*;
import java.lang.*;
import java.math.*;

/**
 * Main class that emulates any 2D cellular automata
 * Code by: Husnain Raza
 */
public class TwoDimensional
{
    public static int LENGTH;
    public static int WIDTH;
    public static final String ON = "X";
    public static final String OFF = " ";
    public int[] ruleSet;
    public int[][] currentState;
    public ArrayList<int[][]> history;

    public TwoDimensional(int[] setRules, int setLength, int setWidth) {
        ruleSet = setRules;
        LENGTH = setLength;
        WIDTH = setWidth;
        currentState = new int[LENGTH][WIDTH];
        history = new ArrayList<int[][]>();
    }

    public void setInitState(int[][] initState) {
        currentState = initState;
        history.add(currentState);
    }

    public void setInitStatePairs(int[][] pairs) {
        for (int[] i:pairs) {
            currentState[i[0]][i[1]] = 1;
        }
        history.add(currentState);
    }

    public int[] getNeighbors(int x, int y) {
        int[] out = new int[9];
        int[][] neighbors = new int[][]{
                {x-1,y+1},{x ,y+1},{x+1,y+1},
                {x-1,y  },{x ,y  },{x+1,y  },
                {x-1,y-1},{x ,y-1},{x+1,y-1}};
        for (int i = 0; i < 9; i++) {
            int currentX = neighbors[i][0];
            int currentY = neighbors[i][1];
            if (0 <= currentX && currentX < LENGTH && 0 <= currentY && currentY < WIDTH) {
                out[i] = currentState[currentX][currentY];
            } else {
                out[i] = 0;
            }
        }
        return out;
    }

    public static int arrToInt(int[] arr) {
        String temp = "";
        for (int i:arr) {
            temp += i + "";
        }
        return Integer.parseInt(temp,2);
    }

    public void next() {
        int[][] nextState = new int[LENGTH][WIDTH];
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                int[] currentNeighbors = getNeighbors(i,j);
                int neighborsInt = arrToInt(currentNeighbors);
                nextState[i][j] = ruleSet[neighborsInt];
            }
        }
        currentState = nextState;
        history.add(currentState);
    }

    public static int[] toBitArray(BigInteger b) {
        String bin = b.toString(2);
        while (bin.length() < 512) {
            bin = "0" + bin;
        }
        int[] out = new int[512];
        for (int i = 0; i < 512; i++) {
            if (bin.charAt(i) == '1') {
                out[i] = 1;
            }
        }
        return out;
    }

    public void printArray(int[][] a) {
        String[][] b = new String[LENGTH][WIDTH];
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (a[i][j]==0) {
                    b[i][j] = OFF;
                } else {
                    b[i][j] = ON;
                }
            }
        }
        for (String[] i:b) {
            for (String j:i) {
                System.out.print(j);
            }
            System.out.println();
        }
    }

    public static String intFromBits(int[] bits) {
        String out = "";
        for (int i:bits) {
            out += i + "";
        }
        return (new BigInteger(out,2)).toString(10);
    }

    public static int numberOfBits(int n) {
        String s = Integer.toBinaryString(n);
        int out = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                out++;
            }
        }
        return out;
    }

    public static int[] generateRules(ArrayList<Integer> b, ArrayList<Integer> s) {
        int[] out = new int[512];
        for (int i = 0; i < 512; i++) {
            if (i == 200) {
                System.out.println();
            }
            int middleBit = i & 16;
            if (middleBit != 0) {
                middleBit = 1;
            }
            int otherBits = i & 495;
            int numBits = numberOfBits(otherBits);
            if (s.contains(numBits)) {
                out[i] = middleBit;
            }
            if (b.contains(numBits)) {
                out[i] = 1;
            }
        }
        return out;
    }

    public void printHistory() {
        for (int[][] i:history) {
            printArray(i);
            System.out.println();
        }
    }

    public void printHistoryDelay(int delay) throws InterruptedException {
        for (int[][] i:history) {
            printArray(i);
            System.out.println("------------------------------------------------");
            Thread.sleep(delay);
        }
    }

    public int[][] convertArrToIm(int[][] in) {
        int[][] out = new int[in.length][in[0].length];
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                if (in[i][j] != 0) {
                    out[i][j] = 0xFFFFFF;
                }
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

    public static void main(String[] args) {
        ArrayList<Integer> b = new ArrayList<>();
        ArrayList<Integer> s = new ArrayList<>();
        b.add(3);
        s.add(2); s.add(3);
        TwoDimensional test = new TwoDimensional(generateRules(b,s),6,6);
        test.setInitStatePairs(new int[][]{{1,1},{1,2},{2,1},{2,2},{3,3},{3,4},{4,3},{4,4}});
        for (int i = 0; i < 2; i++) {
            test.next();
        }
        test.printHistory();
    }
}