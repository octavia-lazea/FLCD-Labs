import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Grammar grammar;
    private String sequence;
    private String grammarFile;
    private ArrayList<String> inputStack;
    private ArrayList<Pair<String, Integer>> workingStack;
    private String state;
    private Integer position;

    public Parser(String sequence, String fileName) throws FileNotFoundException {
        this.sequence = sequence;
        this.grammarFile = fileName;
        this.grammar = new Grammar(fileName);
        this.inputStack = new ArrayList<>(List.of(this.grammar.getS()));
        this.workingStack = new ArrayList<>();
        this.state = "q";
        this.position = 0;
    }

    private void expand() {
        // the head of the input stack is a non-terminal
        String first = popInputStack(); // which is a non-terminal
        this.workingStack.add(new Pair<>(first, 0));
        String production = this.grammar.getProduction(first).get(0);
        ArrayList<String> temp = new ArrayList<>();
        temp.add(production);
        temp.addAll(this.inputStack);
        this.inputStack = temp;
    }

    private void advance() {
        // the head of the input stack is a terminal =  current symbol from input
        String first = popInputStack(); // which is a non-terminal
        this.workingStack.add(new Pair<>(first, -1));
        this.position += 1;
    }

    private void momentaryInsuccess() {
        // the head of the input stack is a terminal != current symbol from input
        this.state = "b";
    }

    private void back() {
        this.position -= 1;
        Pair<String, Integer> nt = popWorkingStack();
        ArrayList<String> temp = new ArrayList<>();
        temp.add(nt.getValue1());
        temp.addAll(this.inputStack);
        this.inputStack = temp;

    }

    private void anotherTry() {
        Pair<String, Integer> last = popWorkingStack(); // non-terminal
        ArrayList<String> productions = this.grammar.getProduction(last.value1);
        if (last.value2 + 1 < productions.size()) {
            popWorkingStack();
            this.workingStack.add(new Pair<>(last.value1, last.value2 + 1));
            if (productions.get(last.value2).length() > 0) {
                this.inputStack.subList(0, productions.get(last.value2).length()).clear();
            }

            ArrayList<String> temp = new ArrayList<>();
            temp.add(productions.get(last.value2 + 1));
            temp.addAll(this.inputStack);
            this.inputStack = temp;
            this.state = "q";
        } else {
            if (this.position == 0 && last.value1.equals(grammar.getS())) {
                this.state = "e";
            }
            popWorkingStack();
            if (productions.get(last.value2).length() > 0)
                this.inputStack.subList(0, productions.get(last.value2).length()).clear();
            this.inputStack.add(0, last.value1);
            this.state = "b";
        }
    }

    private void success() {
        this.state = "f";
    }

    public void parse() {
        while (!this.state.equals("f") && !this.state.equals("e")) {
            if (this.state.equals("q")) {
                if (this.position == this.sequence.length() + 1 && this.inputStack.isEmpty())
                    success();
                else if (this.grammar.isNonterminal(this.inputStack.get(0))) // is a non-terminal
                    expand();
                else if (this.grammar.isTerminal(this.inputStack.get(0)))
                    advance();
                else momentaryInsuccess();
            } else if (this.state.equals("b")) {
                if (this.grammar.isTerminal(this.workingStack.get(0).value1))
                    back();
                else anotherTry();
            }
        }
        if (this.state.equals("e"))
            System.out.println("Error");
        else System.out.println("Sequence accepted");
    }

    String popInputStack() {
        return this.inputStack.remove(0);
    }

    Pair<String, Integer> popWorkingStack() {
        return this.workingStack.remove(this.workingStack.size() - 1);
    }

}
