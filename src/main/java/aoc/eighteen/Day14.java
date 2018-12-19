package aoc.eighteen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 {

    public static void main(final String[] args) {
        long before = System.currentTimeMillis();
        System.out.println("part1: " + part1(846021));
        System.out.println("part2: " + part2("846021"));
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

    private static String part1(int numberOfRecipes) {
        Recipe first = new Recipe(3);
        Recipe second = new Recipe(7);
        first.insertRight(second);

        Recipe[] elves = new Recipe[]{first, second};

        Recipe last = second;

        for (int i = 1; i < numberOfRecipes - elves.length + 10; i++) {
            for (final Recipe newRecipe : getNewRecipes(elves)) {
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

    private static List<Recipe> getNewRecipes(final Recipe[] elves) {
        int score = Arrays.stream(elves).mapToInt(Recipe::getValue).sum();
        List<Recipe> newRecipes = new ArrayList<>();

        int units = score % 10;
        int tens = (score - units) / 10;

        if (tens > 0) {
            newRecipes.add(new Recipe(tens));
        }
        newRecipes.add(new Recipe(units));

        return newRecipes;
    }

    private static int part2(String pattern) {
        Recipe first = new Recipe(3);
        Recipe second = new Recipe(7);
        first.insertRight(second);

        Recipe[] elves = new Recipe[]{first, second};

        Recipe last = second;

        for (int i = elves.length; ; ) {
            for (final Recipe newRecipe : getNewRecipes(elves)) {
                last.insertRight(newRecipe);
                last = newRecipe;

                i++;
                if (matchesPattern(pattern, last)) {
                    return i - pattern.length();
                }
            }
            for (int j = 0; j < elves.length; j++) {
                Recipe elf = elves[j];
                elves[j] = elf.moveRight(1 + elf.getValue());
            }
        }
    }

    private static boolean matchesPattern(final String pattern, final Recipe newRecipe) {
        Recipe recipe = newRecipe;
        final char[] charArray = pattern.toCharArray();
        for (int charIndex = charArray.length - 1; charIndex >= 0; charIndex--) {
            final int val = Character.getNumericValue(charArray[charIndex]);
            if (val != recipe.value) {
                return false;
            }
            recipe = recipe.getLeft();
        }
        return true;
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

        Recipe getLeft() {
            return left;
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

        Recipe moveRight(final int value) {
            if (value == 0) {
                return this;
            } else {
                return right.moveRight(value - 1);
            }

        }

    }

}
