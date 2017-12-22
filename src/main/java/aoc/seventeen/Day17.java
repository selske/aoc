package aoc.seventeen;

import java.util.ArrayList;
import java.util.List;

public class Day17 {

    public static void main(final String[] args) {
        int result = part1(303, 2017);
        System.out.println(result);

        result = part2(303, 50_000_000);
        System.out.println(result);
    }

    private static int part1(final int input, final int loops) {
        List<Integer> buffer = new ArrayList<>(loops + 1);
        buffer.add(0);
        int pos = 0;
        for (int i = 1; i <= loops; i++) {
            int newPos = ((pos + input) % i) + 1;
            buffer.add(newPos, i);
            pos = newPos;
        }
        return buffer.get(pos + 1);
    }

    private static int part2(final int input, final int loops) {
        int pos = 0;
        int zeroPos = 0;
        int afterZeroVal = 0;
        for (int i = 1; i <= loops; i++) {
            int newPos = ((pos + input) % i) + 1;
            pos = newPos;
            if (newPos == zeroPos + 1) {
                afterZeroVal = i;
            }
            if (pos < zeroPos) {
                zeroPos++;
            }
        }
        return afterZeroVal;
    }

}
