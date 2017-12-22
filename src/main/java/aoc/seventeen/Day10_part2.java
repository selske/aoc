package aoc.seventeen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10_part2 {

    private static final int MAX = 256;
    private static final String LENGTHS = "192,69,168,160,78,1,166,28,0,83,198,2,254,255,41,12";

    public static void main(final String[] args) {
        String input = LENGTHS;
        List<Integer> lengths = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            lengths.add((int) input.charAt(i));
        }
        String hash = generateKnotHash(lengths);

        System.out.println(hash);

    }

    public static String generateKnotHash(final List<Integer> lengths) {
        lengths.addAll(Arrays.asList(17, 31, 73, 47, 23));
        int[] values = IntStream.range(0, MAX).toArray();

        int startPos = 0;
        int skip = 0;
        for (int loop = 0; loop < 64; loop++) {
            for (Integer length : lengths) {
                List<Integer> subList = new ArrayList<>();
                for (int i = startPos; subList.size() < length; i = (i + 1) % MAX) {
                    subList.add(values[i]);
                }
                Collections.reverse(subList);
                for (int i = startPos; subList.size() > 0; i = (i + 1) % MAX) {
                    values[i] = subList.remove(0);
                }
                startPos = (startPos + length + skip++) % MAX;
            }
        }

        int[] denseHash = new int[16];
        int curVal = values[0];
        for (int i = 1; i < values.length; i++) {
            curVal ^= values[i];
            if (i % 16 == 15) {
                denseHash[i / 16] = curVal;
                if (i < values.length - 1) {
                    curVal = values[++i];
                }
            }
        }

        String hash = Arrays.stream(denseHash) //
                        .mapToObj((final int hashPart) -> String.format("%02x", hashPart)) //
                        .collect(Collectors.joining());
        return hash;
    }

}
