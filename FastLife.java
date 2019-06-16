import java.util.*;
/**
 * Faster implementation of Conway's Game of Life
 * Heavily inspired by Peter Norvig's writeup on the Game of Life (basically translated from Python to Java)
 * Code by: Husnain Raza
 */
public class FastLife
{
    // taken from https://stackoverflow.com/a/32348537
    public class Counter<T> {
        final Map<T, Integer> counts = new HashMap<>();

        public void add(T t) {
            counts.merge(t, 1, Integer::sum);
        }

        public int count(T t) {
            return counts.getOrDefault(t, 0);
        }

        @Override
        public String toString() {
            return counts.toString();
        }
    }

    public class Cell {
        public int x;
        public int y;

        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "("+x+","+y+")";
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Cell) {
                Cell c = (Cell) o;
                return (this.x == c.x) && (this.y == c.y);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return 11801*x+31337*y;
        }

        public Cell[] neighbors() {
            return new Cell[]{
                new Cell(x,y+1),
                new Cell(x,y-1),
                new Cell(x+1,y),
                new Cell(x-1,y),
                new Cell(x+1,y-1),
                new Cell(x+1,y+1),
                new Cell(x-1,y-1),
                new Cell(x-1,y+1)
            };
        }
    }

    public Counter<Cell> neighborCounts(Set<Cell> world) {
        Counter<Cell> out = new Counter<>();
        for (Cell cell:world) {
            for (Cell neighbor:cell.neighbors()) {
                out.add(neighbor);
            }
        }
        return out;
    }

    public Set<Cell> nextGeneration(Set<Cell> world) {
        Counter cellCounts = neighborCounts(world);
        Set<Cell> possibleCells = cellCounts.counts.keySet();
        Set<Cell> out = new HashSet<>();
        for (Cell cell:possibleCells) {
            if (cellCounts.count(cell) == 3 || (cellCounts.count(cell) == 2 && world.contains(cell))) {
                out.add(cell);
            }
        }
        return out;
    }

    public String printWorld(Set<Cell> world, int xMin, int xMax, int yMin, int yMax) {
        String out = "";
        for (int y = yMin; y < yMax; y++) {
            String row = "";
            for (int x = xMin; x < xMax; x++) {
                if (world.contains(new Cell(x,y))) {
                    row += "#";
                } else {
                    row += ".";
                }
            }
            out += row+"\n";
        }
        return out;
    }

    public int[][] generatePixels(Set<Cell> world, int xMin, int xMax, int yMin, int yMax) {
        ArrayList<ArrayList<Integer>> out = new ArrayList<>();
        for (int y = yMin; y < yMax; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = xMin; x < xMax; x++) {
                if (world.contains(new Cell(x,y))) {
                    row.add(Colour.intFromRGB(255,255,255));
                } else {
                    row.add(Colour.intFromRGB(0,0,0));
                }
            }
            out.add(row);
        }
        return Array.arrayListToArray(out);
    }

    public int[][] generateArray(Set<Cell> world, int xMin, int xMax, int yMin, int yMax, int onValue) {
        ArrayList<ArrayList<Integer>> out = new ArrayList<>();
        for (int y = yMin; y < yMax; y++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int x = xMin; x < xMax; x++) {
                if (world.contains(new Cell(x,y))) {
                    row.add(onValue);
                } else {
                    row.add(0);
                }
            }
            out.add(row);
        }
        return Array.arrayListToArray(out);
    }

    public Set<Cell> cellFrom2Darray(int[][] in, int xOffset, int yOffset) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                if (in[i][j] != 0) {
                    if (i < minX) {
                        minX = i;
                    }
                    if (j < minY) {
                        minY = j;
                    }
                }
            }
        }
        Set<Cell> out = new HashSet<>();
        for (int i = 0; i < in.length; i++) {
            for (int j = 0; j < in[0].length; j++) {
                if (in[i][j] != 0) {
                    out.add(new Cell(i-minX+xOffset,j-minY+yOffset));
                }
            }
        }
        return out;
    }

    public static void printPattern(String url, int xOffset, int yOffset, int[] xRange, int[] yRange, int iterations) throws Exception {
        FastLife f = new FastLife();
        Set<Cell> world = f.cellFrom2Darray(Array.transpose(ImportLifePattern.getPatternFromURL(url)),xOffset, yOffset);
        for (int i = 0; i < iterations; i++) {
            System.out.println(f.printWorld(world, xRange[0], xRange[1], yRange[0], yRange[1]));
            System.out.println();
            world = f.nextGeneration(world);
        }
    }

    public static void genImageSequence(String url, int xOffset, int yOffset, int[] xRange, int[] yRange, int iterations) throws Exception {
        FastLife f = new FastLife();
        Set<Cell> world = f.cellFrom2Darray(Array.transpose(ImportLifePattern.getPatternFromURL(url)),xOffset, yOffset);
        for (int i = 0; i < iterations; i++) {
            System.out.println("iteration "+i);
            int[][] pixels = f.generatePixels(world, xRange[0], xRange[1], yRange[0], yRange[1]);
            Image.saveImageFromArr(i+".png",pixels);
            world = f.nextGeneration(world);
        }
    }
}
