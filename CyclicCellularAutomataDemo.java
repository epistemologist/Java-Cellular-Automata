/**
 * Runner class that demonstrates Cyclic Cellular Automata
 */
public class CyclicCellularAutomataDemo
{
    public static void cyclicSpirals(String[] args) {
        CyclicCellularAutomata board = new CyclicCellularAutomata(400,400,3,5,8,'m');
        for (int i = 0; i < 1000; i++) {
            System.out.println(i+"/"+1000);
            board.next();
        }
        board.genImageSequence(Colour.genGradientMap(14,0,0,255,0,255,255));
    }

    public static void main(String[] args) {
        CyclicCellularAutomata board = new CyclicCellularAutomata(400,400,2,5,2,'m');
        for (int i = 0; i < 1000; i++) {
            System.out.println(i+"/"+1000);
            board.next();
        }
        board.genImageSequence(Colour.genGradientMap(6,255,0,0,255,255,0));
    }
}
