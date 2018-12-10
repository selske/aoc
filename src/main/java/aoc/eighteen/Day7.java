package aoc.eighteen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Day7 {

    private static final String INPUT = "Step F must be finished before step P can begin.\r\n" + "Step R must be finished before step J can begin.\r\n"
            + "Step X must be finished before step H can begin.\r\n" + "Step L must be finished before step N can begin.\r\n" + "Step U must be finished before step Z can begin.\r\n"
            + "Step B must be finished before step C can begin.\r\n" + "Step S must be finished before step C can begin.\r\n" + "Step N must be finished before step Y can begin.\r\n"
            + "Step I must be finished before step J can begin.\r\n" + "Step H must be finished before step K can begin.\r\n" + "Step G must be finished before step Z can begin.\r\n"
            + "Step Q must be finished before step V can begin.\r\n" + "Step E must be finished before step P can begin.\r\n" + "Step P must be finished before step W can begin.\r\n"
            + "Step J must be finished before step D can begin.\r\n" + "Step V must be finished before step W can begin.\r\n" + "Step T must be finished before step D can begin.\r\n"
            + "Step Z must be finished before step A can begin.\r\n" + "Step K must be finished before step A can begin.\r\n" + "Step Y must be finished before step O can begin.\r\n"
            + "Step O must be finished before step W can begin.\r\n" + "Step C must be finished before step M can begin.\r\n" + "Step D must be finished before step A can begin.\r\n"
            + "Step W must be finished before step M can begin.\r\n" + "Step M must be finished before step A can begin.\r\n" + "Step C must be finished before step A can begin.\r\n"
            + "Step F must be finished before step Z can begin.\r\n" + "Step I must be finished before step A can begin.\r\n" + "Step W must be finished before step A can begin.\r\n"
            + "Step T must be finished before step C can begin.\r\n" + "Step S must be finished before step K can begin.\r\n" + "Step B must be finished before step J can begin.\r\n"
            + "Step O must be finished before step A can begin.\r\n" + "Step Q must be finished before step P can begin.\r\n" + "Step G must be finished before step M can begin.\r\n"
            + "Step R must be finished before step T can begin.\r\n" + "Step B must be finished before step G can begin.\r\n" + "Step J must be finished before step O can begin.\r\n"
            + "Step X must be finished before step E can begin.\r\n" + "Step X must be finished before step C can begin.\r\n" + "Step H must be finished before step Y can begin.\r\n"
            + "Step Y must be finished before step A can begin.\r\n" + "Step X must be finished before step W can begin.\r\n" + "Step H must be finished before step A can begin.\r\n"
            + "Step X must be finished before step A can begin.\r\n" + "Step I must be finished before step M can begin.\r\n" + "Step G must be finished before step J can begin.\r\n"
            + "Step N must be finished before step G can begin.\r\n" + "Step D must be finished before step M can begin.\r\n" + "Step L must be finished before step D can begin.\r\n"
            + "Step V must be finished before step T can begin.\r\n" + "Step I must be finished before step Y can begin.\r\n" + "Step S must be finished before step J can begin.\r\n"
            + "Step K must be finished before step Y can begin.\r\n" + "Step F must be finished before step R can begin.\r\n" + "Step U must be finished before step T can begin.\r\n"
            + "Step Z must be finished before step M can begin.\r\n" + "Step T must be finished before step Z can begin.\r\n" + "Step B must be finished before step I can begin.\r\n"
            + "Step E must be finished before step K can begin.\r\n" + "Step N must be finished before step J can begin.\r\n" + "Step X must be finished before step Q can begin.\r\n"
            + "Step F must be finished before step Y can begin.\r\n" + "Step H must be finished before step P can begin.\r\n" + "Step Z must be finished before step D can begin.\r\n"
            + "Step V must be finished before step O can begin.\r\n" + "Step E must be finished before step C can begin.\r\n" + "Step V must be finished before step C can begin.\r\n"
            + "Step P must be finished before step A can begin.\r\n" + "Step B must be finished before step N can begin.\r\n" + "Step S must be finished before step W can begin.\r\n"
            + "Step P must be finished before step D can begin.\r\n" + "Step L must be finished before step W can begin.\r\n" + "Step D must be finished before step W can begin.\r\n"
            + "Step K must be finished before step C can begin.\r\n" + "Step L must be finished before step M can begin.\r\n" + "Step R must be finished before step O can begin.\r\n"
            + "Step F must be finished before step L can begin.\r\n" + "Step R must be finished before step H can begin.\r\n" + "Step K must be finished before step O can begin.\r\n"
            + "Step T must be finished before step W can begin.\r\n" + "Step R must be finished before step K can begin.\r\n" + "Step C must be finished before step W can begin.\r\n"
            + "Step N must be finished before step T can begin.\r\n" + "Step R must be finished before step P can begin.\r\n" + "Step E must be finished before step M can begin.\r\n"
            + "Step G must be finished before step T can begin.\r\n" + "Step U must be finished before step K can begin.\r\n" + "Step Q must be finished before step D can begin.\r\n"
            + "Step U must be finished before step S can begin.\r\n" + "Step J must be finished before step V can begin.\r\n" + "Step P must be finished before step Y can begin.\r\n"
            + "Step X must be finished before step Z can begin.\r\n" + "Step U must be finished before step H can begin.\r\n" + "Step H must be finished before step M can begin.\r\n"
            + "Step I must be finished before step C can begin.\r\n" + "Step V must be finished before step M can begin.\r\n" + "Step N must be finished before step I can begin.\r\n"
            + "Step B must be finished before step K can begin.\r\n" + "Step R must be finished before step Q can begin.\r\n" + "Step O must be finished before step C can begin.";

    private static final Pattern PATTERN = Pattern.compile("Step (?<task>.*) must be finished before step (?<next>.*) can begin.");

    private static final class Task {

        private final String name;

        private final int amountOfWork;
        private final List<Task> next = new ArrayList<>();
        private final Set<Task> dependencies = new HashSet<>();

        public Task(final String name) {
            this.name = name;
            this.amountOfWork = 60 + name.charAt(0) - 'A' + 1;
        }

        public String getName() {
            return name;
        }

        public int getAmountOfWork() {
            return amountOfWork;
        }

        public List<Task> getNext() {
            return next;
        }

        public Set<Task> getDependencies() {
            return dependencies;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            return result;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            Task other = (Task) obj;
            if (name == null) {
                return other.name == null;
            } else return name.equals(other.name);
        }

    }

    private static final class Worker implements Comparable<Worker> {

        private Task task;
        private int workLeft;

        public void assignTask(final Task task) {
            this.task = task;
            this.workLeft = task.getAmountOfWork();
        }

        public void doWork(final int amountOfWork) {
            this.workLeft -= amountOfWork;
        }

        public int getWorkLeft() {
            return workLeft;
        }

        @Override
        public int compareTo(final Worker o) {
            int dif = workLeft - o.workLeft;
            if (dif != 0) {
                return dif;
            }
            return task.name.compareTo(o.task.name);
        }

        @Override
        public String toString() {
            return "w" + (task != null ? task.name : "") + " (" + workLeft + ")";
        }

    }

    private static String part1() {
        Map<String, Task> tasks = new ConcurrentHashMap<>();

        new BufferedReader(new StringReader(INPUT)).lines() //
                .forEach(line -> {
                    Matcher matcher = PATTERN.matcher(line);
                    matcher.matches();
                    String taskName = matcher.group("task");
                    String nextName = matcher.group("next");

                    Task task = tasks.computeIfAbsent(taskName, (k) -> new Task(k));
                    task.getNext().add(tasks.computeIfAbsent(nextName, (k) -> new Task(k)));
                });

        tasks.values().forEach(t -> {
            t.next.forEach(n -> n.dependencies.add(t));
        });

        List<Task> rootTasks = tasks.values().stream()//
                .filter(t -> t.dependencies.isEmpty()) //
                .collect(Collectors.toList());

        Task target = tasks.values().stream()//
                .filter(t -> t.next.isEmpty()) //
                .findFirst().get();

        StringBuilder currentOutput = new StringBuilder();
        Set<Task> solvedTasks = new HashSet<>();
        Set<Task> availableTasks = new HashSet<>(rootTasks);
        while (!availableTasks.isEmpty()) {
            availableTasks.stream() //
                    .sorted(Comparator.comparing(Task::getName)) //
                    .filter(t -> !solvedTasks.contains(t)) //
                    .filter(t -> t.dependencies.isEmpty()) //
                    .findFirst() //
                    .ifPresent(nextTask -> {
                        solvedTasks.add(nextTask);
                        currentOutput.append(nextTask.name);
                        availableTasks.addAll(nextTask.next.stream().filter(t -> !target.equals(t)).collect(Collectors.toList()));
                    });
            availableTasks.removeAll(solvedTasks);
            tasks.values().stream().forEach(t -> t.dependencies.removeAll(solvedTasks));
        }
        currentOutput.append(target.name);
        return currentOutput.toString();
    }

    private static int part2() {
        Map<String, Task> tasks = new ConcurrentHashMap<>();

        new BufferedReader(new StringReader(INPUT)).lines() //
                .forEach(line -> {
                    Matcher matcher = PATTERN.matcher(line);
                    matcher.matches();
                    String taskName = matcher.group("task");
                    String nextName = matcher.group("next");

                    Task task = tasks.computeIfAbsent(taskName, (k) -> new Task(k));
                    task.getNext().add(tasks.computeIfAbsent(nextName, (k) -> new Task(k)));
                });

        tasks.values().forEach(t -> {
            t.next.forEach(n -> n.getDependencies().add(t));
        });

        System.out.println(tasks.size());

        List<Task> rootTasks = tasks.values().stream()//
                .filter(t -> t.getDependencies().isEmpty()) //
                .collect(Collectors.toList());

        Task target = tasks.values().stream()//
                .filter(t -> t.next.isEmpty()) //
                .findFirst().get();

        Queue<Worker> availableWorkers = new ArrayBlockingQueue<>(5);
        for (int i = 0; i < 5; i++) {
            availableWorkers.add(new Worker());
        }

        List<Worker> activeWorkers = new ArrayList<>();

        StringBuilder currentOutput = new StringBuilder();
        Set<Task> unavailableTasks = new HashSet<>();
        Set<Task> availableTasks = new HashSet<>(rootTasks);
        int timeTaken = 0;
        submitPossibleTasks(availableWorkers, activeWorkers, unavailableTasks, availableTasks);
        while (!availableTasks.isEmpty() || !activeWorkers.isEmpty()) {
            System.out.println("xxx active workers: " + activeWorkers.stream().sorted().collect(Collectors.toList()));
            Worker first = activeWorkers.stream().sorted().findFirst().get();
            timeTaken += first.workLeft;
            // System.out.println("time taken: " + timeTaken);

            List<Worker> completedWorkers = activeWorkers.stream() //
                    .sorted() //
//                    .takeWhile(t -> t.workLeft == first.workLeft) //
                    .collect(Collectors.toList());

            activeWorkers.stream().skip(1).forEach(w -> w.doWork(first.workLeft));

            System.out.println("completed: " + completedWorkers);
            completedWorkers.forEach(w -> {
                tasks.values().stream().forEach(t -> t.dependencies.remove(w.task));
                activeWorkers.remove(w);
                availableWorkers.add(w);
                currentOutput.append(w.task.name);
                availableTasks.addAll(w.task.next);

                submitPossibleTasks(availableWorkers, activeWorkers, unavailableTasks, availableTasks);
            });
            System.out.println(timeTaken + " -> " + currentOutput);

        }
        System.out.println(currentOutput.toString());
        return timeTaken;
    }

    private static void submitPossibleTasks(final Queue<Worker> availableWorkers, final List<Worker> activeWorkers, final Set<Task> unavailableTasks, final Set<Task> availableTasks) {
        System.out.println("available tasks: " + availableTasks.stream().filter(t -> t.dependencies.isEmpty()).collect(toList()));
        availableTasks.stream() //
                .sorted(Comparator.comparing(Task::getName)) //
                .filter(t -> !unavailableTasks.contains(t)) //
                .filter(t -> t.dependencies.isEmpty()) //
//                .takeWhile(t -> !availableWorkers.isEmpty()) //
                .forEach(nextTask -> {
                    System.out.println("picking up " + nextTask.name);
                    unavailableTasks.add(nextTask);
                    availableTasks.remove(nextTask);
                    Worker worker = availableWorkers.poll();
                    worker.assignTask(nextTask);
                    activeWorkers.add(worker);
                });
    }

    public static void main(final String[] args) {
        // System.out.println(Day7.part1());
        System.out.println(Day7.part2());
    }

}
