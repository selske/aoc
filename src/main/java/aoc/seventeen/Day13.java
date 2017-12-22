package aoc.seventeen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    private static final String INPUT =
                    "0: 4\n1: 2\n2: 3\n4: 4\n6: 6\n8: 5\n10: 6\n12: 6\n14: 6\n16: 8\n18: 8\n20: 9\n22: 12\n24: 8\n26: 8\n28: 8\n30: 12\n32: 12\n34: 8\n36: 12\n38: 10\n40: 12\n42: 12\n44: 10\n46: 12\n48: 14\n50: 12\n52: 14\n54: 14\n56: 12\n58: 14\n60: 12\n62: 14\n64: 18\n66: 14\n68: 14\n72: 14\n76: 14\n82: 14\n86: 14\n88: 18\n90: 14\n92: 17";

    private static final class Layer {
        private final int index;
        private final int range;

        public Layer(final int index, final int range) {
            this.index = index;
            this.range = range;
        }

        public int severity() {
            return index * range;
        }
    }

    public static void main(final String[] args) {
        System.out.println(part1());
        Instant start = Instant.now();
        System.out.println(part2());
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end));
    }

    private static long part1() {
        return new BufferedReader(new StringReader(INPUT)).lines() //
                        .map(line -> line.split(": ")) //
                        .map(parts -> new Layer(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]))) //
                        .filter(layer -> layer.index % (layer.range + layer.range - 1 - 1) == 0) //
                        .collect(Collectors.summarizingInt(Layer::severity)) //
                        .getSum();
    }

    private static long part2() {
        List<Layer> layers = new BufferedReader(new StringReader(INPUT)).lines() //
                        .map(line -> line.split(": ")) //
                        .map(parts -> new Layer(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]))) //
                        .collect(Collectors.toList());
        int i = 0;
        while (isCaught(layers, i)) {
            i++;
        }
        return i;
    }

    private static boolean isCaught(final List<Layer> layers, final int offset) {
        return layers.stream() //
                        .anyMatch(layer -> (layer.index + offset) % (layer.range + layer.range - 1 - 1) == 0);
    }

}
