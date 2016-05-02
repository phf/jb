package com.github.phf.jb;

import java.lang.reflect.Method;

/**
 * The jb tool to run benchmarks.
 */
public final class Main {
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
        if (args.length < 1) {
            usage();
        }

        for (String a: args) {
            Class<?> c = Reflect.load(a);
            runAllBenchmarks(c);
        }
    }
}
