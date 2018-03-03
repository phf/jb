import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

import java.util.List;
import java.util.Random;

/**
 * Comparing Java's List implementations. The conclusion is that LinkedList is
 * often the wrong choice if we care for performance. Alternatively we could
 * conclude that the Java folks should not be allowed to design more libraries.
 */
public abstract class BaseCompareLists {
    private static final int SIZE = 1000;
    private static final Random r = new Random();
    private static String t;

    abstract List<String> createUnit();

    // Code to benchmark, factored out for clarity.

    private static void append(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            l.add(Integer.toString(i));
        }
    }

    private static void prepend(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            l.add(0, Integer.toString(i));
        }
    }

    private static void insertRandom(List<String> l) {
        l.add("0");
        for (int i = 1; i < SIZE; i++) {
            l.add(r.nextInt(l.size()), Integer.toString(i));
        }
    }

    private static void removeRandom(List<String> l) {
        for (int i = 0; i < SIZE-1; i++) {
            l.remove(r.nextInt(l.size()));
        }
        l.remove(0);
    }

    private static void linearGet(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            t = l.get(i);
        }
    }

    private static void randomGet(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            t = l.get(r.nextInt(SIZE));
        }
    }

    private static void iterate(List<String> l) {
        for (String x: l) {
            t = x;
        }
    }

    // Individual benchmarks, note the variety of setups necessary.

    @Bench
    public void append(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            List<String> l = this.createUnit();
            b.start();

            append(l);
        }
    }

    @Bench
    public void prepend(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            List<String> l = this.createUnit();
            b.start();

            prepend(l);
        }
    }

    @Bench
    public void insertRandom(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            List<String> l = this.createUnit();
            b.start();

            insertRandom(l);
        }
    }

    @Bench
    public void removeRandom(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            List<String> l = this.createUnit();
            append(l);
            b.start();

            removeRandom(l);
        }
    }

    @Bench
    public void linearGet(Bee b) {
        b.stop();
        List<String> l = this.createUnit();
        append(l);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            linearGet(l);
        }
    }

    @Bench
    public void randomGet(Bee b) {
        b.stop();
        List<String> l = this.createUnit();
        append(l);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            randomGet(l);
        }
    }

    @Bench
    public void iterate(Bee b) {
        b.stop();
        List<String> l = this.createUnit();
        append(l);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            iterate(l);
        }
    }
}
