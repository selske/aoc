package aoc.seventeen;

import java.util.*;
import java.util.stream.Collectors;

public class Day6 {
    private static final String INPUT = "14  0   15  12  11  11  3   5   1   6   8   4   9   1   8   4";

    public static void main(final String[] args) {
        List<List<Integer>> previousCombinations = new ArrayList<>();
        List<Integer> values = Arrays.stream(INPUT.split(" {1,}")) //
                .map(Integer::valueOf) //
                .collect(Collectors.toList());

        ArrayList<Integer> newValues = new ArrayList<>(values);
        while (previousCombinations.indexOf(newValues) < 0) {
            previousCombinations.add(newValues);
            int nrOfBlocks = values.stream().max(Integer::compareTo).get();
            int largest = values.indexOf(nrOfBlocks);

            values.set(largest, 0);
            for (int i = (largest + 1) % values.size(); nrOfBlocks > 0; i = (i + 1) % values.size()) {
                values.set(i, values.get(i) + 1);
                nrOfBlocks--;
            }
            System.out.println(newValues);
        }
        System.out.println("part 1: " + previousCombinations.size());
        System.out.println("part 2: " + (previousCombinations.size() - previousCombinations.indexOf(values)));
    }
}
