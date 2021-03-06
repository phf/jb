# jb: Simple Go-style benchmarking for Java

![Jay Bee](gfx/jb-small.jpg)

Welcome to `jb`, also known as `jaybee` out in the shell.

This is a port of
[Go's benchmarking facilities](https://golang.org/pkg/testing/#hdr-Benchmarks)
to Java.
I needed `jb` for the
[data structures course](https://www.cs.jhu.edu/~phf/2016/fall/cs226/)
I taught in [Fall 2016](https://www.cs.jhu.edu/~phf/2016/fall/cs226/), mostly
to make it easier on students to get some basic performance metrics for their
code.
The tool has been reasonably well-received over the years
([Spring 2017](https://www.cs.jhu.edu/~phf/2017/spring/cs226/),
[Fall 2017](https://www.cs.jhu.edu/~phf/2017/fall/cs226/),
[Spring 2018](https://www.cs.jhu.edu/~phf/2018/spring/cs226/))
and students who have previously learned about
[JUnit 4](https://junit.org/junit4/) seem to pick it up with ease.

I borrowed *very* liberally from the Go original as well as from the ports
listed below.
One might say that my only accomplishment was to package things in a slightly
more Java-like way.
(Or maybe the only *real* advantage is that I also remixed a cute logo for it?
Bzzz! Oh, I would like to apologize for taking the whole bee metaphor a little
*too* far in the source code.)

**I welcome pull requests!**

## Building

If you want to build `jaybee` yourself, you'll first need to install
[maven](https://maven.apache.org/).
I actually feel a bit guilty about having switched from a hacky `Makefile` to
an insanely huge monster of a build system, but hey, it's 2018 so let's waste
lots of cycles and even more space to do very simple things.

Once you have [maven](https://maven.apache.org/) installed go ahead and clone
the repository.
Then say `mvn install` and after waiting patiently for a while you'll have the
JAR file referenced below.

If you are on a UNIX system, you should be able to say `make` which will run
`mvn install` as above before packaging the JAR into a "fake executable" called
`jaybee` that can be used to run benchmarks more conveniently.
(At some point I might add `jaybeecee` as well, let's see.)

## Usage

If `StringAppend.java` contains a bunch of methods annotated with `@Bench`,
here's how you compile it (provided `jaybee.jar` is in the current directory):

```
$ javac -classpath jaybee.jar:. StringAppend.java
```

If `StringAppend.class` contains a bunch of compiled benchmark methods, here's
how you run them (provided `jaybee.jar` is in the current directory):

```
$ java -jar jaybee.jar StringAppend
       string	         5,000	       326,858 ns/op	       8.84 MB/s	       156,993 B/op
 stringBuffer	        50,000	        26,474 ns/op	     109.16 MB/s	        18,685 B/op
stringBuilder	       100,000	        13,622 ns/op	     212.15 MB/s	           339 B/op
```

You get (a) the number of times the benchmark was run, (b) the time it took
per iteration, (c) the amount of data processed per second, and (d) the
number of bytes allocated per iteration.
(That last number can be rather flakey because garbage collection behavior
is not exactly deterministic.)

## Disclaimer

The measurements `jb` produces are *not* particularly accurate.
They are, however, *accurate enough* to measure *obvious differences* in
performance.
(At least as long as `jb` has been used correctly that is.)
If accuracy is what you need, please use an *actual* profiler.

## Frequently Asked Questions

### Benchmarks Get Stuck

"*One of my benchmark methods gets stuck. When I print `reps` I notice a large
jump from 10,000 to 500,000 and the latter takes too long to be useful.*"

Whatever you do between `b.stop` and `b.start` does not take **any** time as
far as `jaybee` is concerned, but of course it takes **actual** time to do it.
So if you do 100 ms worth of setup followed by 1 ms worth of benchmark,
`jaybee` wants to run your benchmark 1,000 times to fill up a seconds worth of
time. But by running it 1,000 times, it will now take 100,000 ms = 100 s just
to do the setup!

Ideally you do any setup you need only once, not on every iteration of your
`reps` loop.
(It would probably be even better if, like JUnit, we had a `@Before` tag for
setup code.)

## Cartoon

![Bench Mark](gfx/cartoon-small.jpg)

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

Students who helped:
Austin Kemper (keeping me honest),
Yuqi "Royce" Ma (cartoon),
Brandon Lax (FAQ)

Companies who helped:
[SmartLogic](https://smartlogic.io/)
(much needed quiet time),
[BigHugeGames](https://bighugegames.com/)
(learned [ant](https://ant.apache.org/) and [maven](https://maven.apache.org/))

## Licenses

The original Go code is under a BSD-style license.
Dustin and Keith used the MIT license,
the anonymous Java port doesn't have a license attached.
Whatever code I actually wrote for `jb` is under an MIT license.
You figure it out.
