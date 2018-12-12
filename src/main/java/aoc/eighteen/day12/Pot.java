package aoc.eighteen.day12;

import java.util.List;
import java.util.Objects;

final class Pot {

    private Pot left;
    private Pot right;
    private final int number;
    private final char value;

    Pot(final int number, final char value) {
        this.number = number;
        this.value = value;
    }

    Pot getLeft() {
        return left;
    }

    Pot getRight() {
        return right;
    }

    public char getValue() {
        return value;
    }

    Pot addRight(char value) {
        Pot right = new Pot(number + 1, value);
        this.right = right;
        right.left = this;
        return right;
    }

    private void addRight(Pot right) {
        this.right = right;
        right.left = this;
    }

    Pot pad() {
        padRight();
        return padLeft();
    }

    private Pot padLeft() {
        if (left == null) {
            return addLeft('.').addLeft('.').addLeft('.');
        } else {
            return left.padLeft();
        }
    }

    private void padRight() {
        if (right == null) {
            addRight('.').addRight('.').addRight('.');
        } else {
            right.padRight();
        }
    }

    private Pot addLeft(char value) {
        Pot left = new Pot(number - 1, value);
        this.left = left;
        left.right = this;
        return left;
    }

    Pot trim() {
        trimRight();
        return trimLeft();
    }

    private void trimRight() {
        if (right == null) {
            if ('.' == value) {
                left.right = null;
                left.trimRight();
            }
        } else {
            right.trimRight();
        }
    }

    private Pot trimLeft() {
        if (left == null) {
            if ('.' == value) {
                right.left = null;
                return right.trimLeft();
            }
        }
        return this;
    }

    Pot copy() {
        Pot clone = new Pot(number, value);
        if (right != null) {
            clone.addRight(right.copy());
        }
        return clone;
    }

    long getScore() {
        long score = 0;
        if ('#' == value) {
            score += number;
        }
        if (right != null) {
            score += right.getScore();
        }
        return score;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Pot pot = (Pot) o;
        return value == pot.value &&
                Objects.equals(right, pot.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(right, value);
    }

    @Override
    public String toString() {
        return value + (right != null ? right.toString() : "");
    }

    Pot transform(final List<Transformer> transformers) {
        char output = (char) transformers.stream()
                .filter(t -> t.matches(this))
                .mapToInt(Transformer::getOutput)
                .findFirst().orElse('.');
        Pot transformed = new Pot(number, output);
        if (right != null) {
            transformed.addRight(right.transform(transformers));
        }
        return transformed;
    }

}
