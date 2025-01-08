import java.util.Arrays;

public class TranspositionTable {
    private static class Entry {
        long key;
        byte val;

        public Entry(long key, int val) {
            this.key = key;
            this.val = (byte) val;
        }

        public Entry() {
            this.key = 0;
            this.val = 0;
        }
    }

    private Entry[] T;

    public TranspositionTable(int size) {
        T = new Entry[size];
        for (int i = 0; i < size; i++) {
            T[i] = new Entry();
        }
    }

    private int index(long key) {
        return (int) (key% T.length);
    }

    public void reset() {
        Arrays.fill(T, new Entry());
    }

    public void put(long key, byte val) {
        int i = index(key);
        T[i].key = key;
        T[i].val = val;
    }

    public int get(long key) {
        int i = index(key);
        if (T[i].key == key) return T[i].val;
        else return 0;
    }

}
