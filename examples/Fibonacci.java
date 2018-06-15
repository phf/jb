import com.github.phf.jb.Bench;
import com.github.phf.jb.Bee;

/**
 * An example inspired by Dave Cheney.
 *
 * See Dave's most excellent blog post (about Go, but applicable to jb):
 * http://dave.cheney.net/2013/06/30/how-to-write-benchmarks-in-go
 */
public final class Fibonacci {
    /**
     * A silly recursive Fibonacci. Of course you normally wouldn't have the
     * "unit under benchmark" in the same class as the benchmarks. But this
     * way the example remains short and sweet.
     */
    private static int fib(int n) {
        if (n < 2) {
            return n;
        } else {
            return fib(n - 1) + fib(n - 2);
        }
    }

    /**
     * A wrapper to test Fibonaccis of varying "depth" properly. Note how
     * jb's b.reps() is used for repetitions, not for parameterizing the
     * fib() call itself!
     */
    private static void wrapFib(int i, Bee b) {
        for (int n = 0; n < b.reps(); n++) {
            fib(i);
        }
    }

    /**
     * The actual benchmark functions. The jb tool will look for these, not
     * any of the other function in this file. Of course they don't have to
     * be one-liners, they just happen to be short in this example.
     */
    @Bench
    public void fib01(Bee b) {
        wrapFib(1, b);
    }

    @Bench
    public void fib02(Bee b) {
        wrapFib(2, b);
    }

    @Bench
    public void fib04(Bee b) {
        wrapFib(4, b);
    }

    @Bench
    public void fib10(Bee b) {
        wrapFib(10, b);
    }

    @Bench
    public void fib20(Bee b) {
        wrapFib(20, b);
    }

    @Bench
    public void fib40(Bee b) {
        wrapFib(40, b);
    }
}
