package aoc.seventeen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Day21 {

    private static final String INITIAL_GRID = ".#./..#/###";
    private static final String INPUT = "" +
//     "../.# => ##./#../...\n.#./..#/### => #..#/..../..../#..#";
"../.. => ###/##./##.\n" +
"#./.. => .##/###/...\n" +
"##/.. => ##./###/#..\n" +
".#/#. => .##/#.#/###\n" +
"##/#. => .#./###/#..\n" +
"##/## => .../#.#/...\n" +
".../.../... => .#../#.#./..##/.###\n" +
"#../.../... => ...#/#..#/#.../#.#.\n" +
".#./.../... => .###/..##/.#.#/..#.\n" +
"##./.../... => ##.#/#.##/..#./...#\n" +
"#.#/.../... => .##./####/..../..#.\n" +
"###/.../... => ...#/..../..../##.#\n" +
".#./#../... => ##../#..#/..##/#.##\n" +
"##./#../... => ###./...#/#..#/#.#.\n" +
"..#/#../... => .##./#.../.###/#.#.\n" +
"#.#/#../... => ...#/#.##/####/##.#\n" +
".##/#../... => ####/..../..#./....\n" +
"###/#../... => ..##/..##/.###/..#.\n" +
".../.#./... => ..../.#../.#.#/...#\n" +
"#../.#./... => ..#./#.../####/#...\n" +
".#./.#./... => ...#/##../#.../.#..\n" +
"##./.#./... => ####/..../#.../#..#\n" +
"#.#/.#./... => #.##/#.#./##../.##.\n" +
"###/.#./... => ###./...#/#.##/#.##\n" +
".#./##./... => ..##/#.##/###./#.#.\n" +
"##./##./... => ..../..##/.###/#..#\n" +
"..#/##./... => ####/...#/##../####\n" +
"#.#/##./... => ##../.#.#/.#../.#.#\n" +
".##/##./... => ..#./.#../.#../###.\n" +
"###/##./... => #.../.###/..##/.#..\n" +
".../#.#/... => ...#/##../.##./....\n" +
"#../#.#/... => .#../..../..#./###.\n" +
".#./#.#/... => ##../#.#./#.../...#\n" +
"##./#.#/... => .##./...#/#.##/#.##\n" +
"#.#/#.#/... => #.../#.##/#..#/...#\n" +
"###/#.#/... => ..##/#.##/...#/###.\n" +
".../###/... => #.../#.##/.#.#/...#\n" +
"#../###/... => ..#./###./.#.#/#.#.\n" +
".#./###/... => ###./.#.#/.##./.#.#\n" +
"##./###/... => .###/#.##/##../.##.\n" +
"#.#/###/... => .#../..##/..../..#.\n" +
"###/###/... => ...#/.#../.#.#/#...\n" +
"..#/.../#.. => ...#/.#.#/..#./...#\n" +
"#.#/.../#.. => ##.#/###./####/##.#\n" +
".##/.../#.. => .###/.##./..##/....\n" +
"###/.../#.. => ..../.#.#/...#/#..#\n" +
".##/#../#.. => #.##/.#.#/##../##..\n" +
"###/#../#.. => ..../..##/####/#.#.\n" +
"..#/.#./#.. => #.#./##.#/##../##..\n" +
"#.#/.#./#.. => #.##/.##./##.#/#.##\n" +
".##/.#./#.. => ##.#/#.../.#../####\n" +
"###/.#./#.. => ###./..##/####/.##.\n" +
".##/##./#.. => #.##/..#./...#/.#..\n" +
"###/##./#.. => ##.#/#.#./#.../..#.\n" +
"#../..#/#.. => ####/...#/..../#.#.\n" +
".#./..#/#.. => ##../.###/.###/.#..\n" +
"##./..#/#.. => #.#./###./...#/.##.\n" +
"#.#/..#/#.. => #.#./.###/..##/..##\n" +
".##/..#/#.. => .#../#..#/##../#...\n" +
"###/..#/#.. => ####/#.##/#.../...#\n" +
"#../#.#/#.. => ##../.###/#.../....\n" +
".#./#.#/#.. => ..../#.#./##../..#.\n" +
"##./#.#/#.. => #..#/...#/##../##..\n" +
"..#/#.#/#.. => ####/#..#/..##/##.#\n" +
"#.#/#.#/#.. => .#../..##/###./.#..\n" +
".##/#.#/#.. => #..#/#.../..../####\n" +
"###/#.#/#.. => ..../.##./.#.#/...#\n" +
"#../.##/#.. => #.#./#.##/#.../#...\n" +
".#./.##/#.. => #.##/###./.##./####\n" +
"##./.##/#.. => ###./.###/##.#/#..#\n" +
"#.#/.##/#.. => ##../####/####/#.#.\n" +
".##/.##/#.. => ####/#.#./###./.##.\n" +
"###/.##/#.. => ##.#/#..#/.##./..#.\n" +
"#../###/#.. => ...#/####/#.#./#.#.\n" +
".#./###/#.. => ..##/.##./.###/..#.\n" +
"##./###/#.. => ..#./##.#/####/##..\n" +
"..#/###/#.. => ###./.###/..../###.\n" +
"#.#/###/#.. => ##.#/###./..../..#.\n" +
".##/###/#.. => .##./##.#/#.##/.##.\n" +
"###/###/#.. => .#.#/####/#.##/##..\n" +
".#./#.#/.#. => ..##/..##/#.##/#.#.\n" +
"##./#.#/.#. => #.##/#.##/..#./..##\n" +
"#.#/#.#/.#. => .#.#/..../#.#./..#.\n" +
"###/#.#/.#. => #.#./..##/###./#.##\n" +
".#./###/.#. => .#../.###/..##/##..\n" +
"##./###/.#. => ##.#/..#./#.#./#...\n" +
"#.#/###/.#. => ##../###./.#.#/...#\n" +
"###/###/.#. => ###./##.#/..../###.\n" +
"#.#/..#/##. => #.#./##.#/#.#./.##.\n" +
"###/..#/##. => .#.#/#.../####/.##.\n" +
".##/#.#/##. => ##.#/#.#./#.#./.#..\n" +
"###/#.#/##. => ...#/..##/###./#.##\n" +
"#.#/.##/##. => #.##/##.#/#..#/..##\n" +
"###/.##/##. => .###/.#.#/#.##/.#..\n" +
".##/###/##. => #..#/#..#/#.##/.#..\n" +
"###/###/##. => ..##/.##./####/###.\n" +
"#.#/.../#.# => ##../#.##/##../.##.\n" +
"###/.../#.# => ###./#.#./##.#/####\n" +
"###/#../#.# => ###./#.#./#..#/...#\n" +
"#.#/.#./#.# => ###./###./.#.#/.#..\n" +
"###/.#./#.# => ..#./...#/..#./##..\n" +
"###/##./#.# => .###/#..#/#.##/.#.#\n" +
"#.#/#.#/#.# => ####/#.../##../....\n" +
"###/#.#/#.# => ##../...#/..##/..#.\n" +
"#.#/###/#.# => .##./#.../#..#/#..#\n" +
"###/###/#.# => .#.#/#.../..../#..#\n" +
"###/#.#/### => #.#./.##./##../.###\n" +
"###/###/### => ###./.#../####/..##";

    public static void main(final String[] args) {
        Map<String, char[][]> patterns = getPatterns();

        System.out.println(iterate( patterns, 5));
        System.out.println(iterate( patterns, 18));
    }

    private static int iterate(final Map<String, char[][]> patterns, final int iterations) {
        char[][] grid = stringToGrid(INITIAL_GRID);
        for (int i = 0; i < iterations; i++) {
            final int subGridSize;
            if (grid.length % 2 == 0) {
                subGridSize = 2;
            } else {
                subGridSize = 3;
            }
            int subGridCount = grid.length / subGridSize;
            int newSubGridSize = subGridSize + 1;
            int newGridSize = newSubGridSize * subGridCount;

            char[][] newGrid = new char[newGridSize][newGridSize];
            for (int row = 0; row < subGridCount; row++) {
                for (int col = 0; col < subGridCount; col++) {
                    String gridString = gridToString(grid, row * subGridSize, (row + 1) * subGridSize, col * subGridSize, (col + 1) * subGridSize);

                    char[][] newSubGrid = patterns.get(gridString);
                    if (newSubGrid == null) {
                        throw new IllegalStateException();
                    }
                    putInGrid(newGrid, newSubGrid, row * newSubGridSize, (row + 1) * newSubGridSize, col * newSubGridSize, (col + 1) * newSubGridSize);
                }
            }
            grid = newGrid;
        }

        return gridToString(grid).replaceAll("[\\./]", "").length();
    }

    private static void putInGrid(final char[][] target, final char[][] source, final int rowFrom, final int rowTo, final int colFrom, final int colTo) {
        for (int row = 0; row < rowTo - rowFrom; row++) {
            for (int col = 0; col < colTo - colFrom; col++) {
                target[rowFrom + row][colFrom + col] = source[row][col];
            }
        }
    }

    private static Map<String, char[][]> getPatterns() {
        Map<String, char[][]> literalPatterns = new BufferedReader(new StringReader(INPUT)) //
                .lines() //
                .map(line -> line.split(" => ")) //
                .collect(Collectors.toMap((final String[] l) -> l[0], (final String[] l) -> stringToGrid(l[1])));

        Map<String, char[][]> patterns = new HashMap<>();
        for (Entry<String, char[][]> entry : literalPatterns.entrySet()) {
            for (String permutation : permutateGrid(stringToGrid(entry.getKey()))) {
                char[][] oldVal = patterns.put(permutation, entry.getValue());
                if(oldVal != null) {
                    throw new IllegalStateException();
                }
            }
        }
        return patterns;
    }

    private static Set<String> permutateGrid(final char[][] grid) {
        char[][] gridCopy = Arrays.copyOf(grid, grid.length);
        Set<String> patterns = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            rotateLeft(gridCopy);
            patterns.add(gridToString(gridCopy));
            patterns.addAll(flipper(gridCopy));
        }

        return patterns;
    }

    private static Set<String> flipper(final char[][] grid) {
        Set<String> patterns = new HashSet<>();
        char[][] gridCopy = Arrays.copyOf(grid, grid.length);
        flipHorizontal(gridCopy);
        patterns.add(gridToString(gridCopy));
        flipVertical(gridCopy);
        patterns.add(gridToString(gridCopy));
        flipHorizontal(gridCopy);
        patterns.add(gridToString(gridCopy));
        return patterns;
    }

    private static void flipVertical(final char[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            char temp = grid[row][0];
            grid[row][0] = grid[row][grid.length - 1];
            grid[row][grid.length - 1] = temp;
        }
    }

    private static void flipHorizontal(final char[][] grid) {
        char[] temp = grid[0];
        grid[0] = grid[grid.length - 1];
        grid[grid.length - 1] = temp;
    }

    private static void rotateLeft(final char[][] grid) {
        int n = grid.length;
        for (int x = 0; x < n / 2; x++) {
            for (int y = x; y < n - x - 1; y++) {
                char temp = grid[x][y];
                grid[x][y] = grid[y][n - 1 - x];
                grid[y][n - 1 - x] = grid[n - 1 - x][n - 1 - y];
                grid[n - 1 - x][n - 1 - y] = grid[n - 1 - y][x];
                grid[n - 1 - y][x] = temp;

            }
        }
    }

    private static final char[][] stringToGrid(final String gridString) {
        String[] rows = gridString.split("/");

        char[][] grid = new char[rows.length][rows.length];
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            grid[i] = new char[row.length()];
            for (int j = 0; j < row.length(); j++) {
                grid[i][j] = row.charAt(j);
            }
        }
        return grid;
    }

    private static final String gridToString(final char[][] grid) {
        return gridToString(grid, 0, grid.length, 0, grid.length);
    }

    private static final String gridToString(final char[][] grid, final int rowFrom, final int rowTo, final int colFrom, final int colTo) {
        StringBuilder sb = new StringBuilder();
        for (int i = rowFrom; i < rowTo; i++) {
            for (int j = colFrom; j < colTo; j++) {
                sb.append(grid[i][j]);
            }
            if (i < rowTo - 1) {
                sb.append('/');
            }
        }
        return sb.toString();
    }
}
