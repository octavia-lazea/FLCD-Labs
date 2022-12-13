import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ParserOutput {
    protected final List<Pair<String, Integer>> workingStack;
    protected final Grammar grammar;
    public ParserOutput(final List<Pair<String, Integer>> workingStack, final Grammar grammar) {
        this.workingStack = workingStack;
        this.grammar = grammar;
    }

    private List<List<String>> BFS(final Node root) {
        final Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        final List<Node> traversal = new ArrayList<>();
        final Map<Node, Node> parentOf = new HashMap<>();
        final Map<Node, Node> siblingOf = new HashMap<>();
        int currentIndex = 1;
        while (!queue.isEmpty()) {
            final Node node = queue.remove();
            node.index = currentIndex++;
            traversal.add(node);
            Node currentSibling = node;
            Node rightSibling = node.rightSibling;
            while (rightSibling != null) {
                if (!queue.contains(rightSibling)) {
                    queue.add(rightSibling);
                    parentOf.put(rightSibling, parentOf.get(node));
                    siblingOf.put(rightSibling, currentSibling);
                }
                currentSibling = rightSibling;
                rightSibling = rightSibling.rightSibling;
            }
            if (node.child != null) {
                queue.add(node.child);
                parentOf.put(node.child, node);
            }
        }

        final List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < traversal.size(); ++i) {
            final Node node = traversal.get(i);
            rows.add(List.of(
                    String.valueOf(i + 1),
                    node.value,
                    String.valueOf(parentOf.get(node) == null ? 0 : parentOf.get(node).index),
                    String.valueOf(siblingOf.get(node) == null ? 0 : siblingOf.get(node).index)
            ));
        }

        return rows;
    }

    public String getOutputAsString() {
        final List<Pair<String, Integer>> productions = workingStack
                .stream()
                .filter(pair -> pair.getValue2() != -1)
                .collect(Collectors.toList());

        final Pair<String, Integer> firstProduction = productions.get(0);
        final String firstNonterminal = firstProduction.getValue1();
        final List<String> firstRule = Arrays.stream(grammar.getProduction(firstNonterminal).get(firstProduction.getValue2()).split(" ")).toList();

        final Node root = new Node(firstNonterminal);
        root.child = buildTree(firstRule, productions);

        final List<List<String>> rows = BFS(root);

        return rows.stream().map(row -> String.join(",\t", row)).collect(Collectors.joining("\n"));
    }



    private Node buildTree(final List<String> rule, final List<Pair<String, Integer>> productions) {
        if (rule.isEmpty()) {
            return null;
        }
        final String symbol = rule.get(0);
        if (grammar.isTerminal(symbol)) {
            final Node node = new Node(symbol);
            node.rightSibling = buildTree(rule.subList(1, rule.size()), productions);
            return node;
        } else if (grammar.isNonterminal(symbol)) {
            final Node node = new Node(symbol);
            productions.remove(0);
            final Pair<String, Integer> firstProduction = productions.get(0);
            final String firstNonterminal = firstProduction.getValue1();
            final List<String> firstRule = Arrays.stream(grammar.getProduction(firstNonterminal).get(firstProduction.getValue2()).split(" ")).toList();
            node.child = buildTree(firstRule, productions);
            node.rightSibling = buildTree(rule.subList(1, rule.size()), productions);
            return node;
        } else {
            return new Node("ε");
        }
    }

    private static class Node {

        private final String value;

        private Node child;

        private Node rightSibling;

        private int index;

        public Node(String value){
            this.child = null;
            this.value = value;
            this.index = 0;
            this.rightSibling = null;
        }


        @Override
        public boolean equals(Object o) {
            return this == o;
//            if (o == null || getClass() != o.getClass()) return false;

        }

//        @Override
//        public int hashCode() {
//            return Objects.hash(uuid);
//        }

    }

}

