package aoc.eighteen.day12;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Day12 {

    public static void main(final String[] args) throws Exception {
        long before = System.currentTimeMillis();
        System.out.println("part1: " + simulate(20));
        System.out.println("part2: " + simulate(50_000_000_000L));
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

    private static long simulate(final long generations) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get("src/main/java/aoc/eighteen/day12/Day12"));

        String initialState = lines.get(0).substring("initial state: ".length());
        List<Transformer> transformers = initializeMatchers(lines.subList(2, lines.size()));

        Pot first = initialize(initialState);
        Pot previous;
        for (long i = 1; i <= generations; i++) {

            previous = first.copy();
            first = first.pad();
            first = first.transform(transformers);
            first = first.trim();

            if (previous.equals(first)) {
                long generationsLeft = generations - i;
                long currentScore = first.getScore();
                return currentScore + (currentScore - previous.getScore()) * generationsLeft;
            }
        }

        return first.getScore();
    }

    private static Pot initialize(final String initialState) {
        Pot first = new Pot(0, initialState.charAt(0));
        Pot current = first;
        for (int i = 1; i < initialState.toCharArray().length; i++) {
            current = current.addRight(initialState.charAt(i));
        }
        return first;
    }

    private static List<Transformer> initializeMatchers(final List<String> matcherLines) {
        return matcherLines.stream()
                .map(Transformer::new)
                .collect(toList());
    }

}
