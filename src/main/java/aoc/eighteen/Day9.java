package aoc.eighteen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;

public class Day9 {

    public static final int LIST_LIMIT = 25_000;

    private static long calculateBestScore(int numberOfPlayers, int lastMarbleScore) {
        List<List<Integer>> circles = new ArrayList<>(lastMarbleScore / LIST_LIMIT + 1);
        List<Integer> circle = new ArrayList<>(LIST_LIMIT + 1);
        circles.add(circle);
        circle.add(0);
        Map<Integer, Long> scores = new HashMap<>();
        int currentIndex = 0;
        for (int i = 1, activePlayer = 0; i <= lastMarbleScore; i++, activePlayer++) {
            activePlayer %= numberOfPlayers;
            if (i % 23 == 0) {
                long score = i;
                int indexToRemove;
                if (currentIndex >= 7) {
                    indexToRemove = currentIndex - 7;
                } else {
                    indexToRemove = currentIndex + getSize(circles) - 7;
                }
                score += removeFromCircle(circles, indexToRemove);
                scores.merge(activePlayer, score, (o, n) -> o + n);
                currentIndex = indexToRemove;
            } else {
                int targetIndex = (currentIndex + 2) % getSize(circles);
                if (targetIndex == 0) {
                    targetIndex = getSize(circles);
                }
                addToCircle(circles, i, targetIndex);
                currentIndex = targetIndex;
            }
        }
        return scores.values().stream().sorted(comparing(Long::longValue).reversed()).findFirst().get();
    }

    private static int getSize(List<List<Integer>> circles) {
        return circles.stream().mapToInt(List::size).sum();
    }

    private static int removeFromCircle(List<List<Integer>> circles, int indexToRemove) {
        int itr = indexToRemove;
        for (List<Integer> circle : circles) {
            if (itr < circle.size()) {
                return circle.remove(itr);
            }
            itr -= circle.size();
        }
        throw new IllegalStateException();
    }

    private static void addToCircle(List<List<Integer>> circles, int value, int targetIndex) {
        int itr = targetIndex;
        for (int i = 0; i < circles.size(); i++) {
            List<Integer> circle = circles.get(i);
            if (itr < circle.size()) {
                circle.add(itr, value);

                if (circle.size() > LIST_LIMIT) {
                    circles.remove(i);
                    ArrayList<Integer> l = new ArrayList<>(LIST_LIMIT);
                    l.addAll(circle.subList(0, LIST_LIMIT / 2));
                    circles.add(i, l);

                    l = new ArrayList<>(LIST_LIMIT);
                    l.addAll(circle.subList(LIST_LIMIT / 2, circle.size()));
                    circles.add(i + 1, l);
                }

                return;
            }
            itr -= circle.size();
        }
        circles.get(circles.size() - 1).add(value);
    }

    public static void main(final String[] args) {
        long before = System.currentTimeMillis();
        System.out.println("part 1: " + calculateBestScore(416, 71975));
        System.out.println("part 2: " + calculateBestScore(416, 71975 * 100));
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

}
