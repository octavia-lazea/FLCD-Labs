import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
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

        MyScanner scanner = new MyScanner("p3.txt");
        try {
            scanner.scan();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        scanner.writeToFile("ST.out", scanner.getSymbolTable().toString());
        scanner.writeToFile("PIF.out", scanner.getPif());
    }
}
