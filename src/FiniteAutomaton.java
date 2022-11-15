import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FiniteAutomaton {
    private ArrayList<String> states;
    private ArrayList<String> finalStates;
    private ArrayList<String> alphabet;
    private final Map<Pair<String, String>, ArrayList<String>> transitions;
    private String initialState;
    private String intFile, idFile;

    public FiniteAutomaton(String intFile, String idFile) throws FileNotFoundException {
        states = new ArrayList<>();
        finalStates = new ArrayList<>();
        transitions = new HashMap<>();
        alphabet = new ArrayList<>();
        this.idFile = idFile;
        this.intFile = intFile;
    }

    public String getIntFile() {
        return intFile;
    }

    public String getIdFile() {
        return idFile;
    }

    private void populate(String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));

        String statesLine = scanner.nextLine();
        states = new ArrayList<>(Arrays.asList(statesLine.split(";")));
        states.remove(0);

        String initialStateLine = scanner.nextLine();
        ArrayList<String> initialTokens = new ArrayList<>(Arrays.asList(initialStateLine.split(";")));
        initialState = initialTokens.get(1);

        String finalStatesLine = scanner.nextLine();
        finalStates = new ArrayList<>(Arrays.asList(finalStatesLine.split(";")));
        finalStates.remove(0);

        String alphabetLine = scanner.nextLine();
        alphabet = new ArrayList<>(Arrays.asList(alphabetLine.split(";")));
        alphabet.remove(0);

        scanner.nextLine();

        while (scanner.hasNextLine()) {
            String transitionLine = scanner.nextLine();
            ArrayList<String> transitionElems = new ArrayList<>(Arrays.asList(transitionLine.split(",")));

            if (states.contains(transitionElems.get(0)) && states.contains(transitionElems.get(2)) && alphabet.contains(transitionElems.get(1))) {
                Pair<String, String> states = new Pair<>(transitionElems.get(0), transitionElems.get(1));
                if (!transitions.containsKey(states)) {
                    ArrayList<String> values = new ArrayList<>();
                    values.add(transitionElems.get(2));
                    transitions.put(states, values);
                } else transitions.get(states).add(transitionElems.get(2));
            }
        }

    }

    public ArrayList<String> getStates() {
        return states;
    }

    public ArrayList<String> getFinalStates() {
        return finalStates;
    }

    public Map<Pair<String, String>, ArrayList<String>> getTransitions() {
        return transitions;
    }

    public ArrayList<String> getAlphabet() {
        return alphabet;
    }

    public String getInitialState() {
        return this.initialState;
    }

    String printTransitions() {
        StringBuilder result = new StringBuilder();
        transitions.forEach((K, V) -> result.append("(").append(K.value1).append(", ").append(K.value2).append(") -> ").append(V).append("\n"));
        return result.toString();
    }

    public boolean isDFA() {
        return transitions.values().stream().noneMatch(list -> list.size() > 1);
    }

    public boolean verifySequence(String sequence, String fileName) throws FileNotFoundException {
        populate(fileName);
        if (sequence.length() == 0)
            return finalStates.contains(initialState);

        String state = initialState;
        for (int i = 0; i < sequence.length(); i++) {
            Pair<String, String> key = new Pair<>(state, String.valueOf(sequence.charAt(i)));
            if (transitions.keySet().stream().anyMatch(elem -> elem.value1.equals(key.value1) && elem.value2.equals(key.value2))) {
                Pair<String, String> key1 = transitions.keySet().stream().filter(elem ->  elem.value1.equals(key.value1) && elem.value2.equals(key.value2)).findFirst().get();
                state = transitions.get(key1).iterator().next();
            } else {
                return false;
            }
        }
        return finalStates.contains(state);
    }
}
