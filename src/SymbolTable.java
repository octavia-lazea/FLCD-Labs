import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SymbolTable {

    private ArrayList<ArrayList<String>> elements;
    private int size;

    public SymbolTable(int size) {
        this.size = size;
        this.elements = new ArrayList<>();
        for (int i = 0; i < size; i++)
            this.elements.add(new ArrayList<>());
    }

    public int getSize() {
        return this.size;
    }

    public ArrayList<ArrayList<String>> getElements() {
        return elements;
    }

    private int hash(String key) {
        int asciiSum = 0;
        for (int i = 0; i < key.length(); i++)
            asciiSum += key.charAt(i);
        return asciiSum % this.size;
    }

    public boolean add(String key) {
        int keyHash = hash(key);
        if (!this.elements.get(keyHash).contains(key)) {
            this.elements.get(keyHash).add(key);
            return true;
        }
        return false;
    }

    public boolean remove(String key) {
        int keyHash = hash(key);
        if (this.elements.get(keyHash).contains(key)) {
            this.elements.get(keyHash).remove(key);
            return true;
        }
        return false;
    }

    public boolean isKey(String key) {
        int pos = hash(key);
        return this.elements.get(pos).contains(key);
    }

    public int getPosition(String key) {
        int pos = hash(key);
        if (this.elements.get(pos).contains(key))
            return pos;
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (!elements.get(i).isEmpty()){
                result.append(i).append(": [");
                String separator = "";
                for (String item : elements.get(i)) {
                    result.append(separator);
                    separator = ", ";
                    result.append(item);
                }
                result.append("]\n");
            }

        }
        return result.toString();
    }


}
