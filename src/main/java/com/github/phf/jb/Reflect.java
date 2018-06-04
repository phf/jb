package com.github.phf.jb;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.nio.file.FileSystems;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Reflection in the service of bees. Puts all the code related to reflection
 * in one place. TODO Don't log to stderr, the main program should do that.
 */
final class Reflect {
    private Reflect() {}

    /**
     * Load class from current directory. Note that we silently handle a bunch
     * of exceptions here, mapping them all to a null return.
     *
     * @param name Name of class.
     * @return Class object or null if loading fails.
     */
    static Class<?> load(String name) {
        // TODO load from path instead?
        Class<?> c = null;
        try {
            URL url = FileSystems.getDefault().getPath("").toUri().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{url});
            c = loader.loadClass(name);
        } catch (ClassNotFoundException e) {
            System.err.println(e);
        } catch (MalformedURLException e) {
            System.err.println(e);
        }
        return c;
    }

    /**
     * Check if given method is a valid benchmark method.
     *
     * @param m Method to check.
     * @return True if benchmark method, false otherwise.
     */
    static boolean validBenchmarkMethod(Method m) {
        if (!m.isAnnotationPresent(Bench.class)) {
            return false;
        }

        String name = m.getName();

        Class<?>[] pts = m.getParameterTypes();
        if (pts == null || pts.length != 1) {
            System.err.printf("method %s must take 1 parameter, not %d\n",
                name, pts.length);
            return false;
        }
        if (!pts[0].equals(Bee.class)) {
            System.err.printf("method %s must take %s parameter, not %s\n",
                name, Bee.class, pts[0]);
            return false;
        }

        if (m.getReturnType() != Void.TYPE) {
            System.err.printf("method %s must return void, not %s\n",
                name, m.getReturnType());
            return false;
        }

        // Used to be that only static methods were allowed. Switched to the
        // opposite to make inheritance between benchmark classes work. That
        // way we can write "interface benchmarks" that run against several
        // implementations.
        if (Modifier.isStatic(m.getModifiers())) {
            System.err.printf("method %s must not be static\n", name);
            return false;
        }

        // Reflective calls only work with public methods.
        if (!Modifier.isPublic(m.getModifiers())) {
            System.err.printf("method %s must be public\n", name);
            return false;
        }

        return true;
    }

    /**
     * Return benchmark methods found in given class. The array is sorted by
     * method name. Note that the Go implementation processes benchmarks in
     * declaration order, alas Java's reflection API doesn't guarantee that.
     *
     * @return Array of benchmark methods or null if class has none.
     */
    static Method[] benchmarkMethods(Class<?> c) {
        SortedMap<String, Method> methods = new TreeMap<>();
        for (Method m: c.getMethods()) { // getDeclaredMethods skips inherited
            if (validBenchmarkMethod(m)) {
                methods.put(m.getName(), m);
            }
        }

        if (methods.size() <= 0) {
            return null;
        }

        Method[] result = new Method[methods.size()];
        int i = 0;
        for (SortedMap.Entry<String, Method> x: methods.entrySet()) {
            Method m = x.getValue();
            result[i++] = m;
        }
        return result;
    }
}
