import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        MyScanner myScanner = new MyScanner("p1.txt");
        try {
            myScanner.scan();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

         myScanner.writeToFile("ST.out", myScanner.getSymbolTable().toString());
         myScanner.writeToFile("PIF.out", myScanner.getPif());

//        FiniteAutomaton fa = new FiniteAutomaton();
//
//        String menuOptions = """
//                MENU
//                1: initial states
//                2: transitions
//                3: final states
//                0: exit""";
//        Scanner scanner = new Scanner(System.in);
//        System.out.println(menuOptions);
//        while (true) {
//            System.out.println(">>");
//            String option = scanner.nextLine();
//            switch (option) {
//                case "1" -> {
//                    System.out.println("Initial states:");
//                    var initialStates = fa.getInitialStates();
//                    initialStates.forEach(System.out::println);
//                }
//                case "2" -> {
//                    System.out.println("Transitions:");
//                    var transitions = fa.getTransitions();
//                    transitions.forEach(System.out::println);
//                }
//                case "3" -> {
//                    System.out.println("Final states:");
//                    var finalStates = fa.getFinalStates();
//                    finalStates.forEach(System.out::println);
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
    }
}
