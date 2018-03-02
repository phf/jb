import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

import java.util.Arrays;

/**
 * Comparing copying and filling of arrays. The conclusion is that in many
 * cases System.arraycopy() and Arrays.fill() are the way to go.
 */
public final class ArrayOps {
    private static final int SIZE = 10000;
    private static int refBytes;

    static {
        switch (System.getProperty("sun.arch.data.model")) {
            case "32":
                refBytes = 4;
                break;
            case "64":
                refBytes = 8;
                break;
            default:
                refBytes = -1;
                break;
        }
    }

    private static void init(Object[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = Integer.toString(i);
        }
    }

    @Bench
    public void copyManual(Bee b) {
        b.stop();
        Object[] from = new Object[SIZE];
        init(from);
        Object[] to = new Object[SIZE];
        b.start();
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < from.length; i++) {
                to[i] = from[i];
            }
            b.bytes(from.length * refBytes);
        }
    }

    @Bench
    public void copyClone(Bee b) {
        b.stop();
        Object[] from = new Object[SIZE];
        init(from);
        Object[] to;
        b.start();
        for (int n = 0; n < b.reps(); n++) {
            to = from.clone();
            b.bytes(from.length * refBytes);
        }
    }

    @Bench
    public void copySystem(Bee b) {
        b.stop();
        Object[] from = new Object[SIZE];
        init(from);
        Object[] to = new Object[SIZE];
        b.start();
        for (int n = 0; n < b.reps(); n++) {
            System.arraycopy(from, 0, to, 0, from.length);
            b.bytes(from.length * refBytes);
        }
    }

    @Bench
    public void copyArrays(Bee b) {
        b.stop();
        Object[] from = new Object[SIZE];
        init(from);
        Object[] to;
        b.start();
        for (int n = 0; n < b.reps(); n++) {
            to = Arrays.copyOf(from, from.length);
            b.bytes(from.length * refBytes);
        }
    }

    @Bench
    public void fillManual(Bee b) {
        b.stop();
        Object[] to = new Object[SIZE];
        b.start();
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < to.length; i++) {
                to[i] = "Some string?";
            }
            b.bytes(to.length * refBytes);
        }
    }

    @Bench
    public void fillArrays(Bee b) {
        b.stop();
        Object[] to = new Object[SIZE];
        b.start();
        for (int n = 0; n < b.reps(); n++) {
            Arrays.fill(to, "Some string?");
            b.bytes(to.length * refBytes);
        }
    }
}
