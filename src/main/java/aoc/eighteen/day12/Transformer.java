package aoc.eighteen.day12;

class Transformer {

    private final char leftLeft, left, current, right, rightRight;
    private final char output;

    Transformer(String value) {
        int i = 0;
        leftLeft = value.charAt(i++);
        left = value.charAt(i++);
        current = value.charAt(i++);
        right = value.charAt(i++);
        rightRight = value.charAt(i++);
        i += 4;
        output = value.charAt(i);
    }

    public boolean matches(Pot pot) {
        return pot.getLeft() != null && pot.getLeft().getLeft() != null &&
                pot.getRight() != null && pot.getRight().getRight() != null &&
                pot.getLeft().getLeft().getValue() == leftLeft &&
                pot.getLeft().getValue() == left &&
                pot.getValue() == current &&
                pot.getRight().getValue() == right &&
                pot.getRight().getRight().getValue() == rightRight;
    }

    char getOutput() {
        return output;
    }

}
