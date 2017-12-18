package aoc.seventeen;

public class Day3 {

    private static enum Direction {
        RIGHT, UP, LEFT, DOWN
    }

    public static void main(final String[] args) {
        int right = 0;
        int up = 0;

        int directionCount = 1;
        int stepsLeft = directionCount;

        int input = 347991;
        int squareSize = (int) Math.ceil(Math.sqrt(input)) + 1;
        int center = squareSize / 2;
        int[][] square = new int[squareSize][squareSize];

        System.out.println(squareSize + " " + center);

        Direction direction = Direction.RIGHT;
        for (int i = 1; i < input; i++) {
            int neigbourSum = getNeigbourSum(square, center + up, center + right);
            if (neigbourSum > input) {
                System.out.println(neigbourSum);
                return;
            }
            square[center + up][center + right] = neigbourSum;
            switch (direction) {
                case RIGHT:
                    right++;
                    break;
                case LEFT:
                    right--;
                    break;
                case UP:
                    up++;
                    break;
                case DOWN:
                    up--;
                    break;
            }
            stepsLeft--;
            if (stepsLeft == 0) {
                if (Direction.UP.equals(direction) || Direction.DOWN.equals(direction)) {
                    directionCount++;
                }
                direction = Direction.values()[(direction.ordinal() + 1) % 4];
                stepsLeft = directionCount;
            }
        }
        System.out.println(up);
        System.out.println(right);
        System.out.println(Math.abs(up) + Math.abs(right));

    }

    private static int getNeigbourSum(final int[][] square, final int x, final int y) {
        int sum = 0;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (!(i == x && j == y)) {
                    sum += square[i][j];
                }
            }
        }
        if (sum == 0) {
            sum = 1;
        }
        System.out.println(x + "," + y + ": " + sum);
        return sum;
    }

}
