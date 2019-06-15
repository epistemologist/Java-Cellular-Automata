/**
 * Runner class to demonstrate the "PDE Automata"
 * Code by: Husnain Raza
 */
public class PDEDemo
{
    public static void main(String[] args) {
        PDE board = new PDE();
        for (int i = 0; i < 4000; i++) {
            System.out.println(i+"/"+4000);
            board.next();
        }
        board.genImageSequence();
    }
}
