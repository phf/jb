package com.github.phf.jb;

/**
 * Exception for failed benchmarks. We raise this internally when a benchmark
 * calls fail() on a Bee.
 */
class BenchmarkFailedException extends RuntimeException {
    private static final long serialVersionUID = 0L;

    BenchmarkFailedException() {
        super();
    }

    BenchmarkFailedException(String reason) {
        super(reason);
    }
}
