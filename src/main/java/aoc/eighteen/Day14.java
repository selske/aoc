package aoc.eighteen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 {

    public static void main(final String[] args) {
        long before = System.currentTimeMillis();
        System.out.println("part1: " + part1(846021));
        System.out.println("part2: " + part2());
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

    private static String part1(int numberOfRecipes) {
        Recipe elf1 = new Recipe(3);
        Recipe elf2 = new Recipe(7);
        elf1.insertRight(elf2);

        Recipe[] elves = new Recipe[]{elf1, elf2};

        final Recipe first = elf1;
        Recipe last = elf2;

        for (int i = 1; i < numberOfRecipes - elves.length + 10; i++) {
            int score = Arrays.stream(elves).mapToInt(Recipe::getValue).sum();
            List<Recipe> newRecipes = getNewRecipes(score);
            for (final Recipe newRecipe : newRecipes) {
                last.insertRight(newRecipe);
                last = newRecipe;
            }
            for (int j = 0; j < elves.length; j++) {
                Recipe elf = elves[j];
                elves[j] = elf.moveRight(1 + elf.getValue());
            }

        }
        StringBuilder sb = new StringBuilder();
        Recipe current = first;
        do {
            sb.append(current.getValue());
            current = current.right;
        } while (current != first);

        return sb.substring(numberOfRecipes, numberOfRecipes + 10);
    }

    private static List<Recipe> getNewRecipes(final int score) {
        List<Recipe> newRecipes = new ArrayList<>();

        int units = score % 10;
        int tens = (score - units) / 10;

        if (tens > 0) {
            newRecipes.add(new Recipe(tens));
        }
        newRecipes.add(new Recipe(units));

        return newRecipes;
    }

    private static String part2() {
        return null;
    }

    private static final class Recipe {

        private Recipe left;
        private Recipe right;
        private int value;

        Recipe(final int value) {
            this.value = value;
            this.left = this;
            this.right = this;
        }

        public Recipe getLeft() {
            return left;
        }

        public Recipe getRight() {
            return right;
        }

        public int getValue() {
            return value;
        }

        void insertRight(Recipe other) {
            this.right.left = other;
            other.right = this.right;
            this.right = other;
            other.left = this;
        }

        public Recipe moveRight(final int value) {
            if (value == 0) {
                return this;
            } else if (value == 1) {
                return right;
            } else {
                return right.moveRight(value - 1);
            }

        }

    }

}
