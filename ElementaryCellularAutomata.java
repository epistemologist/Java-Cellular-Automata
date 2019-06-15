import java.util.*;
import java.lang.*;
/**
 * Class that emulates Stephen Wolfram's Elementary Cellular Automata
 * Code by: Husnain Raza
 */
public class ElementaryCellularAutomata
{
    public static final int LENGTH = 100;
    public static final String ON = "#";
    public static final String OFF = " ";
    public String state;
    public ArrayList<String> history;
    public int ruleNumber;
    public String leftNeighbor;
    public String rightNeighbor;

    public ElementaryCellularAutomata(int setRuleNumber) {
        state = "";
        for (int i = 0; i < LENGTH; i++) {
            if (i == LENGTH/2) {
                state += "1";
            } else {
                state += "0";
            }
        }
        ruleNumber = setRuleNumber;
        history = new ArrayList<String>();
        history.add(state);
        leftNeighbor = "0";
        rightNeighbor = "0";
    }

    public int getRuleNumber() {
        return ruleNumber;
    }

    public void setRuleNumber(int setRuleNum) {
        this.ruleNumber = setRuleNum;
    }

    public void next() {
        String tempState = leftNeighbor + state + rightNeighbor;
        String nextState = "";
        boolean[] ruleSet = new boolean[8];
        for (int i = 0; i < 8; i++) {
            if ((ruleNumber & (1 << i)) >= 1) {
                ruleSet[i] = true;
            }
        }
        for (int i = 0; i < tempState.length() - 2; i++) {
            int temp = Integer.parseInt(tempState.substring(i,i+3),2);
            if (ruleSet[temp]) {
                nextState += "1";
            } else {
                nextState += "0";
            }
        }
        state = nextState;
        history.add(state);
    }

    public void displayHistory() {
        for (String s: history) {
            s = s.replaceAll("0",OFF).replaceAll("1",ON);
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        ElementaryCellularAutomata test = new ElementaryCellularAutomata(110);
        for (int i = 0; i < 100; i++) {
            test.next();
        }
        test.displayHistory();
    }
}
