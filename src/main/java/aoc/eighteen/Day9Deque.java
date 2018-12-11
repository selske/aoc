package aoc.eighteen;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static java.util.Comparator.comparing;

public class Day9Deque {

    private static long calculateBestScore(int numberOfPlayers, int lastMarbleScore) {
        Deque<Integer> circle = new LinkedList<>();
        circle.addFirst(0);
        Map<Integer, Long> scores = new HashMap<>();
        for (int marble = 1, activePlayer = 0; marble <= lastMarbleScore; marble++, activePlayer++) {
            activePlayer %= numberOfPlayers;
            if (marble % 23 == 0) {
                for (int i = 0; i < 7; i++) {
                    circle.addLast(circle.pollFirst());
                }

                long score = marble + circle.pollFirst();
                scores.merge(activePlayer, score, (o, n) -> o + n);
                circle.addFirst(circle.pollLast());
            } else {
                circle.addFirst(circle.pollLast());
                circle.addFirst(marble);
            }
        }
        return scores.values().stream().max(comparing(Long::longValue)).get();
    }

    public static void main(final String[] args) {
        long before = System.currentTimeMillis();
        System.out.println("part 1: " + calculateBestScore(416, 71975));
        System.out.println("part 2: " + calculateBestScore(416, 71975 * 100));
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

}
