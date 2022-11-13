import java.util.ArrayList;
import java.util.List;

public class PIF {

    private final List<Pair<String, Pair<Integer, Integer>>> pif;

    public PIF() {
        this.pif = new ArrayList<>();
    }

    public void add(Pair<String, Pair<Integer, Integer>> element) {
        this.pif.add(element);
    }

    public String get() {
        return this.pif.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair<String, Pair<Integer, Integer>> tokensPosition : this.pif) {
            stringBuilder.append(tokensPosition.key)
                    .append(" : ")
                    .append("[")
                    .append(tokensPosition.value.getKey())
                    .append(", ")
                    .append(tokensPosition.value.getValue())
                    .append("]")
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
