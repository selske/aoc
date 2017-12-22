package aoc.seventeen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day18 {

    private static final String INPUT =
            // "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0L\nrcv a\njgz
            // a -1\nset a 1\njgz a -2";
            "set i 31\nset a 1\nmul p 17\njgz p p\nmul a 2\nadd i -1\njgz i -2\nadd a -1\nset i 127\nset p 618\nmul p 8505\nmod p a\nmul p 129749\nadd p 12345\nmod p a\nset b p\nmod b 10000\nsnd b\nadd i -1\njgz i -9\njgz a 3\nrcv b\njgz b -1\nset f 0\nset i 126\nrcv a\nrcv b\nset p a\nmul p -1\nadd p b\njgz p 4\nsnd a\nset a b\njgz 1 3\nsnd b\nset f 1\nadd i -1\njgz i -11\nsnd a\njgz f -16\njgz a -19";

    public static void main(final String[] args) {

        BufferedReader reader = new BufferedReader(new StringReader(INPUT));
        List<String> lines = reader.lines().collect(Collectors.toList());

        List<Program> programs = LongStream.range(0, 1).mapToObj(Program::new).collect(Collectors.toList());

        int activeProgram = 0;
        Program currentProgram = programs.get(activeProgram);
        loop(currentProgram, lines);
    }

    private static final class Program {
        Map<String, Long> registry = new HashMap<>();
        AtomicLong lastPlayedValue = new AtomicLong();
        AtomicLong sentCount = new AtomicLong();

        public Program(final Long id) {
            registry.put("p", id);
        }

    }

    private static void loop(final Program program, final List<String> lines) {
        Pattern setValPattern = Pattern.compile("set ([a-z]) (-?\\d*)");
        Pattern setPattern = Pattern.compile("set ([a-z]) ([a-z])");
        Pattern addValPattern = Pattern.compile("add ([a-z]) (-?\\d*)");
        Pattern addPattern = Pattern.compile("add ([a-z]) ([a-z])");
        Pattern mulValPattern = Pattern.compile("mul ([a-z]) (-?\\d*)");
        Pattern mulPattern = Pattern.compile("mul ([a-z]) ([a-z])");
        Pattern modValPattern = Pattern.compile("mod ([a-z]) (-?\\d*)");
        Pattern modPattern = Pattern.compile("mod ([a-z]) ([a-z])");
        Pattern sndPattern = Pattern.compile("snd ([a-z])");
        Pattern rcvPattern = Pattern.compile("rcv ([a-z])");
        Pattern jgzValPattern = Pattern.compile("jgz ([a-z]) (-?\\d*)");
        Pattern jgzValValPattern = Pattern.compile("jgz (-?\\d*) (-?\\d*)");
        Pattern jgzPattern = Pattern.compile("jgz ([a-z]) ([a-z])");

        Map<String, Long> registry = program.registry;
        AtomicLong lastPlayedValue = program.lastPlayedValue;
        AtomicLong sentCount = program.sentCount;
        long waitingCount = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            Matcher setValMatcher = setValPattern.matcher(line);
            if (setValMatcher.matches()) {
                registry.put(setValMatcher.group(1), Long.valueOf(setValMatcher.group(2)));
                continue;
            }
            Matcher setMatcher = setPattern.matcher(line);
            if (setMatcher.matches()) {
                String otherRegister = setMatcher.group(2);
                Long otherVal = registry.computeIfAbsent(otherRegister, v -> 0L);
                registry.put(setMatcher.group(1), otherVal);
                continue;
            }
            Matcher addValMatcher = addValPattern.matcher(line);
            if (addValMatcher.matches()) {
                String register = addValMatcher.group(1);
                Long value = Long.valueOf(addValMatcher.group(2));
                Long curVal = registry.computeIfAbsent(register, v -> 0L);
                registry.put(register, curVal + value);
                continue;
            }
            Matcher addMatcher = addPattern.matcher(line);
            if (addMatcher.matches()) {
                String register = addMatcher.group(1);
                String otherRegister = addMatcher.group(2);
                Long curVal = registry.computeIfAbsent(register, v -> 0L);
                Long otherVal = registry.computeIfAbsent(otherRegister, v -> 0L);
                registry.put(register, curVal + otherVal);
                continue;
            }
            Matcher mulMatcher = mulPattern.matcher(line);
            if (mulMatcher.matches()) {
                String register = mulMatcher.group(1);
                String otherRegister = mulMatcher.group(2);
                Long curVal = registry.computeIfAbsent(register, v -> 0L);
                Long otherVal = registry.computeIfAbsent(otherRegister, v -> 0L);
                registry.put(register, curVal * otherVal);
                continue;
            }
            Matcher mulValMatcher = mulValPattern.matcher(line);
            if (mulValMatcher.matches()) {
                String register = mulValMatcher.group(1);
                Long val = Long.valueOf(mulValMatcher.group(2));
                Long curVal = registry.computeIfAbsent(register, v -> 0L);
                registry.put(register, curVal * val);
                continue;
            }
            Matcher sndMatcher = sndPattern.matcher(line);
            if (sndMatcher.matches()) {
                String register = sndMatcher.group(1);
                sentCount.incrementAndGet();
                waitingCount++;
                lastPlayedValue.set(registry.get(register));
                continue;
            }
            Matcher rcvMatcher = rcvPattern.matcher(line);
            if (rcvMatcher.matches()) {
                if (waitingCount == 0) {
                    System.out.println("sc: " + sentCount);
                    return;
                }
                waitingCount--;
                String register = rcvMatcher.group(1);
                Long curVal = registry.computeIfAbsent(register, v -> 0L);
                if (curVal != 0L) {
                    System.out.println("lpv: " + lastPlayedValue);
                    registry.put(register, lastPlayedValue.get());
                    return;
                }
                continue;
            }
            Matcher modValMatcher = modValPattern.matcher(line);
            if (modValMatcher.matches()) {
                String register = modValMatcher.group(1);
                Long value = Long.valueOf(modValMatcher.group(2));
                Long curVal = registry.computeIfAbsent(register, v -> 0L);
                registry.put(register, curVal % value);
                continue;
            }
            Matcher modMatcher = modPattern.matcher(line);
            if (modMatcher.matches()) {
                String register = modMatcher.group(1);
                String otherRegister = modMatcher.group(2);
                Long curVal = registry.computeIfAbsent(register, v -> 0L);
                Long otherVal = registry.computeIfAbsent(otherRegister, v -> 0L);
                registry.put(register, curVal % otherVal);
                continue;
            }
            Matcher jgzValMatcher = jgzValPattern.matcher(line);
            if (jgzValMatcher.matches()) {
                String register = jgzValMatcher.group(1);
                Long value = Long.valueOf(jgzValMatcher.group(2));
                Long curVal = registry.computeIfAbsent(register, v -> 0L);
                if (curVal > 0L) {
                    i += (value - 1);
                }
                continue;
            }
            Matcher jgzValValMatcher = jgzValValPattern.matcher(line);
            if (jgzValValMatcher.matches()) {
                Long curVal = Long.valueOf(jgzValValMatcher.group(1));
                Long value = Long.valueOf(jgzValValMatcher.group(2));
                if (curVal > 0L) {
                    i += (value - 1);
                }
                continue;
            }
            Matcher jgzMatcher = jgzPattern.matcher(line);
            if (jgzMatcher.matches()) {
                String register = jgzMatcher.group(1);
                String otherRegister = jgzMatcher.group(2);
                Long curVal = registry.computeIfAbsent(register, v -> 0L);
                Long otherVal = registry.computeIfAbsent(otherRegister, v -> 0L);
                if (curVal > 0L) {
                    i += (otherVal - 1);
                }
                continue;
            }
            throw new IllegalArgumentException(line);
        }
        throw new IllegalStateException();
    }

}
