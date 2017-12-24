package aoc.seventeen;

import java.io.BufferedReader;
import java.io.StringReader;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day24 {

    private static final String INPUT = // "0/2\n2/2\n2/3\n3/4\n3/5\n0/1\n10/1\n9/10";
                    "31/13\n" + "34/4\n" + "49/49\n" + "23/37\n" + "47/45\n" + "32/4\n" + "12/35\n" + "37/30\n" + "41/48\n" + "0/47\n" + "32/30\n" + "12/5\n" + "37/31\n" + "7/41\n" + "10/28\n"
                                    + "35/4\n" + "28/35\n" + "20/29\n" + "32/20\n" + "31/43\n" + "48/14\n" + "10/11\n" + "27/6\n" + "9/24\n" + "8/28\n" + "45/48\n" + "8/1\n" + "16/19\n" + "45/45\n"
                                    + "0/4\n" + "29/33\n" + "2/5\n" + "33/9\n" + "11/7\n" + "32/10\n" + "44/1\n" + "40/32\n" + "2/45\n" + "16/16\n" + "1/18\n" + "38/36\n" + "34/24\n" + "39/44\n"
                                    + "32/37\n" + "26/46\n" + "25/33\n" + "9/10\n" + "0/29\n" + "38/8\n" + "33/33\n" + "49/19\n" + "18/20\n" + "49/39\n" + "18/39\n" + "26/13\n" + "19/32";

    private static final class Component {
        private final int in;
        private final int out;

        public Component(final int in, final int out) {
            this.in = in;
            this.out = out;
        }

        @Override
        public String toString() {
            return in + "/" + out;
        }
    }

    public static void main(final String[] args) {
        Instant before = Instant.now();
        List<Component> components = new BufferedReader(new StringReader(INPUT)).lines() //
                        .map(line -> {
                            String[] split = line.split("/");
                            return new Component(Integer.valueOf(split[0]), Integer.valueOf(split[1]));
                        }) //
                        .collect(Collectors.toList());
        List<Node> nodes = new ArrayList<>();
        Node rootNode = new Node(null, new Component(0, 0), false);
        nodes.add(rootNode);
        List<Node> childNodes = expand(nodes, components);

        int maxStrength = childNodes.stream().sorted((n1, n2) -> n2.getStrength() - n1.getStrength()) //
                        .findFirst().get().getStrength();

        System.out.println(maxStrength);
        int longestStrength = childNodes.stream().sorted(((Comparator<Node>) ((n1, n2) -> n2.getBridge().size() - n1.getBridge().size()))//
                        .thenComparing(((n1, n2) -> n2.getStrength() - n1.getStrength()))) //
                        .findFirst().get().getStrength();
        System.out.println(longestStrength);
        Instant after = Instant.now();
        System.out.println(Duration.between(before, after));
    }

    private static List<Node> expand(final List<Node> nodes, final List<Component> components) {
        List<Node> childNodes = new ArrayList<>();
        nodes.forEach(node -> {
            components.forEach(component -> {
                int connectingPort;
                if (node.reversed) {
                    connectingPort = node.component.in;
                } else {
                    connectingPort = node.component.out;
                }
                if (node.isAncestor(component)) {
                    return;
                }
                if (component.in == connectingPort) {
                    node.children.add(new Node(node, component, false));
                } else if (component.out == connectingPort) {
                    node.children.add(new Node(node, component, true));
                }
            });
            childNodes.addAll(node.children);
            childNodes.addAll(expand(node.children, components));
        });
        return childNodes;
    }

    private static final class Node {
        private final Node parent;
        private final Component component;
        private final boolean reversed;
        private Integer strength;
        private List<Component> bridge;

        private final List<Node> children = new ArrayList<>();

        public Node(final Node parent, final Component component, final boolean reversed) {
            this.parent = parent;
            this.component = component;
            this.reversed = reversed;
        }

        public boolean isAncestor(final Component potentialAncestor) {
            if (parent == null) {
                return false;
            } else {
                if (parent.component.equals(potentialAncestor) || component.equals(potentialAncestor)) {
                    return true;
                } else {
                    return parent.isAncestor(potentialAncestor);
                }
            }
        }

        public int getStrength() {
            if (strength == null) {
                strength = component.in + component.out + (parent == null ? 0 : parent.getStrength());
            }
            return strength;
        }

        public List<Component> getBridge() {
            if (bridge == null) {
                bridge = new ArrayList<>();
                if (parent != null) {
                    bridge.addAll(parent.getBridge());
                }
                bridge.add(component);
            }
            return bridge;
        }
    }

}
