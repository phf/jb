package com.github.phf.jb;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Flags is a simplistic parser for command-line flags. You know, things like
 * "cat -n -v oh no" and friends. Flags can be strings (yay!) but they always
 * require a value after them (boo!) for consistency. (Yes, even booleans.)
 *
 * Use string, bool, integer to register handlers for flags. Note that Java 8
 * lambdas often result in one-liners here. Neat?
 *
 * TODO don't write to stderr directly, let main do it
 * TODO write a real flags package that could stand on its own
 */
final class Flags {
    /**
     * Flagger and friends interpret flags. Yes, we hardcode the different
     * kinds of flags we can handle, sorry. Extensibility would be nice but it
     * comes at too steep a price.
     */
    interface Flagger {
    }

    interface Stringer extends Flagger {
        void set(String s);
    }

    interface Booler extends Flagger {
        void set(Boolean b);
    }

    interface Inter extends Flagger {
        void set(Integer i);
    }

    private HashMap<String, Flagger> flags = new HashMap<String, Flagger>();

    void string(String flag, Stringer f) {
        this.flags.put(flag, f);
    }

    void bool(String flag, Booler f) {
        this.flags.put(flag, f);
    }

    void integer(String flag, Inter f) {
        this.flags.put(flag, f);
    }

    /**
     * Parse the given arguments for flags. Each actual flag found is checked
     * as far as possible and then the corresponding handler is invoked. The
     * remaining arguments are collected and returned as "leftovers" for the
     * main program to deal with.
     */
    String[] parse(String[] args) {
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < args.length; i++) {
            if (!args[i].startsWith("-")) {
                out.add(args[i]);
                continue;
            }

            String f = args[i].substring(1);
            if (!this.flags.containsKey(f)) {
                System.err.printf("unknown flag -%s\n", f);
                continue;
            }

            if (i + 1 >= args.length) {
                System.err.printf("flag -%s requires argument\n", f);
                continue;
            }

            String d = args[i + 1];
            i++;
            Flagger q = this.flags.get(f);

            if (q instanceof Stringer) {
                Stringer r = (Stringer) q;
                r.set(d);
            } else if (q instanceof Booler) {
                Booler r = (Booler) q;
                if ("true".equals(d)) {
                    r.set(true);
                } else if ("false".equals(d)) {
                    r.set(false);
                } else {
                    System.err.printf(
                        "invalid boolean %s, should be true or false\n",
                        d
                    );
                }
            } else if (q instanceof Inter) {
                Inter r = (Inter) q;
                r.set(Integer.valueOf(d));
            } else {
                System.err.printf("unknown flagger %s\n", q);
            }
        }
        String[] x = new String[out.size()];
        return out.toArray(x);
    }
}
