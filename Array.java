/**
 * Utility class that provides functions that work with 2D arrays.
 */
import java.util.List;
import java.util.ArrayList;
public class Array
{
    public static int mod(int x, int n) {
        int out = x%n;
        if (out < 0) {
            out += n;
        }
        return out;
    }

    public static int[][] mooreNeighborhood(int[][] board, int x0, int y0, int r) {
        int[][] out = new int[2*r+1][2*r+1];
        for (int i = -r; i <= r; i++) {
            for (int j = -r; j <= r; j++) {
                out[i+r][j+r] = board[mod(x0+i,board.length)][mod(y0+j,board[0].length)];
            }
        }
        return out;
    }

    public static int[][] vonNeumannNeighborhood(int[][] board, int x0, int y0, int r) {
        int[][] out = new int[2*r+1][2*r+1];
        for (int i = -r; i <= r; i++) {
            for (int j = -r; j <= r; j++) {
                if (Math.abs(i) + Math.abs(j) <= r) {
                    out[i+r][j+r] = board[mod(x0+i,board.length)][mod(y0+j,board[0].length)];
                } else {
                    out[i+r][j+r] = -1;
                }
            }
        }
        return out;
    }

    public static Integer[] flattenArr(int[][] arr) {
        List<Integer> out = new ArrayList<>();
        for (int[] i:arr) {
            for (int j:i) {
                if (j != -1) {
                    out.add(j);
                }
            }
        }
        return out.toArray(new Integer[out.size()]);
    }

    public static int count(int[] arr, int value) {
        int out = 0;
        for (int i:arr) {
            if (i == value) {
                out++;
            }
        }
        return out;
    }

    public static int count(int[][] arr, int value) {
        int out = 0;
        for (int[] i:arr) {
            for (int j:i) {
                if (j == value) {
                    out++;
                }
            }
        }
        return out;
    }
    
    public static int[][] transpose(int[][] in) {
        int[][] out = new int[in[0].length][in.length];
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                out[j][i] = in[i][j];
            }
        }
        return out;
    }
    public static void printArr(int[][] arr) {
        for (int[] i:arr) {
            for (int j:i) {
                System.out.print(j+",");
            }
            System.out.println();
        }
    }

    public static int max(int[][] arr) {
        int out = arr[0][0];
        for (int[] i:arr) {
            for (int j:i) {
                if (out < j) {
                    out = j;
                }
            }
        }
        return out;
    }

    public static double max(double[][] arr) {
        double out = arr[0][0];
        for (double[] i:arr) {
            for (double j:i) {
                if (out < j) {
                    out = j;
                }
            }
        }
        return out;
    }

    public static int min(int[][] arr) {
        int out = arr[0][0];
        for (int[] i:arr) {
            for (int j:i) {
                if (out > j) {
                    out = j;
                }
            }
        }
        return out;
    }

    public static double min(double[][] arr) {
        double out = arr[0][0];
        for (double[] i:arr) {
            for (double j:i) {
                if (out > j) {
                    out = j;
                }
            }
        }
        return out;
    }
    
    public static double[][] normalize(double[][] in) {
        double max = max(in);
        double[][] out = new double[in.length][in[0].length];
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                out[i][j] = in[i][j] / max;
            }
        }
        return out;
    }
    
    public static int[][] arrayListToArray(ArrayList<ArrayList<Integer>> in) {
        int w = in.get(0).size();
        int h = in.size();
        int[][] out = new int[h][w];
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                out[j][i] = in.get(j).get(i);
            }
        }
        return out;
    }
   
}
