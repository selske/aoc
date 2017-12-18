package aoc.seventeen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day10 {

    private static final int MAX = 256;
    private static final String LENGTHS = "192,69,168,160,78,1,166,28,0,83,198,2,254,255,41,12";

    public static void main(final String[] args) {
        List<Integer> lengths = Arrays.stream(LENGTHS.split(",")) //
                        .map(Integer::valueOf) //
                        .collect(Collectors.toList());

        int[] values = IntStream.range(0, MAX).toArray();

        int startPos = 0;
        int skip = 0;
        for (Integer length : lengths) {
            System.out.println(Arrays.toString(values));
            List<Integer> subList = new ArrayList<>();
            for (int i = startPos; subList.size() < length; i = (i + 1) % MAX) {
                subList.add(values[i]);
            }
            System.out.println(subList);
            Collections.reverse(subList);
            for (int i = startPos; subList.size() > 0; i = (i + 1) % MAX) {
                values[i] = subList.remove(0);
            }
            startPos = (startPos + length + skip++) % MAX;
        }
        System.out.println(values[0] * values[1]);
    }

}
