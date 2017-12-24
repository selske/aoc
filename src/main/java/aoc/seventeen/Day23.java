package aoc.seventeen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day23 {

    private static final String INPUT =
                    // "set a 1\nsub a 2\nmul a a\nmod a 5\nsnd a\nset a 0L\nrcv a\njnz
                    // a -1\nset a 1\njnz a -2";
                    "set b 93\n" + "set c b\n" + "jnz a 2\n" + "jnz 1 5\n" + "mul b 100\n" + "sub b -100000\n" + "set c b\n" + "sub c -17000\n" + "set f 1\n" + "set d 2\n" + "set e 2\n" + "set g d\n"
                                    + "mul g e\n" + "sub g b\n" + "jnz g 2\n" + "set f 0\n" + "sub e -1\n" + "set g e\n" + "sub g b\n" + "jnz g -8\n" + "sub d -1\n" + "set g d\n" + "sub g b\n"
                                    + "jnz g -13\n" + "jnz f 2\n" + "sub h -1\n" + "set g b\n" + "sub g c\n" + "jnz g 2\n" + "jnz 1 3\n" + "sub b -17\n" + "jnz 1 -23";

    public static void main(final String[] args) {
        List<Consumer<Program>> lines = getLines();

        Program currentProgram = new Program();
        // loop(currentProgram, lines);

        currentProgram = new Program();
        currentProgram.registry.put("a", 1L);
        loop(currentProgram, lines);
    }

    private static List<Consumer<Program>> getLines() {
        Pattern setValPattern = Pattern.compile("set ([a-z]) (-?\\d*)");
        Pattern setPattern = Pattern.compile("set ([a-z]) ([a-z])");
        Pattern subValPattern = Pattern.compile("sub ([a-z]) (-?\\d*)");
        Pattern subPattern = Pattern.compile("sub ([a-z]) ([a-z])");
        Pattern mulValPattern = Pattern.compile("mul ([a-z]) (-?\\d*)");
        Pattern mulPattern = Pattern.compile("mul ([a-z]) ([a-z])");
        Pattern jnzValPattern = Pattern.compile("jnz ([a-z]) (-?\\d*)");
        Pattern jnzValValPattern = Pattern.compile("jnz (-?\\d*) (-?\\d*)");
        Pattern jnzPattern = Pattern.compile("jnz ([a-z]) ([a-z])");

        List<Consumer<Program>> lines = new BufferedReader(new StringReader(INPUT)).lines() //
                        .map(line -> {
                            Matcher setValMatcher = setValPattern.matcher(line);
                            if (setValMatcher.matches()) {
                                return new SetValue(setValMatcher.group(1), Long.valueOf(setValMatcher.group(2)));
                            }
                            Matcher setMatcher = setPattern.matcher(line);
                            if (setMatcher.matches()) {
                                return new Set(setMatcher.group(1), setMatcher.group(2));
                            }
                            Matcher subValMatcher = subValPattern.matcher(line);
                            if (subValMatcher.matches()) {
                                return new SubValue(subValMatcher.group(1), Long.valueOf(subValMatcher.group(2)));
                            }
                            Matcher subMatcher = subPattern.matcher(line);
                            if (subMatcher.matches()) {
                                return new Sub(subMatcher.group(1), subMatcher.group(2));
                            }
                            Matcher mulMatcher = mulPattern.matcher(line);
                            if (mulMatcher.matches()) {
                                return new Mul(mulMatcher.group(1), mulMatcher.group(2));
                            }
                            Matcher mulValMatcher = mulValPattern.matcher(line);
                            if (mulValMatcher.matches()) {
                                return new MulValue(mulValMatcher.group(1), Long.valueOf(mulValMatcher.group(2)));
                            }
                            Matcher jnzValMatcher = jnzValPattern.matcher(line);
                            if (jnzValMatcher.matches()) {
                                return new JnzValue(jnzValMatcher.group(1), Long.valueOf(jnzValMatcher.group(2)));
                            }
                            Matcher jnzValValMatcher = jnzValValPattern.matcher(line);
                            if (jnzValValMatcher.matches()) {
                                return new JnzValueValue(Long.valueOf(jnzValValMatcher.group(1)), Long.valueOf(jnzValValMatcher.group(2)));
                            }
                            Matcher jnzMatcher = jnzPattern.matcher(line);
                            if (jnzMatcher.matches()) {
                                return new Jnz(jnzMatcher.group(1), jnzMatcher.group(2));
                            }
                            throw new IllegalArgumentException(line);
                        }) //
                        .collect(Collectors.toList());
        return lines;
    }

    private static final class Program {
        private int currentIndex = 0;
        private int mulCount = 0;
        private final Map<String, Long> registry = new HashMap<>();

        public Program() {
            // registry.put("a", 1L);
        }

        @SuppressWarnings("unused")
        public void setCurrentIndex(final int currentIndex) {
            this.currentIndex = currentIndex;
        }

        @SuppressWarnings("unused")
        public void setMulCount(final int mulCount) {
            this.mulCount = mulCount;
        }
    }

    private static void loop(final Program program, final List<Consumer<Program>> lines) {
        while (program.currentIndex < lines.size()) {
            Consumer<Program> line = lines.get(program.currentIndex);
            System.out.println(line + ": " + program.registry);
            line.accept(program);
        }
        System.out.println(program.mulCount);
        System.out.println(program.registry.get("h"));
    }

    private static final class SetValue implements Consumer<Program> {
        private final String register;
        private final long value;

        public SetValue(final String register, final long value) {
            this.register = register;
            this.value = value;
        }

        @Override
        public void accept(final Program program) {
            program.registry.put(register, value);
            program.currentIndex++;
        }

        @Override
        public String toString() {
            return "set " + register + " " + value;
        }
    }

    private static final class Set implements Consumer<Program> {
        private final String register;
        private final String otherRegister;

        public Set(final String register, final String otherRegister) {
            this.register = register;
            this.otherRegister = otherRegister;
        }

        @Override
        public void accept(final Program program) {
            program.registry.put(register, program.registry.computeIfAbsent(otherRegister, v -> 0L));
            program.currentIndex++;
        }

        @Override
        public String toString() {
            return "set " + register + " " + otherRegister;
        }
    }

    private static final class SubValue implements Consumer<Program> {
        private final String register;
        private final long value;

        public SubValue(final String register, final long value) {
            this.register = register;
            this.value = value;
        }

        @Override
        public void accept(final Program program) {
            program.registry.compute(register, (k, v) -> (v == null ? 0 : v) - value);
            program.currentIndex++;
        }

        @Override
        public String toString() {
            return "sub " + register + " " + value;
        }
    }

    private static final class Sub implements Consumer<Program> {
        private final String register;
        private final String otherRegister;

        public Sub(final String register, final String otherRegister) {
            this.register = register;
            this.otherRegister = otherRegister;
        }

        @Override
        public void accept(final Program program) {
            program.registry.compute(register, (k, v) -> (v == null ? 0 : v) - program.registry.computeIfAbsent(otherRegister, val -> 0L));
            program.currentIndex++;
        }

        @Override
        public String toString() {
            return "sub " + register + " " + otherRegister;
        }
    }

    private static final class MulValue implements Consumer<Program> {
        private final String register;
        private final long value;

        public MulValue(final String register, final long value) {
            this.register = register;
            this.value = value;
        }

        @Override
        public void accept(final Program program) {
            program.registry.compute(register, (k, v) -> (v == null ? 0 : v) * value);
            program.mulCount++;
            program.currentIndex++;
        }

        @Override
        public String toString() {
            return "mul " + register + " " + value;
        }
    }

    private static final class Mul implements Consumer<Program> {
        private final String register;
        private final String otherRegister;

        public Mul(final String register, final String otherRegister) {
            this.register = register;
            this.otherRegister = otherRegister;
        }

        @Override
        public void accept(final Program program) {
            program.registry.compute(register, (k, v) -> (v == null ? 0 : v) * program.registry.computeIfAbsent(otherRegister, val -> 0L));
            program.mulCount++;
            program.currentIndex++;
        }

        @Override
        public String toString() {
            return "mul " + register + " " + otherRegister;
        }
    }

    private static final class JnzValue implements Consumer<Program> {
        private final String register;
        private final long value;

        public JnzValue(final String register, final long value) {
            this.register = register;
            this.value = value;
        }

        @Override
        public void accept(final Program program) {
            if (program.registry.computeIfAbsent(register, v -> 0L) != 0) {
                program.currentIndex += value;
            } else {
                program.currentIndex++;
            }
        }

        @Override
        public String toString() {
            return "jnz " + register + " " + value;
        }
    }
    private static final class JnzValueValue implements Consumer<Program> {
        private final long value;
        private final long otherValue;

        public JnzValueValue(final long value, final long otherValue) {
            this.value = value;
            this.otherValue = otherValue;
        }

        @Override
        public void accept(final Program program) {
            if (value != 0) {
                program.currentIndex += otherValue;
            } else {
                program.currentIndex++;
            }
        }

        @Override
        public String toString() {
            return "jnz " + value + " " + otherValue;
        }
    }

    private static final class Jnz implements Consumer<Program> {
        private final String register;
        private final String otherRegister;

        public Jnz(final String register, final String otherRegister) {
            this.register = register;
            this.otherRegister = otherRegister;
        }

        @Override
        public void accept(final Program program) {
            if (program.registry.computeIfAbsent(register, v -> 0L) != 0) {
                program.currentIndex += program.registry.computeIfAbsent(otherRegister, v -> 0L);
            } else {
                program.currentIndex++;
            }
        }

        @Override
        public String toString() {
            return "jnz " + register + " " + otherRegister;
        }
    }

}
