package com.github.phf.jb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static java.lang.Math.min;
import static java.lang.Math.max;

/**
 * Bee represents a running benchmark. Bzzz. Can you hear it?
 * TODO Don't log to stderr, the main program should do that.
 */
public final class Bee {
    // Needed for memory statistics, storing it once is cheaper.
    private static final Runtime RT = Runtime.getRuntime();
    // Some constants we need.
    private static final long ONE_SECOND = 1000 * 1000 * 1000;
    private static final long MAX_TIME = ONE_SECOND;
    private static final int MAX_REPETITIONS = 1000 * 1000 * 1000;

    // How much time has been used? In nanoseconds.
    private long startTime;
    private long netTime;
    // How much memory has been used? In bytes.
    private long startMemory;
    private long netMemory;
    // How much data has been processed? In bytes.
    private long netThroughput;

    // Whether we're currently measuring or not.
    private boolean measuring;
    // If an exception stopped the benchmark, here it is.
    private Throwable throwable;
    // How many iterations for this bee?
    private int repetitions;

    /**
     * A new bee, still has to be started.
     */
    private Bee() {}

    /**
     * Run benchmark method. Note that we silently handle a bunch of
     * exceptions here. The method exists mostly so that Bee can store
     * the exception that terminated a benchmark.
     */
    private void run(Method m, Object o) {
        try {
            m.invoke(o, this);
        } catch (IllegalAccessException | InvocationTargetException e) {
            this.throwable = e;
        }
    }

    /* Nanoseconds. */
    private long currentTime() {
        return System.nanoTime();
    }

    /* Bytes used/allocated. */
    private long currentMemory() {
        return Bee.RT.totalMemory() - Bee.RT.freeMemory();
    }

    /* Nanoseconds per operation. */
    private long nsPerOp() {
        return this.repetitions <= 0 ? 0 : this.netTime / this.repetitions;
    }

    /**
     * How many repetitions of your benchmark to run. You will usually end up
     * writing a for loop running reps times around the code you're interested
     * in benchmarking.
     *
     * @return Number of repetitions to run.
     */
    public int reps() {
        return this.repetitions;
    }

    /**
     * Start (or restart if you paused it earlier) this bee. Further calls of
     * start are ignored until the bee is stopped.
     */
    public void start() {
        if (!this.measuring) {
            this.startMemory = this.currentMemory();
            this.startTime = this.currentTime();
            this.measuring = true;
        }
    }

    /**
     * Stop (or pause if you intend to restart it later) this bee. Further
     * calls of stop are ignored until the bee is started.
     */
    public void stop() {
        if (this.measuring) {
            this.netTime += this.currentTime() - this.startTime;
            this.netMemory += this.currentMemory() - this.startMemory;
            this.measuring = false;
        }
    }

    /**
     * Tell this bee how many bytes your code processed in one iteration.
     * If you call this, the output will include bytes/second.
     *
     * @param n Number of bytes this iteration.
     */
    public void bytes(long n) {
        if (this.measuring) {
            this.netThroughput += n;
        }
    }

    /**
     * Reset this bee. Everything starts over from 0 so this bee forgets all
     * it ever measured. Doesn't affect whether this Bee is running or not.
     */
    private void reset() {
        if (this.measuring) {
            this.startMemory = this.currentMemory();
            this.startTime = this.currentTime();
        }
        this.netTime = 0;
        this.netMemory = 0;
        this.netThroughput = 0;
    }

    /**
     * Fail the benchmark. See fail(String) below.
     */
    public void fail() {
        this.fail("no reason given");
    }

    /**
     * Fail the benchmark. Aborts (and stops) the current bee immediately but
     * continues to the next one (if any).
     *
     * @param reason Explains why the benchmark failed.
     */
    public void fail(String reason) {
        this.stop();
        throw new BenchmarkFailedException(reason);
    }

    /**
     * Run a complete benchmark. This should conceptually live in a "runner"
     * class (or "keeper" to stick with the "bee" metaphor). Alas we need to
     * access Bee internals a lot, so it's more convenient to just put it in
     * here with the rest of the Bee.
     */
    static Result runBenchmark(Method m, Object o) {
        int n = 1;
        Bee b = new Bee();

        for (;;) {
            RT.gc();

            b.repetitions = n;
            b.reset();
            b.start();
            b.run(m, o);
            if (b.throwable != null) {
                System.err.printf("Method %s failed!\n", m.getName());
                b.throwable.printStackTrace(System.err);
                break;
            }
            b.stop();

            if (b.netTime >= MAX_TIME || n >= MAX_REPETITIONS) {
                break;
            }

            int last = n;

            if (b.nsPerOp() == 0) {
                n = MAX_REPETITIONS;
            } else {
                n = (int) (ONE_SECOND / b.nsPerOp());
            }

            n = (int) roundUp(max(min(n + (n / 5), 100 * last), last + 1));
        }

        return new Result(
            b.repetitions, b.netTime, b.netThroughput, b.netMemory
        );
    }

    /**
     * Round down to the nearest power of 10. For example, 99 is rounded
     * to 10.
     */
    private static long roundDown10(long n) {
        int tens = 0;
        while (n >= 10) {
            n /= 10;
            tens++;
        }
        int result = 1;
        for (int i = 0; i < tens; i++) {
            result *= 10;
        }
        return result;
    }

    /**
     * Round up to a number of the form 1eX, 2eX, 3eX, 5eX. For example,
     * 99 is rounded to 100, but 4273 is rounded to 5000.
     */
    private static long roundUp(long n)  {
        long base = roundDown10(n);
        if (n <= base) {
            return base;
        }
        if (n <= (2 * base)) {
            return 2 * base;
        }
        if (n <= (3 * base)) {
            return 3 * base;
        }
        if (n <= (5 * base)) {
            return 5 * base;
        }
        return 10 * base;
    }
}
