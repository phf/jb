import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

import java.util.HashMap;

/**
 * Comparing different ways of copying hashmaps. The dubious clone() method as
 * well as the respectable copy constructor beat everything else. Don't use the
 * clone() method though. Please.
 */
public final class CopyHashMap {
    private static final int SIZE = 1 << 13;

    private static void init(HashMap<String, Integer> m) {
        for (int i = 0; i < SIZE; i++) {
            String k = Integer.toString((1 << 30) - i * 73, (i & 16) + 1);
            m.put(k, i);
        }
    }

    @Bench
    public void manualEntry(Bee b) {
        b.stop();
        HashMap<String, Integer> from = new HashMap<>();
        init(from);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            HashMap<String, Integer> to = new HashMap<>();
            for (HashMap.Entry<String, Integer> e: from.entrySet()) {
                to.put(e.getKey(), e.getValue());
            }
        }
    }

    @Bench
    public void manualKey(Bee b) {
        b.stop();
        HashMap<String, Integer> from = new HashMap<>();
        init(from);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            HashMap<String, Integer> to = new HashMap<>();
            for (String s: from.keySet()) {
                to.put(s, from.get(s));
            }
        }
    }

    @Bench
    public void lambda(Bee b) {
        b.stop();
        HashMap<String, Integer> from = new HashMap<>();
        init(from);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            HashMap<String, Integer> to = new HashMap<>();
            from.forEach((k, v) -> to.put(k, v));
        }
    }

    @Bench
    public void constructor(Bee b) {
        b.stop();
        HashMap<String, Integer> from = new HashMap<>();
        init(from);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            HashMap<String, Integer> to = new HashMap<>(from);
        }
    }

    @Bench
    @SuppressWarnings("unchecked")
    public void clone(Bee b) {
        b.stop();
        HashMap<String, Integer> from = new HashMap<>();
        init(from);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            HashMap<String, Integer> to = (HashMap<String, Integer>) from.clone();
        }
    }
}
