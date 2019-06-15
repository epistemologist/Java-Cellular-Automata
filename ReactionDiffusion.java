/**
 * Implementation of the Grey Scott model for Reaction Diffusion.
 * Inspired by https://thecodingtrain.com/CodingChallenges/013-reactiondiffusion-p5.html
 * 
 * Code: by Husnain Raza
 */
public class ReactionDiffusion
{
    public class Cell {
        public double a;
        public double b;
        Cell(double a, double b) {
            this.a = a;
            this.b = b;
        }

        int toRGB() {
            //int greyScaleFactor = (int) Math.floor(255*(a-b));
            //return Colour.intFromRGB(greyScaleFactor,greyScaleFactor,greyScaleFactor);
            return Colour.intFromRGB((int) Math.floor(255*a), (int) Math.floor(255*a), (int) Math.floor(255*a));
        }
    }

    public int length;
    public int width;
    public Cell[][] board;
    public int iteration;
    public final double D_A = 1.0;
    public final double D_B = 0.5;
    public final double f = 0.055;
    public final double k = 0.062;
    public final double dt = 1.0;

    public ReactionDiffusion(int length, int width) {
        this.length = length;
        this.width = width;
        board = new Cell[length][width];
        iteration = 0;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }

    public ReactionDiffusion() {
        this.length = 200;
        this.width = 200;
        board = new Cell[length][width];
        iteration = 0;
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                board[i][j] = new Cell(1,0);
            }
        }
        for (int i = 100; i < 110; i++) {
            for (int j = 100; j < 110; j++) {
                board[i][j].b = 1;
            }
        }
    }

    public void save() {
        int[][] image = new int[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                image[i][j] = board[i][j].toRGB();
            }
        }
        Image.saveImageFromArr(iteration+".png", image);
    }

    public void save(String filename) {
        int[][] image = new int[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                image[i][j] = board[i][j].toRGB();
            }
        }
        Image.saveImageFromArr(filename+".png", image);
    }

    public void next() {
        save();
        iteration++;
        Cell[][] newBoard = new Cell[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == length - 1 || j == 0 || j == width - 1) {
                    newBoard[i][j] = board[i][j];
                } else {
                    double A = board[i][j].a;
                    double B = board[i][j].b;
                    double nextA = A + dt*(D_A*laplaceA(i,j)-A*B*B+f*(1-A));
                    double nextB = B + dt*(D_B*laplaceB(i,j)+A*B*B-(k+f)*B);
                    newBoard[i][j] = new Cell(constrain(nextA,0,1), constrain(nextB,0,1));
                }
            }
        }
        board = newBoard;
    }

    public void nextNoSave() {
        iteration++;
        Cell[][] newBoard = new Cell[length][width];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == length - 1 || j == 0 || j == width - 1) {
                    newBoard[i][j] = board[i][j];
                } else {
                    double A = board[i][j].a;
                    double B = board[i][j].b;
                    double nextA = A + dt*(D_A*laplaceA(i,j)-A*B*B+f*(1-A));
                    double nextB = B + dt*(D_B*laplaceB(i,j)+A*B*B-(k+f)*B);
                    newBoard[i][j] = new Cell(constrain(nextA,0,1), constrain(nextB,0,1));
                }
            }
        }
        board = newBoard;
    }

    public static int constrain(int value, int min, int max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    public static double constrain(double value, double min, double max) {
        if (value < min) {
            return min;
        } else if (value > max) {
            return max;
        } else {
            return value;
        }
    }

    public double laplaceA(int x, int y) {
        double sum = 0;
        sum += board[x][y].a * -1;
        sum += board[x][y+1].a * 0.2;
        sum += board[x][y-1].a * 0.2;
        sum += board[x-1][y].a * 0.2;
        sum += board[x+1][y].a * 0.2;
        sum += board[x+1][y+1].a * 0.05;
        sum += board[x+1][y-1].a * 0.05;
        sum += board[x-1][y+1].a * 0.05;
        sum += board[x-1][y-1].a * 0.05;
        return sum;
    }

    public double laplaceB(int x, int y) {
        double sum = 0;
        sum += board[x][y].b * -1;
        sum += board[x][y+1].b * 0.2;
        sum += board[x][y-1].b * 0.2;
        sum += board[x-1][y].b * 0.2;
        sum += board[x+1][y].b * 0.2;
        sum += board[x+1][y+1].b * 0.05;
        sum += board[x+1][y-1].b * 0.05;
        sum += board[x-1][y+1].b * 0.05;
        sum += board[x-1][y-1].b * 0.05;
        return sum;
    }
}
