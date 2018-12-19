package aoc.eighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toUnmodifiableList;

public class Day16 {

    public static void main(final String[] args) throws IOException {
        long before = System.currentTimeMillis();
        List<String> lines = Files.readAllLines(Paths.get("src/main/java/aoc/eighteen/Day16"));
        Input input = parse(lines);

        System.out.println("part1: " + part1(input));
        System.out.println("part2: " + part2(input));
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

    private static long part1(final Input input) {
        return input.samples.stream()
                .filter(sample -> Arrays.stream(OpCode.values()) //
                        .map(opCode -> opCode.apply(sample.instruction.a, sample.instruction.b, sample.instruction.c, sample.registry))
                        .filter(sample.expectedOutput::equals)
                        .count() >= 3)
                .count();
    }

    private static int part2(final Input input) {
        Map<Integer, OpCode> resolvedOpCodes = resolveOpCodes(input);

        List<Integer> register = IntStream.range(0, 4).mapToObj(i -> 0).collect(toList());
        for (final Instruction instruction : input.instructions) {
            OpCode opCode = resolvedOpCodes.get(instruction.value);
            register = opCode.apply(instruction.a, instruction.b, instruction.c, register);
        }
        return register.get(0);
    }

    private static Map<Integer, OpCode> resolveOpCodes(final Input input) {
        List<OpCode> opCodes = new ArrayList<>(Arrays.asList(OpCode.values()));
        final List<Sample> samples = new ArrayList<>(input.samples);

        Map<Integer, OpCode> resolvedOpCodes = new HashMap<>();

        while (!opCodes.isEmpty()) {
            for (Iterator<Sample> iterator = samples.iterator(); iterator.hasNext(); ) {
                final Sample sample = iterator.next();
                List<OpCode> collect = opCodes.stream()
                        .filter(opCode -> sample.expectedOutput.equals(opCode.apply(sample.instruction.a, sample.instruction.b, sample.instruction.c, sample.registry)))
                        .collect(toList());

                if (collect.size() == 1) {
                    OpCode opCode = collect.get(0);
                    opCodes.remove(opCode);
                    resolvedOpCodes.put(sample.instruction.value, opCode);
                    iterator.remove();
                }
            }
        }
        return resolvedOpCodes;
    }

    private static final class Input {

        private final List<Sample> samples;
        private final List<Instruction> instructions;

        Input(final List<Sample> samples, final List<Instruction> instructions) {
            this.samples = unmodifiableList(samples);
            this.instructions = unmodifiableList(instructions);
        }

    }

    private static Input parse(final List<String> lines) {
        List<Sample> samples = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();
        for (int i = 0; i < lines.size(); ) {
            final String line = lines.get(i++);
            if (line.startsWith("Before")) {

                final String input = lines.get(i++);
                final String after = lines.get(i++);

                samples.add(new Sample(line, input, after));
            } else if (!"".equals(line)) {
                instructions.add(new Instruction(line));
            }
        }

        return new Input(samples, instructions);
    }

    private static final class Sample {

        private final List<Integer> registry, expectedOutput;
        private final Instruction instruction;

        Sample(String before, String input, String after) {
            registry = Arrays.stream(before.substring(before.indexOf("[") + 1, before.length() - 1)
                    .split(", "))
                    .map(Integer::valueOf)
                    .collect(toUnmodifiableList());
            expectedOutput = Arrays.stream(after.substring(after.indexOf("[") + 1, after.length() - 1)
                    .split(", "))
                    .map(Integer::valueOf)
                    .collect(toUnmodifiableList());

            instruction = new Instruction(input);
        }

    }

    private static final class Instruction {

        private final int value, a, b, c;

        Instruction(final String input) {
            String[] values = input.split(" ");

            value = Integer.valueOf(values[0]);
            a = Integer.valueOf(values[1]);
            b = Integer.valueOf(values[2]);
            c = Integer.valueOf(values[3]);
        }

    }

    private enum OpCode {
        addr((a, b, register) -> register.get(a) + register.get(b)),
        addi((a, b, register) -> register.get(a) + b),
        mulr((a, b, register) -> register.get(a) * register.get(b)),
        muli((a, b, register) -> register.get(a) * b),
        banr((a, b, register) -> register.get(a) & register.get(b)),
        bani((a, b, register) -> register.get(a) & b),
        borr((a, b, register) -> register.get(a) | register.get(b)),
        bori((a, b, register) -> register.get(a) | b),
        setr((a, b, register) -> register.get(a)),
        seti((a, b, register) -> a),
        gtir((a, b, register) -> a > register.get(b) ? 1 : 0),
        gtri((a, b, register) -> register.get(a) > b ? 1 : 0),
        gtrr((a, b, register) -> register.get(a) > register.get(b) ? 1 : 0),
        eqir((a, b, register) -> a == register.get(b) ? 1 : 0),
        eqri((a, b, register) -> register.get(a) == b ? 1 : 0),
        eqrr((a, b, register) -> register.get(a).equals(register.get(b)) ? 1 : 0);

        private final Operation operation;

        OpCode(final Operation operation) {
            this.operation = operation;
        }

        List<Integer> apply(int a, int b, int c, List<Integer> register) {
            ArrayList<Integer> newRegister = new ArrayList<>(register);
            newRegister.set(c, operation.apply(a, b, register));
            return newRegister;
        }
    }

    private interface Operation {

        int apply(int a, int b, List<Integer> register);

    }

}
