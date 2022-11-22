import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Grammar {
    private ArrayList<String> nonterminals;
    private ArrayList<String> terminals;
    private Map<String, ArrayList<String>> productions;
    private String S;
    private String fileName;

    public String getS() {
        return S;
    }

    public Grammar(String fileName) throws FileNotFoundException {
        this.fileName = fileName;
        nonterminals = new ArrayList<>();
        terminals = new ArrayList<>();
        productions = new HashMap<>();
        readFromFile();
    }

    public ArrayList<String> getNonterminals() {
        return nonterminals;
    }

    public ArrayList<String> getTerminals() {
        return terminals;
    }

    public Map<String, ArrayList<String>> getProductions() {
        return productions;
    }

    void readFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(this.fileName));

        String contentLine = scanner.nextLine();
        nonterminals = new ArrayList<>(Arrays.asList(contentLine.split(",")));

        contentLine = scanner.nextLine();
        terminals = new ArrayList<>(Arrays.asList(contentLine.split(",")));

        contentLine = scanner.nextLine();
        S = contentLine;

        while (scanner.hasNextLine()) {
            String productionLine = scanner.nextLine();
            String lhs = productionLine.split(" -> ")[0];
            String rhs = productionLine.split(" -> ")[1];
            ArrayList<String> allProductions = new ArrayList<>(Arrays.asList(rhs.split(" \\| ")));
            ArrayList<String> values = new ArrayList<>(allProductions);
            productions.put(lhs, values);
        }
    }

    String printProductions(){
        StringBuilder stringBuilder = new StringBuilder();
        productions.forEach((K,V) -> {
            stringBuilder.append(K).append(" -> ");
            V.forEach(p -> stringBuilder.append(p).append(" | "));
            stringBuilder.delete(stringBuilder.length()-2, stringBuilder.length()-1);
            stringBuilder.append("\n");
        });
        return stringBuilder.toString();
    }

}
