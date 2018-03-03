package com.github.phf.jb;

/**
 * Result represents the outcome of a benchmark. It exists to put the
 * formatting code in one place.
 */
final class Result {
    private int n;  // Number of iterations.
    private long t; // Total time taken.
    private long p; // Bytes processed in duration.
    private long m; // Bytes allocated in duration.

    Result(int iterations, long duration, long throughput, long memory) {
        this.n = iterations;
        this.t = duration;
        this.p = throughput;
        this.m = memory;
    }

    private long nsPerOp() {
        return this.n <= 0 ? 0 : this.t / this.n;
    }

    private double mbPerSec() {
        if (this.p <= 0 || this.t <= 0 || this.n <= 0) {
            return 0;
        }
        return (this.p / 1e6) / (this.t / 1e9);
    }

    private long bytesPerOp() {
        return this.n <= 0 ? 0 : this.m / this.n;
    }

    @Override
    public String toString() {
        long ns = this.nsPerOp();
        long bs = this.bytesPerOp();

        String mb = "";
        if (this.p > 0) {
            double mps = this.mbPerSec();
            mb = String.format("\t%,11.2f MB/s", mps);
        }

        return String.format("%,14d\t%,14d ns/op%s\t%,14d B/op",
            this.n, ns, mb, bs);
    }
}
