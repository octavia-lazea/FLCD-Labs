import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class MyScanner {

    private SymbolTable symbolTable;
    private PIF pif;
    private final String programFileName;
    private final ArrayList<String> reservedWords = new ArrayList<>(List.of("integer", "char", "string", "list", "read", "write", "const", "if", "then", "else", "while", "for", "var", "def", "true", "false", "and", "or", "not"));
    private final ArrayList<String> operators = new ArrayList<>(List.of("+", "-", "*", "/", "=", "==", "<", ">", "<=", ">=", "!=", "!", "%","+=", "-="));
    private final ArrayList<String> separators = new ArrayList<>(List.of("(", ")", "{", "}", "[", "]", ",", ";", " ", "\n"));

    public MyScanner(String fileName) {
        this.programFileName = fileName;
        this.symbolTable = new SymbolTable(100);
        this.pif = new PIF();
    }

//    public ArrayList<ArrayList<String>> getSymbolTable() {
//        return symbolTable.getElements();
//    }

    public SymbolTable getSymbolTable(){return this.symbolTable;}

    public String getPif() {
        return String.valueOf(this.pif);
    }

    public boolean isNumericalConstant(String value) {
        return value.matches("^(-?[1-9][0-9]*)|0$");
    }

    public boolean isCharConstant(String value) {
        return value.matches("^'[a-zA-Z0-9 ]'$");
    }

    public boolean isStringConstant(String value) {
        return value.matches("^\"[a-zA-Z0-9 ]*\"$");
    }

    public boolean isConstant(String value) {
        return isNumericalConstant(value) || isCharConstant(value) || isStringConstant(value);
    }

    public boolean isIdentifier(String value) {
        return value.matches("^[a-zA-Z_]([a-zA-Z0-9_]*)$");
    }

    public String readFromFile() throws FileNotFoundException {
        StringBuilder fileContent = new StringBuilder();
        Scanner scanner = new Scanner(new File(this.programFileName));
        while (scanner.hasNextLine())
            fileContent.append(scanner.nextLine()).append("\n");
        return fileContent.toString().replace("\t", "");
    }

    public List<String> tokenize() throws FileNotFoundException {
        String fileContent = this.readFromFile();
        String separators = this.separators.stream().reduce("", (a, b) -> a + b);
        List<String> tokensWithSeparators = new ArrayList<>(Collections.list(new StringTokenizer(fileContent, separators, true)).stream()
                .map(token -> (String) token).toList());
        for (String token : tokensWithSeparators) {
            for (String operator : this.operators) {
                if (token.contains(operator)) {
                    for (int i = 0; i < token.length(); i++) {
                        if (isIdentifier(String.valueOf(token.charAt(i)))) {
                            if (tokensWithSeparators.contains(token))
                                tokensWithSeparators.set(tokensWithSeparators.indexOf(token), operator);
                        }
                    }
                }
            }
        }
        return tokensWithSeparators;
    }

    public void scan() throws FileNotFoundException {
        List<String> tokens = this.tokenize();
        int lineNumber = 1;
        for (String token : tokens) {
            if (token.contains("\n")) {
                lineNumber += 1;
                continue;
            }
            if (this.reservedWords.contains(token) || this.operators.contains(token) || this.separators.contains(token))
                this.pif.add(new Pair<>(token, -1));
            else if (isIdentifier(token) || isConstant(token)) {
                this.symbolTable.add(token);
                int pos = this.symbolTable.getPosition(token);
                this.pif.add(new Pair<>(token, pos));
            } else{
                System.out.println("ERROR on line " + lineNumber + ": " + token);
                return;
            }
        }
        System.out.println("Lexically correct");
    }

    public void writeToFile(String fileName, Object object){
        try (PrintStream printStream = new PrintStream(fileName)){
            printStream.println(object);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

}
