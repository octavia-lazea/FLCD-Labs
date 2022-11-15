public class Pair<K, V> {
    K value1;
    V value2;

    public Pair(K value1, V value2) {
        this.value1 = value1;
        this.value2 = value2;
    }

    public Pair() {

    }

    public K getValue1() {
        return this.value1;
    }

    public V getValue2() {
        return this.value2;
    }

    public void setValue1(K value1) {
        this.value1 = value1;
    }

    public void setValue2(V value2) {
        this.value2 = value2;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "value1=" + value1 +
                ", value2=" + value2 +
                '}';
    }
}
