public class PhoneBook implements IMap {
    private Entry[] entries;
    private int size;

    public PhoneBook() {
        this(10);
    }

    public PhoneBook(int capacity) {
        this.entries = new Entry[capacity];
        this.size = 0;
    }

    private int hash(Person person) {
        return person.hashCode() % entries.length;
    }

    @Override
    public PhoneNumber put(Person person, PhoneNumber phone) {
        Entry entry = findEntry(person);
        // create new entry if it doesn't exist
        if (entry == null) {
            int index = hash(person);
            // if there is no entry at the index, create one
            if (entries[index] == null) {
                entries[index] = entry = new Entry(person, phone);
            }
            else {
                // otherwise make a new entry at the end
                entry = entries[index];
                while (entry.next != null) {
                    entry = entry.next;
                }
                entry.next = new Entry(person, phone);
            }
            size++;
        }
        else {
            PhoneNumber previous = entry.number;
            entry.number = phone;
            return previous;
        }
        return null;
    }

    private Entry findEntry(Person person) {
        int index = hash(person);
        Entry entry = entries[index];
        while (entry != null && !entry.person.equals(person)) {
            entry = entry.next;
        }
        return entry;
    }

    @Override
    public PhoneNumber get(Person person) {
        Entry entry = findEntry(person);
        if (entry == null)
            return null;
        return entry.number;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public PhoneNumber remove(Person person) {
        Entry entry = findEntry(person);
        if (entry == null) {
            return null;
        }
        size--;
        PhoneNumber number = entry.number;
        int index = hash(person);
        if (entry == entries[index]) {
            entries[index] = null;
            return number;
        }
        entry = entries[index];
        // entry is guaranteed to be in this linked list. find Entry e where e.next == entry
        while (!entry.next.person.equals(person)) {
            entry = entry.next;
        }
        entry.next = entry.next.next;
        return number;
    }

    class Entry {
        private Person person;
        private PhoneNumber number;
        private Entry next;

        public Entry(Person person, PhoneNumber number) {
            this(person, number, null);
        }

        public Entry(Person person, PhoneNumber number, Entry next) {
            this.person = person;
            this.number = number;
            this.next = next;
        }
    }
}
