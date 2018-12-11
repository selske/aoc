package aoc.eighteen;

import java.util.HashMap;
import java.util.Map;

import static java.util.Comparator.comparing;

public class Day9LinkedMarbles {

    private static final class Marble {

        private final int value;
        private Marble clockWise;
        private Marble counterClockWise;

        private Marble(int value) {
            this.value = value;
        }

        public void setClockWise(Marble clockWise) {
            this.clockWise = clockWise;
            clockWise.counterClockWise = this;
        }

        public void setCounterClockWise(Marble counterClockWise) {
            this.counterClockWise = counterClockWise;
            counterClockWise.clockWise = this;
        }

    }

    private static long calculateBestScore(int numberOfPlayers, int lastMarbleScore) {
        Marble currentMarble = new Marble(0);
        Map<Integer, Long> scores = new HashMap<>();
        for (int marble = 1, activePlayer = 0; marble <= lastMarbleScore; marble++, activePlayer++) {
            activePlayer %= numberOfPlayers;
            if (marble % 23 == 0) {
                for (int i = 0; i < 7; i++) {
                    currentMarble = currentMarble.counterClockWise;
                }

                currentMarble.clockWise.setCounterClockWise(currentMarble.counterClockWise);
                long score = marble + currentMarble.value;
                scores.merge(activePlayer, score, (o, n) -> o + n);
                currentMarble = currentMarble.clockWise;
            } else {
                currentMarble = currentMarble.clockWise;
                Marble temp = currentMarble.clockWise;
                currentMarble.clockWise = new Marble(marble);
                temp.counterClockWise = currentMarble.clockWise;
            }
        }
        return scores.values().stream().max(comparing(Long::longValue)).get();
    }

    public static void main(final String[] args) {
        long before = System.currentTimeMillis();
        System.out.println("part 1: " + calculateBestScore(10, 1618));
//        System.out.println("part 1: " + calculateBestScore(416, 71975));
//        System.out.println("part 2: " + calculateBestScore(416, 71975 * 100));
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

}
