package aoc.seventeen;

import aoc.util.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Day14 {

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
            removedLinked(new Coordinate(seed.getX() + 1, seed.getY()), coordinates);
            removedLinked(new Coordinate(seed.getX(), seed.getY() + 1), coordinates);
            removedLinked(new Coordinate(seed.getX(), seed.getY() - 1), coordinates);
            removedLinked(new Coordinate(seed.getX() - 1, seed.getY()), coordinates);
        }
    }
}
