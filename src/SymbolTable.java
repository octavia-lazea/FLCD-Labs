import java.util.ArrayList;

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

    private int hash(String key) {
        int asciiSum = 0;
        for (int i = 0; i < key.length(); i++)
            asciiSum += key.charAt(i);
        return asciiSum % this.size;
    }

    private boolean add(String key) {
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
        int keyHash = hash(key);
        return this.elements.get(keyHash).contains(key);
    }

}
