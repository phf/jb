package com.github.phf.jb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for benchmarks. Just put @Bench in front of the methods with
 * benchmarking code. Those methods must take exactly one parameter of type
 * Bee.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bench {
}
