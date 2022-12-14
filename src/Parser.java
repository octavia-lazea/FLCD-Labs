import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Parser {
    private final Grammar grammar;
    private final List<String> sequence;
    private ArrayList<String> inputStack;
    private final ArrayList<Pair<String, Integer>> workingStack;
    private String state;
    private Integer position;
    private String sequenceFile;

    private String outputFile;

    public Parser(String sequenceFile, String outputFile, Grammar grammar) throws FileNotFoundException {
        this.sequence = new ArrayList<>();
       // this.sequence.addAll(Arrays.stream(sequence.split(" ")).toList());
        this.grammar = grammar;
        this.sequenceFile = sequenceFile;
        this.outputFile = outputFile;
        this.inputStack = new ArrayList<>(List.of(this.grammar.getS()));
        this.workingStack = new ArrayList<>();
        this.state = "q";
        this.position = 0;
        readSequenceFromFile();
    }

    private void readSequenceFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(this.sequenceFile));
        while (scanner.hasNextLine()){
            String character = scanner.nextLine();
            this.sequence.add(character);
        }
    }

    public ArrayList<Pair<String, Integer>> getWorkingStack() {
        return workingStack;
    }

    public Grammar getGrammar(){
        return grammar;
    }

    private void expand() {
        System.out.println("--> expand");
        // the head of the input stack is a non-terminal
        String head = popInputStack(); // which is a non-terminal
        this.workingStack.add(new Pair<>(head, 0));
        String production = this.grammar.getProduction(head).get(0);
        ArrayList<String> temp = new ArrayList<>();
        temp.addAll(Arrays.stream(production.split(" ")).toList());
        temp.addAll(this.inputStack);
        this.inputStack = temp;
    }

    private void advance() {
        System.out.println("--> advance");
        // the head of the input stack is a terminal =  current symbol from input
        String head = popInputStack(); // which is a non-terminal
        this.workingStack.add(new Pair<>(head, -1));
        this.position += 1;
    }

    private void momentaryInsuccess() {
        System.out.println("--> momentary insuccess");
        // the head of the input stack is a terminal != current symbol from input
        this.state = "b";
    }

    private void back() {
        System.out.println("--> back");
        //  this.position -= 1;
        Pair<String, Integer> nt = popWorkingStack();
        position -= 1;
        ArrayList<String> temp = new ArrayList<>();
        temp.add(nt.getValue1());
        temp.addAll(this.inputStack);
        this.inputStack = temp;
    }

    private void anotherTry() {
        System.out.println("--> another try");

        Pair<String, Integer> currentProduction = popWorkingStack(); // value1 = non-terminal
        ArrayList<String> productions = this.grammar.getProduction(currentProduction.value1);
        if (productions.size() - 1 > currentProduction.value2) {
            this.state = "q";
            this.workingStack.add(new Pair<>(currentProduction.value1, currentProduction.value2 + 1));
            if (productions.get(currentProduction.value2).length() > 0) {
                var prod = new ArrayList<>(Arrays.stream(productions.get(currentProduction.value2).split(" ")).toList());
                for (var ignored : prod)
                    popInputStack();
            }

            ArrayList<String> temp = new ArrayList<>();
            temp.addAll(Arrays.stream(productions.get(currentProduction.value2 + 1).split(" ")).toList());
            temp.addAll(this.inputStack);
            this.inputStack = temp;
        }
        else{
            var prod = new ArrayList<>(Arrays.stream(productions.get(currentProduction.value2).split(" ")).toList());
            for (var ignored : prod)
                popInputStack();
            this.inputStack.add(0, currentProduction.value1);
        }

    }

    private void success() {
        System.out.println("--> success");
        this.state = "f";
        this.position += 1;
    }

    public void parse() {
        while (!this.state.equals("f") && !this.state.equals("e")) {
            if (inputStack.isEmpty() && position != sequence.size() || (state.equals("b") && workingStack.isEmpty()))
                state = "e";
            if (this.state.equals("q")) {
                if (this.position == this.sequence.size() && this.inputStack.isEmpty())
                    success();
                else {
                    if (this.grammar.isNonterminal(getInputStackHead()))
                        expand();
                    else if (position < sequence.size() && getInputStackHead().equals(sequence.get(position)))
                        advance();
                    else momentaryInsuccess();
                }
            } else if (this.state.equals("b")) {
                if (this.grammar.isTerminal(getWorkingStackHead().value1))
                    back();
                else anotherTry();
            }
        }
        if (this.state.equals("e"))
            System.out.println("Error");
        else {
            System.out.println("Sequence accepted");
            System.out.println(printWorkingStack());
            ParserOutput parserOutput = new ParserOutput(this.workingStack, this.grammar);
            writeToFile(parserOutput.getOutputAsString());
            System.out.print(parserOutput.getOutputAsString());

        }
    }

    String popInputStack() {
        return this.inputStack.remove(0);
    }

    Pair<String, Integer> popWorkingStack() {
        return this.workingStack.remove(this.workingStack.size() - 1);
    }

    String getInputStackHead() {
        return this.inputStack.get(0);
    }

    Pair<String, Integer> getWorkingStackHead() {
        return this.workingStack.get(this.workingStack.size() - 1);
    }

    String printWorkingStack() {
        StringBuilder stringBuilder = new StringBuilder();
        this.workingStack.forEach(pair -> stringBuilder.append("(").append(pair.value1).append(" ,").append(pair.value2).append(")\n"));
        return stringBuilder.toString();
    }

    public void writeToFile(String output) {
        try (PrintStream printStream = new PrintStream(this.outputFile)) {
            printStream.println(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
