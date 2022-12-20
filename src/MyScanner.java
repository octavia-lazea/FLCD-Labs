import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

public class MyScanner {

    private final SymbolTable symbolTable;
    private final PIF pif;
    private final String programFileName;

    private final FiniteAutomaton finiteAutomaton;
    private final ArrayList<String> reservedWords = new ArrayList<>(List.of("integer", "char", "string", "list", "read", "write", "const", "if", "then", "else", "while", "for", "var", "def", "true", "false", "and", "or", "not", "start", "end"));
    private final ArrayList<String> operators = new ArrayList<>(List.of("+", "-", "*", "/", "=", "==", "<", ">", "<=", ">=", "!=", "!", "%", "+=", "-="));
    private final ArrayList<String> separators = new ArrayList<>(List.of("(", ")", "{", "}", "[", "]", ",", ";", " ", "\n"));

    public MyScanner(String fileName) throws FileNotFoundException {
        this.programFileName = fileName;
        this.symbolTable = new SymbolTable(50);
        this.pif = new PIF();
        this.finiteAutomaton = new FiniteAutomaton("FA_int.in", "FA_id.in");
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public String getPif() {
        return String.valueOf(this.pif);
    }

//    private boolean isNumericalConstant(String value) {
//        return value.matches("^(-?[1-9][0-9]*)|0$");
//    }

    private boolean isNumericalConstant(String value) throws FileNotFoundException {
        return finiteAutomaton.verifySequence(value, this.finiteAutomaton.getIntFile());
    }

    private boolean isCharConstant(String value) {
        return value.matches("^'[a-zA-Z0-9 ]'$");
    }

    private boolean isStringConstant(String value) {
        return value.matches("^\"[a-zA-Z0-9 ]*\"$");
    }

    private boolean isConstant(String value) throws FileNotFoundException {
        return isNumericalConstant(value) || isCharConstant(value) || isStringConstant(value);
    }

//    private boolean isIdentifier(String value) {
//        return value.matches("^[a-zA-Z_]([a-zA-Z0-9_]*)$");
//    }

    private boolean isIdentifier(String value) throws FileNotFoundException {
        return finiteAutomaton.verifySequence(value, this.finiteAutomaton.getIdFile());
    }

    private boolean isOperator(String token) {
        return this.operators.contains(token);
    }

    private boolean isSeparator(String token) {
        return this.separators.contains(token);
    }

    private boolean isReservedWord(String token) {
        return this.reservedWords.contains(token);
    }

    private String readFromFile() throws FileNotFoundException {
        StringBuilder fileContent = new StringBuilder();
        Scanner scanner = new Scanner(new File(this.programFileName));
        while (scanner.hasNextLine())
            fileContent.append(scanner.nextLine()).append("\n");
        return fileContent.toString();
    }

    private List<String> tokenize() throws FileNotFoundException {
        String fileContent = this.readFromFile();
        String separators = this.separators.stream().reduce("", (a, b) -> a + b);
        // tokenize the content of the file but also keep the separators
        return new ArrayList<>(Collections.list(new StringTokenizer(fileContent, separators, true)).stream()
                .map(Object::toString).toList());
    }

    public void scan() throws FileNotFoundException {
        List<String> tokens = this.tokenize();
        boolean hasError = false;
        int lineNumber = 1;
        for (String token : tokens) {
            if (token.contains("\n")) {
                lineNumber += 1;
                continue;
            }
            if (isReservedWord(token) || isOperator(token) || isSeparator(token))
                this.pif.add(new Pair<>(token, new Pair<>(-1, -1)));
            else if (isIdentifier(token) || isConstant(token)) {
                this.symbolTable.add(token);
                Pair<Integer, Integer> pos = this.symbolTable.getPosition(token);
                this.pif.add(new Pair<>(token, pos));
            } else {
                System.out.println("ERROR on line " + lineNumber + ": " + token);
                hasError = true;
            }
        }
        if (!hasError)
            System.out.println("Lexically correct");
    }

    public void writeToFile(String fileName, Object object) {
        try (PrintStream printStream = new PrintStream(fileName)) {
            printStream.println(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
