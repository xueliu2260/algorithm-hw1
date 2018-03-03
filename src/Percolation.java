import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private boolean[][] map;
    private final WeightedQuickUnionUF grid;
    private int countOpenSite = 0;
    private boolean percolates = false;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        map = new boolean[n][n];
        grid = new WeightedQuickUnionUF(n * n);

    }

    public void open(int row, int col) {

        if (row <= 0 || col <= 0 || row > map.length || col > map.length) {
            throw new IllegalArgumentException();

        }
        if (map.length == 1) percolates = true;
        row = row - 1;
        col = col - 1;


        int n = map.length;
        if (!map[row][col]) {
            countOpenSite += 1;
            map[row][col] = true;
            int numInGrid = row * n + col;

            if (col - 1 >= 0 && map[row][col - 1]) {
                int numInGridLeft = row * n + col - 1;
                grid.union(numInGrid, numInGridLeft);
            }

            if (col + 1 < n && map[row][col + 1]) {
                int numInGridRight = row * n + col + 1;
                grid.union(numInGrid, numInGridRight);
            }

            if (row - 1 >= 0 && map[row - 1][col]) {
                int numInGridTop = (row - 1) * n + col;
                grid.union(numInGrid, numInGridTop);
            }

            if (row + 1 < n && map[row + 1][col]) {
                int numInGridBottom = (row + 1) * n + col;
                grid.union(numInGrid, numInGridBottom);
            }

            if (isFull(row + 1, col + 1)) {
                for (int i = 0; i < map.length; i++) {
                    if (map[map.length-1][i] && grid.connected((map.length-1) * map.length + i, row * map.length + col)) {
                        percolates = true;
                        return;
                    }
                }
            }
        }


    }

    public boolean isOpen(int row, int col) {

        if (row <= 0 || col <= 0 || row > map.length || col > map.length) {
            throw new IllegalArgumentException();
        }
        row = row - 1;
        col = col - 1;
        return map[row][col];

    }

    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0 || row > map.length || col > map.length) {
            throw new IllegalArgumentException();
        }
        if (!map[row-1][col-1]) return false;
        row = row - 1;
        col = col - 1;
        int numInGrid = row * map.length + col;
        for (int i = 0; i < map.length; i++) {
            if (map[0][i] && grid.connected(numInGrid, i)) {
                if (row == map.length - 1) {
                    percolates = true;
                }
                return true;
            }
        }
        return false;
    }


    public int numberOfOpenSites() {
        return countOpenSite;
    }

    public boolean percolates() {
        return percolates;
    }

    public static void main(String[] args) {
    }

}