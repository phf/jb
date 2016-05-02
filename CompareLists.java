import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Comparing ArrayList and LinkedList. The conclusion is that LinkedList is
 * often the wrong choice if we care for performance.
 */
public final class CompareLists {
    private static final int SIZE = 1000;
    private static final Random r = new Random();

    private CompareLists() {}

    /*
     * Code to benchmark, factored out because we compare different
     * container classes.
     */
    private static void insert(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            l.add(Integer.toString(i));
        }
    }

    private static void insertMany(List<String> l, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            l.clear();
            insert(l);
        }
    }

    private static void linearMany(List<String> l, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < SIZE; i++) {
                String s = l.get(i);
            }
        }
    }

    private static void randomMany(List<String> l, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < SIZE; i++) {
                String s = l.get(r.nextInt(SIZE));
            }
        }
    }

    /*
     * Individual benchmarks.
     */
    @Bench
    public static void insertArrayList(Bee b) {
        List<String> l = new ArrayList<>();
        insertMany(l, b);
    }

    @Bench
    public static void insertLinkedList(Bee b) {
        List<String> l = new LinkedList<>();
        insertMany(l, b);
    }

    @Bench
    public static void linearArrayList(Bee b) {
        b.stop();
        List<String> l = new ArrayList<>();
        insert(l);
        b.start();
        linearMany(l, b);
    }

    @Bench
    public static void linearLinkedList(Bee b) {
        b.stop();
        List<String> l = new LinkedList<>();
        insert(l);
        b.start();
        linearMany(l, b);
    }

    @Bench
    public static void randomArrayList(Bee b) {
        b.stop();
        List<String> l = new ArrayList<>();
        insert(l);
        b.start();
        randomMany(l, b);
    }

    @Bench
    public static void randomLinkedList(Bee b) {
        b.stop();
        List<String> l = new LinkedList<>();
        insert(l);
        b.start();
        randomMany(l, b);
    }
}
