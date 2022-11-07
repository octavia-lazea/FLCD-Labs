import java.util.ArrayList;
import java.util.List;

public class PIF {

    private List<Pair<String, Integer>> pif;

    public PIF() {
        this.pif = new ArrayList<>();
    }

    public void add(Pair<String, Integer> element) {
        this.pif.add(element);
    }

    public String get() {
        return this.pif.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Pair<String, Integer> tokensPosition : this.pif) {
            stringBuilder.append(tokensPosition.key)
                    .append(" : ")
                    .append(tokensPosition.value)
                    .append("\n");
        }
        return stringBuilder.toString();
    }
}
