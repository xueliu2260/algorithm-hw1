import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private final double[] result;
    private final double mean;
    private final double stddev;
    private static final double pi = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        result = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {

                int random = StdRandom.uniform(n * n);
                int row = random / n;
                int col = random - row * n;
                while (percolation.isOpen(row + 1, col + 1)) {
                    random = StdRandom.uniform(n * n);
                    row = random / n;
                    col = random - row * n;
                }

                percolation.open(row + 1, col + 1);
            }
            result[i] = (double) percolation.numberOfOpenSites() / (n * n);

        }
        mean = StdStats.mean(result);
        stddev = StdStats.stddev(result);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        double value = mean - pi * stddev / Math.sqrt(result.length);
        return value;
    }

    public double confidenceHi() {
        double value = mean + pi * stddev / Math.sqrt(result.length);
        return value;
    }

    public static void main(String[] args) {
    }

}