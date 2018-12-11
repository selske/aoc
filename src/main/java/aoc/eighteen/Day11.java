package aoc.eighteen;

public class Day11 {

    public static void main(final String[] args) {
        long before = System.currentTimeMillis();
        System.out.println("part1: " + part1());
        System.out.println("part2: " + part2());
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

    private static String part1() {
        int size = 300;
        int[][] grid = initGridScores(size);

        int bestScore = Integer.MIN_VALUE;
        int bestX = -1;
        int bestY = -1;
        for (int row = 0; row < size - 2; row++) {
            for (int col = 0; col < size - 2; col++) {
                int gridScore = calculateGridScore(grid, row, col, 3);
                if (bestScore < gridScore) {
                    bestX = col + 1;
                    bestY = row + 1;
                    bestScore = gridScore;
                }
            }
        }
        return bestX + "," + bestY;
    }

    private static String part2() {
        int size = 300;
        int[][] grid = initGridScores(size);

        int bestScore = Integer.MIN_VALUE;
        int bestX = -1;
        int bestY = -1;
        int bestBlockSize = -1;
        for (int row = 0; row < size - 2; row++) {
            for (int col = 0; col < size - 2; col++) {
                for (int blockSize = 1; blockSize < size - Integer.max(col, row); blockSize++) {
                    int gridScore = calculateGridScore(grid, row, col, blockSize);
                    if (bestScore < gridScore) {
                        bestX = col + 1;
                        bestY = row + 1;
                        bestBlockSize = blockSize;
                        bestScore = gridScore;
                    }
                }
            }
        }
        return bestX + "," + bestY + "," + bestBlockSize;
    }

    private static int[][] initGridScores(int size) {
        int[][] grid = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = calculateScore(col + 1, row + 1, 9995);
            }
        }
        return grid;
    }

    private static int calculateGridScore(int[][] grid, int row, int col, int blockSize) {
        int score = 0;

        for (int r = row; r < row + blockSize; r++) {
            for (int c = col; c < col + blockSize; c++) {
                score += grid[r][c];
            }
        }

        return score;
    }

    private static int calculateScore(int x, int y, int serialNumber) {
        int rackId = x + 10;

        long power = rackId * y;
        power += serialNumber;
        power *= rackId;

        power %= 1_000;
        power = (power - power % 100) / 100;
        power -= 5;

        return (int) power;
    }

}
