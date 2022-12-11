import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        // Symbol Table test
//        SymbolTable symbolTable = new SymbolTable(20);
//        symbolTable.add("a");
//        symbolTable.add("b");
//        symbolTable.add("c");
//        boolean v = symbolTable.add("c");
//        symbolTable.add("c");
//        symbolTable.add("d");
//        symbolTable.add("d");
//        System.out.println(symbolTable);
//        System.out.println(v);

//        MyScanner myScanner = new MyScanner("p1.txt");
//        try {
//            myScanner.scan();
//        }catch (FileNotFoundException e){
//            e.printStackTrace();
//        }
//
//          myScanner.writeToFile("ST.out", myScanner.getSymbolTable().toString());
//          myScanner.writeToFile("PIF.out", myScanner.getPif());

//        FiniteAutomaton fa = new FiniteAutomaton("FA_int.in", "FA_id.in");
//
//        String menuOptions = """
//                MENU
//                1: all states
//                2: initial state
//                3: final states
//                4: alphabet
//                5: all transitions
//                6: verify DFA
//                0: exit""";
//        Scanner scanner = new Scanner(System.in);
//        System.out.println(menuOptions);
//        while (true) {
//            System.out.println(">>");
//            String option = scanner.nextLine();
//            switch (option) {
//                case "1" -> {
//                    System.out.println("All states:");
//                    var states = fa.getStates();
//                    states.forEach(System.out::println);
//                }
//                case "2" -> {
//                    System.out.println("Initial state:");
//                    var initialState = fa.getInitialState();
//                    System.out.println(initialState);
//                }
//                case "3" -> {
//                    System.out.println("Final states:");
//                    var finalStates = fa.getFinalStates();
//                    finalStates.forEach(System.out::println);
//                }
//                case "4" -> {
//                    System.out.println("Alphabet:");
//                    var alphabet = fa.getAlphabet();
//                    alphabet.forEach(System.out::println);
//                }
//                case "5" -> {
//                    System.out.println("All transitions");
//                    String transitions = fa.printTransitions();
//                    System.out.println(transitions);
//                }
//                case "6" -> {
//                    System.out.println("Check DFA:");
//                    if (fa.isDFA()) {
//                        System.out.println(">> Enter sequence: ");
//                        Scanner read = new Scanner(System.in);
//                        String sequence = read.nextLine();
//                        if (fa.verifySequence(sequence, "FA_id.in"))
//                            System.out.println("Sequence is valid!");
//                        else System.out.println("Sequence is not valid!");
//
//                    } else System.out.println("FA is not deterministic!");
//
//                }
//                case "0" -> {
//                    System.out.println("Bye-bye!");
//                    return;
//                }
//                default -> {
//                    System.out.println("Invalid command, here are the options:");
//                    System.out.println(menuOptions);
//                }
//            }
//        }

      // Grammar grammar = new Grammar("g1.txt");
//        System.out.println(">> Nonterminals");
//        grammar.getNonterminals().forEach(System.out::println);
//        System.out.println(">> Terminals");
//        grammar.getTerminals().forEach(System.out::println);
//        System.out.println(">> Start");
//        System.out.println(grammar.getS());
//        System.out.println(">> Productions");
//        System.out.println(grammar.printProductions());
//        System.out.println(">> CFG Check");
//        System.out.println(grammar.checkCFG());

        Parser parser = new Parser("aa", "g1.txt");
        parser.parse();
        System.out.println(parser.toString());
    }
}
