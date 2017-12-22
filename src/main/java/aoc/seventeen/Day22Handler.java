package aoc.seventeen;

import aoc.seventeen.Day22.Coordinate;
import aoc.seventeen.Day22.Direction;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public interface Day22Handler {

    Direction handle(Map<Coordinate, Character> map, Coordinate currentLocation, Direction direction, AtomicInteger infectCount, char current);

}
