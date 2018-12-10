package aoc.eighteen;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day8 {

    private static final class Node {

        private final List<Node> children = new ArrayList<>();
        private final List<Integer> metadataEntries = new ArrayList<>();

        public int getSize() {
            int size = 2;
            size += children.stream().mapToInt(Node::getSize).sum();
            size += metadataEntries.size();
            return size;
        }

        int getMetadataValue() {
            int val = 0;
            val += children.stream().mapToInt(Node::getMetadataValue).sum();
            val += metadataEntries.stream().mapToInt(i -> i).sum();
            return val;
        }

        int getNodeValue() {
            if (children.isEmpty()) {
                return metadataEntries.stream().mapToInt(i -> i).sum();
            } else {
                int nodeValue = 0;
                for (int metaVal : metadataEntries) {
                    int index = metaVal - 1;
                    if (index < children.size()) {
                        nodeValue += children.get(index).getNodeValue();
                    }
                }
                return nodeValue;
            }
        }

    }

    private static Node parseNodes() throws IOException {
        String input = Files.readAllLines(Paths.get("src/main/java/aoc/eighteen/Day8")).get(0);
        int[] ints = Arrays.stream(input.split(" ")).mapToInt(Integer::valueOf).toArray();
        Node rootNode = new Node();
        parseChildNodes(ints, rootNode, 0, 1);
        return rootNode;
    }

    private static int parseChildNodes(final int[] ints, final Node parentNode, final int start, final int nrOfChildren) {
        int size = 0;
        int startIndex = start;
        for (int j = 0; j < nrOfChildren; j++) {
            Node n = new Node();
            parentNode.children.add(n);

            int nrOfChildNodes = ints[startIndex];
            int nrOfMetadataEntries = ints[startIndex + 1];

            int childLength = parseChildNodes(ints, n, startIndex + 2, nrOfChildNodes);

            int metadataEntriesStart = startIndex + 2 + childLength;
            for (int k = metadataEntriesStart; k < metadataEntriesStart + nrOfMetadataEntries; k++) {
                n.metadataEntries.add(ints[k]);
            }
            size += n.getSize();
            startIndex += n.getSize();
        }
        return size;
    }

    public static void main(final String[] args) throws IOException {
        long before = System.currentTimeMillis();
        Node rootNode = parseNodes();
        System.out.println("part 1: " + rootNode.getMetadataValue());
        System.out.println("part 2: " + rootNode.children.get(0).getNodeValue());
        System.out.println("took: " + (System.currentTimeMillis() - before) + "ms");
    }

}
