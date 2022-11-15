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
            stringBuilder.append(tokensPosition.value1)
                    .append(" : ")
                    .append("[")
                    .append(tokensPosition.value2.getValue1())
                    .append(", ")
                    .append(tokensPosition.value2.getValue2())
                    .append("]")
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
