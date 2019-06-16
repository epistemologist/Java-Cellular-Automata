/**
 * Demonstration class for FastLife class
 * Code by: Husnain Raza
 */
public class FastLifeDemo
{
    public static void rPentomino(int size) throws Exception {
        FastLife.genImageSequence("http://www.conwaylife.com/w/index.php?title=R-pentomino", size/2, size/2, new int[]{0,size}, new int[]{0,size}, 10000);
    }
    
    public static void pattern47575M() throws Exception {
        int size = 1000;
        FastLife.genImageSequence("http://www.conwaylife.com/wiki/47575M", size/2, size/2, new int[]{0,size}, new int[]{0,size}, 60000);
    }
}
