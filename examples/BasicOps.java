import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

/**
 * Comparing basic operations on integers. The one thing that still seems to be
 * true these days is that division/modulo is a LOT slower than anything else.
 */
public final class BasicOps {
    private static final int COUNT = 1 << 21;
    public static int x = 10, y = 20, z = 30;
    public static int[] a = new int[COUNT];
    public static boolean c = false;

    @Bench
    public void add(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < COUNT; i++) {
                x = y + i;
            }
        }
    }

    @Bench
    public void subtract(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < COUNT; i++) {
                x = y - i;
            }
        }
    }

    @Bench
    public void multiply(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < COUNT; i++) {
                x = y * i;
            }
        }
    }

    @Bench
    public void divide(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 1; i < COUNT+1; i++) {
                x = y / i;
            }
        }
    }

    @Bench
    public void modulo(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 1; i < COUNT+1; i++) {
                x = y % i;
            }
        }
    }

    @Bench
    public void equals(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < COUNT; i++) {
                c = x == i;
            }
        }
    }

    @Bench
    public void less(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < COUNT; i++) {
                c = x < i;
            }
        }
    }

    @Bench
    public void and(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < COUNT; i++) {
                x = y & i;
            }
        }
    }

    @Bench
    public void or(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < COUNT; i++) {
                x = y | i;
            }
        }
    }

    @Bench
    public void not(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < COUNT; i++) {
                x = ~i;
            }
        }
    }

    @Bench
    public void index(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 0; i < COUNT; i++) {
                x = a[i];
            }
        }
    }

    @Bench
    public void divideConstant(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 1; i < COUNT+1; i++) {
                x = y / 1037;
            }
        }
    }

    @Bench
    public void divideConstantPowerOfTwo(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 1; i < COUNT+1; i++) {
                x = y / 1024;
            }
        }
    }

    @Bench
    public void shiftRightConstant(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 1; i < COUNT+1; i++) {
                x = y >> 7;
            }
        }
    }

    @Bench
    public void shiftLeftConstant(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 1; i < COUNT+1; i++) {
                x = y << 7;
            }
        }
    }

    @Bench
    public void shiftRight(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 1; i < COUNT+1; i++) {
                x = y >> (i & 31);
            }
        }
    }

    @Bench
    public void shiftLeft(Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            for (int i = 1; i < COUNT+1; i++) {
                x = y << (i & 31);
            }
        }
    }
}
