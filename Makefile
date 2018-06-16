# If you're building on a UNIX system, this might be preferable to running
# maven directly. You'll also get a (fake) jaybee executable this way.

JARPATH = $(shell find -name 'jaybee-*.jar' -print)
JARNAME = $(shell basename $(JARPATH))
JFLAGS = -Xdiags:verbose -Xlint:all

.PHONY: maven examples clean

all: maven jaybee examples

maven:
	mvn install

jaybee: Executable.txt
	cat Executable.txt >$@
	uuencode $(JARNAME) <$(JARPATH) >>$@
	chmod +x $@

examples:
	javac -cp $(JARPATH) $(JFLAGS) examples/*.java

clean:
	mvn clean
	rm -f jaybee
	rm -f examples/*.class
