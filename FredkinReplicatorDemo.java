/**
 * Runner class that demonstrates implementation of Fredkin's replicator
 * Code by: Husnain Raza
 */
public class FredkinReplicatorDemo 
{
    public static void demo(int[][] pattern, int iterations) throws Exception {
        int[][] newPattern = new int[pattern.length][pattern[0].length];
        for (int i = 0; i < pattern.length; i++) {
            int[] temp = new int[2];
            temp[0] = pattern[i][0] + 30;
            temp[1] = pattern[i][1] + 30;
            newPattern[i] = temp;
        }
        FredkinReplicator board = new FredkinReplicator(60,60,newPattern);
        board.printHistoryDelay(iterations,1000);
    }

    public static void genImageSeq(int[][] pattern, int iterations) {
        int SIZE = 400;
        int[][] newPattern = new int[pattern.length][pattern[0].length];
        for (int i = 0; i < pattern.length; i++) {
            int[] temp = new int[2];
            temp[0] = pattern[i][0] + SIZE/2;
            temp[1] = pattern[i][1] + SIZE/2;
            newPattern[i] = temp;
        }
        FredkinReplicator board = new FredkinReplicator(SIZE,SIZE,newPattern);
        board.genImageSequence(iterations);
    }

    public static void main(String[] args) throws Exception {
        genImageSeq(new int[][] {{0,0},{0,1},{1,0},{1,1}}, 300);
    }
}
