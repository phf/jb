import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

import java.util.List;
import java.util.Random;

/**
 * Comparing Java's List implementations. The conclusion is that LinkedList is
 * often the wrong choice if we care for performance. (Alternatively we could
 * conclude that the Java folks should not be allowed to design libraries.)
 */
public abstract class BaseCompareLists {
    private static final int SIZE = 1000;
    private static final Random r = new Random();
    private static String t;

    abstract List<String> createUnit();

    // Code to benchmark, factored out for clarity.

    private static void insertBack(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            l.add(Integer.toString(i));
        }
    }

    private static void insertFront(List<String> l) {
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

    private static void removeFront(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            l.remove(0);
        }
    }

    private static void removeBack(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            l.remove(l.size() - 1);
        }
    }

    private static void removeRandom(List<String> l) {
        for (int i = 0; i < SIZE-1; i++) {
            l.remove(r.nextInt(l.size()));
        }
        l.remove(0);
    }

    private static void getLinear(List<String> l) {
        for (int i = 0; i < SIZE; i++) {
            t = l.get(i);
        }
    }

    private static void getRandom(List<String> l) {
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
    public void insertBack(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            List<String> l = this.createUnit();
            b.start();

            insertBack(l);
        }
    }

    @Bench
    public void insertFront(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            List<String> l = this.createUnit();
            b.start();

            insertFront(l);
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
    public void removeFront(Bee b) {
        b.stop();
        List<String> backup = this.createUnit();
        insertBack(backup);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            List<String> l = this.createUnit();
            l.addAll(backup);
            b.start();

            removeFront(l);
        }
    }

    @Bench
    public void removeBack(Bee b) {
        b.stop();
        List<String> backup = this.createUnit();
        insertBack(backup);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            List<String> l = this.createUnit();
            l.addAll(backup);
            b.start();

            removeBack(l);
        }
    }

    @Bench
    public void removeRandom(Bee b) {
        b.stop();
        List<String> backup = this.createUnit();
        insertBack(backup);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            b.stop();
            List<String> l = this.createUnit();
            l.addAll(backup);
            b.start();

            removeRandom(l);
        }
    }

    @Bench
    public void getLinear(Bee b) {
        b.stop();
        List<String> l = this.createUnit();
        insertBack(l);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            getLinear(l);
        }
    }

    @Bench
    public void getRandom(Bee b) {
        b.stop();
        List<String> l = this.createUnit();
        insertBack(l);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            getRandom(l);
        }
    }

    @Bench
    public void iterate(Bee b) {
        b.stop();
        List<String> l = this.createUnit();
        insertBack(l);
        b.start();

        for (int n = 0; n < b.reps(); n++) {
            iterate(l);
        }
    }
}
