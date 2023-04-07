public class MyHashTable<K, V> {
    private Object[] entries;
    private int size;

    public MyHashTable() {
        this(10);
    }

    public MyHashTable(int capacity) {
        this.entries = new Object[capacity];
        this.size = 0;
    }

    private int hash(K key) {
        int index = key.hashCode() % entries.length;
        if (index < 0)
            return index + entries.length;
        return index;
    }

    public V put(K key, V phone) {
        Entry entry = findEntry(key);
        // create new entry if it doesn't exist
        if (entry == null) {
            int index = hash(key);
            // if there is no entry at the index, create one
            if (entries[index] == null) {
                entries[index] = new Entry(key, phone);
            }
            else {
                // otherwise make a new entry at the end
                entry = (Entry) entries[index];
                while (entry.next != null) {
                    entry = entry.next;
                }
                entry.next = new Entry(key, phone);
            }
            size++;
        }
        else {
            V previous = entry.value;
            entry.value = phone;
            return previous;
        }
        return null;
    }

    private Entry findEntry(K key) {
        int index = hash(key);
        Entry entry = (Entry) entries[index];
        while (entry != null && !entry.key.equals(key)) {
            entry = entry.next;
        }
        return entry;
    }

    public V get(K key) {
        Entry entry = findEntry(key);
        if (entry == null)
            return null;
        return entry.value;
    }

    public int size() {
        return size;
    }

    public V remove(K key) {
        Entry entry = findEntry(key);
        if (entry == null) {
            return null;
        }
        size--;
        V value = entry.value;
        int index = hash(key);
        if (entry == entries[index]) {
            entries[index] = entry.next;
            return value;
        }
        entry = (Entry) entries[index];
        // entry is guaranteed to be in this linked list. find Entry e where e.next == entry
        while (!entry.next.key.equals(key)) {
            entry = entry.next;
        }
        entry.next = entry.next.next;
        return value;
    }

    class Entry {
        private K key;
        private V value;
        private Entry next;

        public Entry(K key, V value) {
            this(key, value, null);
        }

        public Entry(K key, V value, Entry next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
