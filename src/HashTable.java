public class HashTable {

    static private class ListNode {
        Object key;
        Object value;
        ListNode next;
    }

    private ListNode[] table;
    private int size;

    public HashTable() {
        table = new ListNode[50];
    }

    public HashTable(int initialSize) {
        table = new ListNode[initialSize];
    }

    private int hash(Object key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public void put(Object key, Object value) {
        int bucket = hash(key);
        ListNode nodes = table[bucket];
        while (nodes != null) {
            if (nodes.key.equals(key))
                break;
            nodes = nodes.next;
        }
        if (nodes != null)
            nodes.value = value;
        else if (size >= 0.75 * table.length)
            resize();
        ListNode newNode = new ListNode();
        newNode.key = key;
        newNode.value = value;
        newNode.next = table[bucket];
        table[bucket] = newNode;
        size++;

    }

    private void resize() {
        ListNode[] newList = new ListNode[this.size * 2];
        for (int i = 0; i < table.length; i++) {
            newList[i] = table[i];
        }
        this.table = newList;
    }

    private Object get(Object key) {
        int bucket = hash(key);
        ListNode nodes = table[bucket];
        while (nodes != null) {
            if (nodes.key.equals(key))
                return nodes.value;
            nodes = nodes.next;
        }
        return null;
    }

    private int getSize() {
        return this.size;
    }
}
