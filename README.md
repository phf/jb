# jb: Simple Go-style benchmarking for Java

![J Bee](gfx/jb-small.jpg)

Welcome to `jb`, also known as `jaybee` out in the shell.

This is a port of Go's benchmarking facilities to Java. I needed it for a
course I am scheduled to teach in Fall 2016, mostly to make it easier on
students to get some basic performance metrics for their code.

I borrowed *very* liberally from the Go original as well as from the ports
listed below. One might say that my only accomplishment is to package things
in a slightly more Java-like way.

(Or maybe the only *real* advantage is that I also remixed a cute logo for it?
Bzzz! Oh, I would like to apologize for taking the whole bee metaphor a little
*too* far in the source code.)

**I welcome pull requests!**
In particular, I'd love for folks with more Java experience to add support for
established Java build tools.

## Usage

If `StringAppend.class` contains a bunch of benchmarks, here's how you run
them (provided `jaybee` is suitably aliased):

```
$ jaybee StringAppend
       string	      1000	   1042721 ns/op	   2.77 MB/s	    159592 B/op
 stringBuffer	     30000	     40549 ns/op	  71.27 MB/s	      5313 B/op
stringBuilder	     50000	     24185 ns/op	 119.50 MB/s	      1545 B/op
```

You get (a) the number of times the benchmark was run, (b) the time it took
per iteration, (c) the amount of data processed per second, and (d) the
number of bytes allocated per iteration. (That last number can be rather
flakey because the garbage collector doesn't seem to be overly deterministic.)

## Credits / Inspiration

Original Go Documentation:
https://golang.org/pkg/testing/#hdr-Benchmarks

Dustin Sallings' Testing Bee C Port:
https://github.com/dustin/testingbee

Keith Rarick's port of Testing Bee into ct:
https://github.com/kr/ct

Someone else's Java port (found after I finished my first version):
https://gist.github.com/a2800276/2000141

Yong Mook Kim taught me about Java annotations:
http://www.mkyong.com/java/java-custom-annotations-example/

Bee Image Design Credits:
http://www.vecteezy.com/
(Original at http://www.vecteezy.com/vector-art/94396-spring-bee-vector-icons)

Logo Font:
Philipp Poll's Linux Biolinum (see http://www.linuxlibertine.org/ for more)

## Licenses

The original Go code is under a BSD-style license.
Dustin and Keith used the MIT license,
the anonymous Java port doesn't have a license attached.
Whatever code I actually wrote for `jb` is under an MIT license.
You figure it out.
