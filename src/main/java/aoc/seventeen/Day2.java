package aoc.seventeen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day2 {

    private static final String input =
                    "86  440 233 83  393 420 228 491 159 13  110 135 97  238 92  396\n" + "3646    3952    3430    145 1574    2722    3565    125 3303    843 152 1095    3805    134 3873    3024\n"
                                    + "2150    257 237 2155    1115    150 502 255 1531    894 2309    1982    2418    206 307 2370\n"
                                    + "1224    343 1039    126 1221    937 136 1185    1194    1312    1217    929 124 1394    1337    168\n"
                                    + "1695    2288    224 2667    2483    3528    809 263 2364    514 3457    3180    2916    239 212 3017\n"
                                    + "827 3521    127 92  2328    3315    1179    3240    695 3144    3139    533 132 82  108 854\n"
                                    + "1522    2136    1252    1049    207 2821    2484    413 2166    1779    162 2154    158 2811    164 2632\n"
                                    + "95  579 1586    1700    79  1745    1105    89  1896    798 1511    1308    1674    701 60  2066\n"
                                    + "1210    325 98  56  1486    1668    64  1601    1934    1384    69  1725    992 619 84  167\n"
                                    + "4620    2358    2195    4312    168 1606    4050    102 2502    138 135 4175    1477    2277    2226    1286\n"
                                    + "5912    6261    3393    431 6285    3636    4836    180 6158    6270    209 3662    5545    204 6131    230\n"
                                    + "170 2056    2123    2220    2275    139 461 810 1429    124 1470    2085    141 1533    1831    518\n"
                                    + "193 281 2976    3009    626 152 1750    1185    3332    715 1861    186 1768    3396    201 3225\n"
                                    + "492 1179    154 1497    819 2809    2200    2324    157 2688    1518    168 2767    2369    2583    173\n"
                                    + "286 2076    243 939 399 451 231 2187    2295    453 1206    2468    2183    230 714 681\n"
                                    + "3111    2857    2312    3230    149 3082    408 1148    2428    134 147 620 128 157 492 2879";

    public static void main(final String[] args) throws Exception {
        calculateChecksum(Day2::part1);
        calculateChecksum(Day2::part2);
    }

    private static int part2(final List<Integer> values) {
        for (int i = 0; i < values.size(); i++) {
            for (int j = i + 1; j < values.size(); j++) {
                if (values.get(i) % values.get(j) == 0) {
                    return values.get(i) / values.get(j);
                } else if (values.get(j) % values.get(i) == 0) {
                    return values.get(j) / values.get(i);
                }
            }
        }
        throw new IllegalStateException("No evenly divisible numbers");
    }

    private static int part1(final List<Integer> values) {
        List<Integer> sorted = values;
        Collections.sort(sorted);
        return sorted.get(sorted.size() - 1) - sorted.get(0);
    }

    private static void calculateChecksum(final Function<List<Integer>, Integer> lineChecksumCalculator) throws IOException {
        final AtomicInteger checksum = new AtomicInteger(0);
        try (final BufferedReader reader = new BufferedReader(new StringReader(input))) {
            reader.lines().forEach(line -> {
                List<Integer> values = Arrays.stream(line.split(" {1,}")) //
                                .map(Integer::valueOf) //
                                .collect(Collectors.toList());

                checksum.addAndGet(lineChecksumCalculator.apply(values));
            });
        }
        System.out.println(checksum);
    }
}
