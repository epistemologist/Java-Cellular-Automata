import java.util.Random;
/**
 * Runner class for ReactionDiffusion class
 * Code by: Husnain Raza
 */
public class ReactionDiffusionRunner
{
    public double F;
    public double k;
    public ReactionDiffusion board;

    public ReactionDiffusionRunner(double F, double k) {
        this.board = new ReactionDiffusion(200,200);
        this.F = F;
        this.k = k;
        Random r = new Random();
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                double randomScaleFactor = 0.99 + r.nextDouble()/50;
                if (i>90 && i<110 && j>90 && j<110) {
                    this.board.board[i][j] = board.new Cell(0.5*randomScaleFactor,0.25*randomScaleFactor);
                } else {
                    this.board.board[i][j] = board.new Cell(randomScaleFactor,0);
                }
            }
        }
    }
    
    public void run() {
        int filename = 0;
        for (int i = 0; i < 200000; i++) {
            System.out.println("iteration "+i);
            board.nextNoSave();
            if (i%10==0) {
                board.save(filename+"");
                filename++;
            }
        }
        board.save();
    }
}
