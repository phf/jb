package com.github.phf.jb;

import java.lang.reflect.Method;

/**
 * The jb tool to run benchmarks.
 */
public final class Main {
    private static boolean verbose;
    private static String time;
    private static String pattern;

    private Main() {}

    private static void runAllBenchmarks(Class<?> c) {
        Method[] methods = Reflect.benchmarkMethods(c);
        if (methods == null) {
            return;
        }

        int longest = 0;
        for (Method m: methods) {
            longest = Math.max(longest, m.getName().length());
        }

        for (Method m: methods) {
            String s = m.getName();
            Result r = Bee.runBenchmark(m);
            System.out.printf("%" + longest + "s\t%s\n", s, r);
        }
    }

    private static void usage() {
        System.out.println("usage: jaybee classes");
        System.exit(1);
    }

    /**
     * The main method.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Flags f = new Flags();
        f.bool("verbose", (Boolean b) -> Main.verbose = b);
        f.string("time", (String t) -> Main.time = t);
        f.string("pattern", (String p) -> Main.pattern = p);
        args = f.parse(args);

        if (args.length < 1) {
            usage();
        }

        for (String a: args) {
            Class<?> c = Reflect.load(a);
            runAllBenchmarks(c);
        }
    }
}
