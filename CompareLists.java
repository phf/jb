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

    private static String s;

    /*
     * Code to benchmark, factored out because we compare different
     * container classes.
     */
    private static void append(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            l.add(Integer.toString(i));
        }
    }

    private static void appendMany(List<String> l, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            l.clear();
            b.start();
            append(l);
        }
    }

    private static void prepend(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            l.add(0, Integer.toString(i));
        }
    }

    private static void prependMany(List<String> l, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            l.clear();
            b.start();
            prepend(l);
        }
    }

    private static void insertRandom(List<String> l) {
        l.add("0");
        for (int i = 0; i < SIZE-1; i++) {
            l.add(r.nextInt(l.size()), Integer.toString(i));
        }
    }

    private static void insertRandomMany(List<String> l, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            l.clear();
            b.start();
            insertRandom(l);
        }
    }

    private static void removeRandom(List<String> l) {
        for (int i = 0; i < SIZE-1; i++) {
            l.remove(r.nextInt(l.size()));
        }
    }

    private static void removeRandomMany(List<String> l, Bee b) {
        b.stop();
        append(l);
        List<String> backup = new ArrayList<>();
        backup.addAll(l);
        b.start();
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            l.clear();
            l.addAll(backup);
            b.start();
            removeRandom(l);
        }
    }

    private static void linearGetMany(List<String> l, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < SIZE; i++) {
                s = l.get(i);
            }
        }
    }

    private static void randomGetMany(List<String> l, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < SIZE; i++) {
                s = l.get(r.nextInt(SIZE));
            }
        }
    }

    private static void linearIterMany(List<String> l, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (String x : l) {
                s = x;
            }
        }
    }

    /*
     * Individual benchmarks.
     */
    @Bench
    public static void appendArrayList(Bee b) {
        b.stop();
        List<String> l = new ArrayList<>();
        b.start();
        appendMany(l, b);
    }

    @Bench
    public static void appendLinkedList(Bee b) {
        b.stop();
        List<String> l = new LinkedList<>();
        b.start();
        appendMany(l, b);
    }

    @Bench
    public static void prependArrayList(Bee b) {
        b.stop();
        List<String> l = new ArrayList<>();
        b.start();
        prependMany(l, b);
    }

    @Bench
    public static void prependLinkedList(Bee b) {
        b.stop();
        List<String> l = new LinkedList<>();
        b.start();
        prependMany(l, b);
    }

    @Bench
    public static void insertRandomArrayList(Bee b) {
        b.stop();
        List<String> l = new ArrayList<>();
        b.start();
        insertRandomMany(l, b);
    }

    @Bench
    public static void insertRandomLinkedList(Bee b) {
        b.stop();
        List<String> l = new LinkedList<>();
        b.start();
        insertRandomMany(l, b);
    }

    @Bench
    public static void removeRandomArrayList(Bee b) {
        b.stop();
        List<String> l = new ArrayList<>();
        b.start();
        removeRandomMany(l, b);
    }

    @Bench
    public static void removeRandomLinkedList(Bee b) {
        b.stop();
        List<String> l = new LinkedList<>();
        b.start();
        removeRandomMany(l, b);
    }

    @Bench
    public static void linearGetArrayList(Bee b) {
        b.stop();
        List<String> l = new ArrayList<>();
        append(l);
        b.start();
        linearGetMany(l, b);
    }

    @Bench
    public static void linearGetLinkedList(Bee b) {
        b.stop();
        List<String> l = new LinkedList<>();
        append(l);
        b.start();
        linearGetMany(l, b);
    }

    @Bench
    public static void randomGetArrayList(Bee b) {
        b.stop();
        List<String> l = new ArrayList<>();
        append(l);
        b.start();
        randomGetMany(l, b);
    }

    @Bench
    public static void randomGetLinkedList(Bee b) {
        b.stop();
        List<String> l = new LinkedList<>();
        append(l);
        b.start();
        randomGetMany(l, b);
    }

    @Bench
    public static void linearIterArrayList(Bee b) {
        b.stop();
        List<String> l = new ArrayList<>();
        append(l);
        b.start();
        linearIterMany(l, b);
    }

    @Bench
    public static void linearIterLinkedList(Bee b) {
        b.stop();
        List<String> l = new LinkedList<>();
        append(l);
        b.start();
        linearIterMany(l, b);
    }
}
