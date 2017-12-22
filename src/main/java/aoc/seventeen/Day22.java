package aoc.seventeen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day22 {
    private static final String INPUT = "" + //
                    "###.######..##.##..#..#.#\n" + "#.#.#.##.##.#####..##..#.\n" + "##...#.....#.#.#..##.#.##\n" + "....#####.#.#.#..###.###.\n" + "###.#.......#..#.#...#..#\n"
                    + ".#.######.##.#.....#...##\n" + "##.#...#..#..#....##.#.#.\n" + "#.##..#..##.##..###...#.#\n" + ".#.......#.#..####.#.#.##\n" + ".#...###...##..#...#.#..#\n"
                    + "...##......#.##.....#..#.\n" + "######....##...##.....#.#\n" + ".####..##..##.#.##.##..#.\n" + ".#.#...###.#....#.##.####\n" + "..####..#.#..#.#.#......#\n"
                    + "#.#..##..#####.#.#....##.\n" + ".....#..########....#.##.\n" + "##.###....#..###..#.....#\n" + ".#.##...#.#...###.##...#.\n" + "..#.##..#..####.##..###.#\n"
                    + ".#..#.##..#.##...#####.#.\n" + "#..##............#..#....\n" + "###.....#.##.#####...#.##\n" + "##.##..#.....##..........\n" + "#.#..##.#.#..#....##..#.#";

    public static enum Direction {
        RIGHT(c -> new Coordinate(c.row, c.col + 1)), //
        UP(c -> new Coordinate(c.row - 1, c.col)), //
        LEFT(c -> new Coordinate(c.row, c.col - 1)), //
        DOWN(c -> new Coordinate(c.row + 1, c.col));

        Function<Coordinate, Coordinate> moveFunction;

        private Direction(final Function<Coordinate, Coordinate> moveFunction) {
            this.moveFunction = moveFunction;
        }

        public Direction reverse() {
            return values()[(ordinal() + 2) % values().length];
        }

        public Direction turnLeft() {
            return values()[(ordinal() + 1) % values().length];
        }

        public Direction turnRight() {
            return values()[(ordinal() + values().length - 1) % values().length];
        }

    }
    public static final class Coordinate {
        private final int row, col;

        public Coordinate(final int row, final int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return row + "," + col;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + col;
            result = prime * result + row;
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
            if (col != other.col) {
                return false;
            }
            if (row != other.row) {
                return false;
            }
            return true;
        }

    }

    public static void main(final String[] args) {

        List<String> lines = new BufferedReader(new StringReader(INPUT)) //
                        .lines() //
                        .collect(Collectors.toList());
        System.out.println(solve(lines, Day22::part1Handle, 10_000));
        System.out.println(solve(lines, Day22::part2Handle, 10_000_000));
    }

    private static AtomicInteger solve(final List<String> lines, final Day22Handler handler, final int loops) {
        Map<Coordinate, Character> map = new HashMap<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).replaceAll(" ", "");
            for (int j = 0; j < lines.size(); j++) {
                map.put(new Coordinate(i, j), line.charAt(j));
            }
        }

        int center = lines.size() / 2;
        Coordinate currentLocation = new Coordinate(center, center);
        Direction direction = Direction.UP;
        AtomicInteger infectCount = new AtomicInteger();
        for (int i = 0; i < loops; i++) {
            char current = map.computeIfAbsent(currentLocation, c -> '.');
            direction = handler.handle(map, currentLocation, direction, infectCount, current);
            currentLocation = direction.moveFunction.apply(currentLocation);
        }
        return infectCount;
    }

    private static Direction part1Handle(final Map<Coordinate, Character> map, final Coordinate currentLocation, final Direction direction, final AtomicInteger infectCount, final char current) {
        switch (current) {
            case '#':
                map.put(currentLocation, '.');
                return direction.turnRight();
            case '.':
                map.put(currentLocation, '#');
                infectCount.incrementAndGet();
                return direction.turnLeft();
            default:
                throw new IllegalArgumentException();
        }
    }

    private static Direction part2Handle(final Map<Coordinate, Character> map, final Coordinate currentLocation, final Direction direction, final AtomicInteger infectCount, final char current) {
        switch (current) {
            case '#':
                map.put(currentLocation, 'F');
                return direction.turnRight();
            case '.':
                map.put(currentLocation, 'W');
                return direction.turnLeft();
            case 'W':
                map.put(currentLocation, '#');
                infectCount.incrementAndGet();
                return direction;
            case 'F':
                map.put(currentLocation, '.');
                return direction.reverse();
            default:
                throw new IllegalArgumentException();
        }
    }

}
