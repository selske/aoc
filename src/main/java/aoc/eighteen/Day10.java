package aoc.eighteen;

import lombok.Getter;
import lombok.ToString;
import lombok.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

public class Day10 {

    @Value
    private static final class Coordinate {

        private final int x;
        private final int y;

        @Override
        public String toString() {
            return "<" + x + ", " + y + ">";
        }

    }

    @Value
    private static final class Frame {

        private final int fromX, fromY, toX, toY;

    }

    @ToString
    @Getter
    private static final class Point {

        private static final Pattern PATTERN = Pattern.compile("position=< *(?<x>-?\\d*), *(?<y>-?\\d*)> velocity=< *(?<vx>-?\\d*), *(?<vy>-?\\d*)>");

        private Coordinate position;
        private final Coordinate velocity;

        public Point(String line) {
            Matcher matcher = PATTERN.matcher(line);
            matcher.matches();
            position = new Coordinate(parseInt(matcher.group("x")), parseInt(matcher.group("y")));
            velocity = new Coordinate(parseInt(matcher.group("vx")), parseInt(matcher.group("vy")));
        }

        public void move() {
            position = new Coordinate(position.x + velocity.x, position.y + velocity.y);
        }

    }

    private static int solve() throws IOException {
        List<Point> points = Files.lines(Paths.get("src/main/java/aoc/eighteen/Day10"))
                .map(Point::new)
                .collect(toList());

        for (int i = 1; i <= 100_000; i++) {
            points.forEach(Point::move);
            if (evaluate(points)) {
                return i;
            }
        }

        return -1;
    }

    private static boolean evaluate(List<Point> points) {
        Frame frame = calculateFrame(points);
        int frameHeight = Math.abs(frame.toY - frame.fromY);
        if (frameHeight < 9 || frameHeight > 10) {
            return false;
        }

        for (int j = frame.fromY; j <= frame.toY; j++) {
            for (int i = frame.fromX; i <= frame.toX; i++) {
                Coordinate c = new Coordinate(i, j);
                if (points.stream().anyMatch(p -> p.position.equals(c))) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        return true;
    }

    private static Frame calculateFrame(List<Point> points) {
        int fromX = points.stream().map(Point::getPosition).min(comparingInt(Coordinate::getX)).get().getX();
        int toX = points.stream().map(Point::getPosition).max(comparingInt(Coordinate::getX)).get().getX();
        int fromY = points.stream().map(Point::getPosition).min(comparingInt(Coordinate::getY)).get().getY();
        int toY = points.stream().map(Point::getPosition).max(comparingInt(Coordinate::getY)).get().getY();

        return new Frame(fromX, fromY, toX, toY);
    }

    public static void main(final String[] args) throws IOException {
        long before = System.currentTimeMillis();
        System.out.println(solve());
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

}
