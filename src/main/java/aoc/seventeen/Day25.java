package aoc.seventeen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day25 {

    private static final String INPUT = "Begin in state A.\n" + //
                    "Perform a diagnostic checksum after 12317297 steps.\n" + //
                    "\n" + //
                    "In state A:\n" + //
                    "  If the current value is 0:\n" + //
                    "    - Write the value 1.\n" + //
                    "    - Move one slot to the right.\n" + //
                    "    - Continue with state B.\n" + //
                    "  If the current value is 1:\n" + //
                    "    - Write the value 0.\n" + //
                    "    - Move one slot to the left.\n" + //
                    "    - Continue with state D.\n" + //
                    "\n" + //
                    "In state B:\n" + //
                    "  If the current value is 0:\n" + //
                    "    - Write the value 1.\n" + //
                    "    - Move one slot to the right.\n" + //
                    "    - Continue with state C.\n" + //
                    "  If the current value is 1:\n" + //
                    "    - Write the value 0.\n" + //
                    "    - Move one slot to the right.\n" + //
                    "    - Continue with state F.\n" + //
                    "\n" + //
                    "In state C:\n" + //
                    "  If the current value is 0:\n" + //
                    "    - Write the value 1.\n" + //
                    "    - Move one slot to the left.\n" + //
                    "    - Continue with state C.\n" + //
                    "  If the current value is 1:\n" + //
                    "    - Write the value 1.\n" + //
                    "    - Move one slot to the left.\n" + //
                    "    - Continue with state A.\n" + //
                    "\n" + //
                    "In state D:\n" + //
                    "  If the current value is 0:\n" + //
                    "    - Write the value 0.\n" + //
                    "    - Move one slot to the left.\n" + //
                    "    - Continue with state E.\n" + //
                    "  If the current value is 1:\n" + //
                    "    - Write the value 1.\n" + //
                    "    - Move one slot to the right.\n" + //
                    "    - Continue with state A.\n" + //
                    "\n" + //
                    "In state E:\n" + //
                    "  If the current value is 0:\n" + //
                    "    - Write the value 1.\n" + //
                    "    - Move one slot to the left.\n" + //
                    "    - Continue with state A.\n" + //
                    "  If the current value is 1:\n" + //
                    "    - Write the value 0.\n" + //
                    "    - Move one slot to the right.\n" + //
                    "    - Continue with state B.\n" + //
                    "\n" + //
                    "In state F:\n" + //
                    "  If the current value is 0:\n" + //
                    "    - Write the value 0.\n" + //
                    "    - Move one slot to the right.\n" + //
                    "    - Continue with state C.\n" + //
                    "  If the current value is 1:\n" + //
                    "    - Write the value 0.\n" + //
                    "    - Move one slot to the right.\n" + //
                    "    - Continue with state E.";

    public static void main(final String[] args) {
        Machine machine = getMachine();

        String state = machine.startState;
        for (int i = 0; i < machine.checksumAfter; i++) {
            StateDescription stateDescription = machine.stateDescriptions.get(state);

            Integer currentValue = machine.tape.computeIfAbsent(machine.tapeLocation, k -> 0);

            final Action action;
            switch (currentValue) {
                case 0:
                    action = stateDescription.zeroAction;
                    break;
                case 1:
                    action = stateDescription.oneAction;
                    break;
                default:
                    throw new IllegalStateException();
            }

            state = action.nextState;
            machine.tape.put(machine.tapeLocation, action.writeValue);
            machine.tapeLocation += action.locationDelta;
        }
        System.out.println(machine.getChecksum());
    }

    private static Machine getMachine() {
        List<String> lines = new BufferedReader(new StringReader(INPUT)).lines() //
                        .collect(Collectors.toList());

        String line = lines.get(0);
        Matcher beginStateMatcher = Pattern.compile("Begin in state (.*).").matcher(line);
        beginStateMatcher.matches();
        String beginState = beginStateMatcher.group(1);

        line = lines.get(1);
        Matcher stepMatcher = Pattern.compile("Perform a diagnostic checksum after (\\d*) steps.").matcher(line);
        stepMatcher.matches();
        int checksumAfter = Integer.valueOf(stepMatcher.group(1));

        Machine machine = new Machine(beginState, checksumAfter);

        Pattern statePattern = Pattern.compile("In state (.*):");
        Pattern writeValuePattern = Pattern.compile("    - Write the value (\\d).");
        Pattern movePattern = Pattern.compile("    - Move one slot to the (.*).");
        Pattern nextStatePattern = Pattern.compile("    - Continue with state (.*).");
        for (int i = 3; i < lines.size(); i++) {
            String state = getNextStep(statePattern, lines.get(i++));

            i++;
            int writeValue = getWriteValue(lines.get(i++), writeValuePattern);
            int moveDelta = getMoveDelta(movePattern, lines.get(i++));
            String nextStep = getNextStep(nextStatePattern, lines.get(i++));
            Action zeroAction = new Action(writeValue, moveDelta, nextStep);
            i++;
            writeValue = getWriteValue(lines.get(i++), writeValuePattern);
            moveDelta = getMoveDelta(movePattern, lines.get(i++));
            nextStep = getNextStep(nextStatePattern, lines.get(i++));
            Action oneAction = new Action(writeValue, moveDelta, nextStep);

            machine.stateDescriptions.put(state, new StateDescription(zeroAction, oneAction));
        }
        return machine;
    }

    private static String getNextStep(final Pattern nextStatePattern, final String line) {
        Matcher nextStateMatcher = nextStatePattern.matcher(line);
        nextStateMatcher.matches();
        String nextStep = nextStateMatcher.group(1);
        return nextStep;
    }

    private static int getMoveDelta(final Pattern movePattern, final String line) {
        Matcher zeroDirectionMatcher = movePattern.matcher(line);
        zeroDirectionMatcher.matches();
        String direction = zeroDirectionMatcher.group(1);
        final int moveDelta;
        switch (direction) {
            case "left":
                moveDelta = -1;
                break;
            case "right":
                moveDelta = 1;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return moveDelta;
    }

    private static int getWriteValue(final String line, final Pattern writeValuePattern) {
        Matcher writeValueMatcher = writeValuePattern.matcher(line);
        writeValueMatcher.matches();
        return Integer.valueOf(writeValueMatcher.group(1));
    }

    private static final class Machine {
        private final String startState;
        private final int checksumAfter;
        private final Map<Integer, Integer> tape = new HashMap<>();
        private int tapeLocation = 0;

        private final Map<String, StateDescription> stateDescriptions = new HashMap<>();

        public Machine(final String startState, final int checksumAfter) {
            super();
            this.startState = startState;
            this.checksumAfter = checksumAfter;
        }

        public long getChecksum() {
            return tape.values().stream().collect(Collectors.summarizingInt(i -> i)).getSum();
        }

        public void setTapeLocation(final int tapeLocation) {
            this.tapeLocation = tapeLocation;
        }

    }
    private static final class StateDescription {
        private final Action zeroAction;
        private final Action oneAction;

        public StateDescription(final Action zeroAction, final Action oneAction) {
            this.zeroAction = zeroAction;
            this.oneAction = oneAction;
        }

    }

    private static final class Action {
        private final int writeValue;
        private final int locationDelta;
        private final String nextState;

        public Action(final int writeValue, final int locationDelta, final String nextState) {
            this.writeValue = writeValue;
            this.locationDelta = locationDelta;
            this.nextState = nextState;
        }

    }

}
