package aoc.seventeen;

import java.util.ArrayList;
import java.util.List;

public class Day14 {

    private static final class Coordinate {
        private final int x, y;

        public Coordinate(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Coordinate [x=" + x + ", y=" + y + "]";
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Coordinate other = (Coordinate) obj;
            if (x != other.x) {
                return false;
            }
            if (y != other.y) {
                return false;
            }
            return true;
        }

    }

    public static void main(final String[] args) {
        // String input = "flqrgnkx";
        String input = "ffayrhll";
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < 128; i++) {
            coordinates.addAll(getUsedCoordinates(input + "-" + i, i));
        }
        System.out.println(coordinates.size());

        int groupCount = countGroups(coordinates);
        System.out.println(groupCount);
    }

    private static List<Coordinate> getUsedCoordinates(final String input, final int row) {
        List<Integer> lengths = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            lengths.add((int) input.charAt(i));
        }
        String knotHash = Day10_part2.generateKnotHash(lengths);

        List<Coordinate> coordinates = new ArrayList<>();
        StringBuilder rowBinaryBuilder = new StringBuilder(128);
        for (int i = 0; i < knotHash.length(); i++) {
            int numericValue = Character.getNumericValue(knotHash.charAt(i));
            String rowBinary = String.format("%04d", Integer.valueOf(Integer.toBinaryString(numericValue)));
            rowBinaryBuilder.append(rowBinary);
        }
        String rowBinary = rowBinaryBuilder.toString();
        for (int j = 0; j < rowBinary.length(); j++) {
            char c = rowBinary.charAt(j);
            if (c == '1') {
                coordinates.add(new Coordinate(row, j));
            }
        }
        return coordinates;
    }

    private static int countGroups(final List<Coordinate> coordinates) {
        int count = 0;
        while (!coordinates.isEmpty()) {
            count++;
            removedLinked(coordinates.get(0), coordinates);
        }
        return count;
    }

    private static void removedLinked(final Coordinate seed, final List<Coordinate> coordinates) {
        if (coordinates.remove(seed)) {
            removedLinked(new Coordinate(seed.x + 1, seed.y), coordinates);
            removedLinked(new Coordinate(seed.x, seed.y + 1), coordinates);
            removedLinked(new Coordinate(seed.x, seed.y - 1), coordinates);
            removedLinked(new Coordinate(seed.x - 1, seed.y), coordinates);
        }
    }
}
