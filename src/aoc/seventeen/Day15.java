package aoc.seventeen;

import java.time.Duration;
import java.time.Instant;
import java.util.PrimitiveIterator.OfLong;
import java.util.function.LongPredicate;
import java.util.stream.LongStream;

public class Day15 {

    private static final int MASK = 0b1111111111111111;

    private static final class Generator {
        private static final int MODULATOR = 2147483647;

        private final OfLong iterator;

        public Generator(final int seed, final int multiplier) {
            this(seed, multiplier, i -> true);
        }

        public Generator(final int seed, final int multiplier, final LongPredicate filter) {
            iterator = LongStream.iterate(seed, (i) -> (i * multiplier) % MODULATOR).skip(1).filter(filter).iterator();
        }

        public long nextLong() {
            return iterator.nextLong();
        }
    }

    public static void main(final String[] args) {
        System.out.println(countMatches(new Generator(873, 16807), new Generator(583, 48271), 40_000_000));

        System.out.println(countMatches(new Generator(873, 16807, i -> (i & 3) == 0), new Generator(583, 48271, i -> (i & 7) == 0), 5_000_000));
    }

    private static int countMatches(final Generator generatorA, final Generator generatorB, final int amountToCheck) {
        Instant before = Instant.now();

        int count = 0;
        for (int i = 0; i < amountToCheck; i++) {
            if ((generatorA.nextLong() & MASK) == (generatorB.nextLong() & MASK)) {
                count++;
            }
        }

        Instant after = Instant.now();
        System.out.println(Duration.between(before, after));
        return count;

    }

}
