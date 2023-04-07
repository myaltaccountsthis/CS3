import java.util.Arrays;

public class EmployeeDatabase {
    private Entry[] entries;

    public EmployeeDatabase() {
        this(100);
    }

    public EmployeeDatabase(int size) {
        entries = new Entry[size];
    }

    private int hash(int key) {
        // good hashing
        return key * key % entries.length;
        // bad hashing
//        int length = 0;
//        for (; key > 0; key /= 10, length++);
//        return length;
    }

    private int linearProbe(int count) {
        return 1;
    }

    private int quadraticProbe(int count) {
        return 2 * count - 1;
    }

    private int getCollisionProbe(int count) {
        return linearProbe(count);
//        return quadraticProbe(count);
    }

    private int nextHashCode(int hashCode, int count) {
        return (hashCode + getCollisionProbe(count)) % entries.length;
    }

    /**
     * Key is the id
     */
    public void put(int key, Employee employee) {
        int hashCode = hash(key);
        int count = 0;
        // search for empty
        while (entries[hashCode] != null && entries[hashCode].ID != key) {
            count++;
            // full case
            if (count > entries.length)
                return;
            hashCode = nextHashCode(hashCode, count);
        }
        entries[hashCode] = new Entry(key, employee);
    }

    public Employee get(int key) {
        int hashCode = hash(key);
        int count = 0;
        // search for id
        while (entries[hashCode] == null || entries[hashCode].ID != key) {
            count++;
            if (count > entries.length)
                return null;
            hashCode = nextHashCode(hashCode, count);
        }
        return entries[hashCode].employee;
    }

    private class Entry {
        private int ID;
        private Employee employee;

        public Entry(int ID, Employee employee) {
            this.ID = ID;
            this.employee = employee;
        }

        @Override
        public String toString() {
            return ID + ": " + employee;
        }
    }
}
