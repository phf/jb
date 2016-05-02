JFLAGS=-Xdiags:verbose -Xlint:all

all: jaybee examples

jaybee:
	javac $(JFLAGS) -d bin/ src/com/github/phf/jb/*.java
	$(MAKE) -C bin/

examples:
	javac -cp bin/ $(JFLAGS) *.java

clean:
	$(MAKE) -C bin clean
	rm -rf *.class bin/com*
