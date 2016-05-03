# Produces the jar file. In a truly roundabout way. Broken tools be broken.
ALL=Classes.txt jaybee.jar jaybee

all: $(ALL)

jaybee.jar: Classes.txt Manifest.txt
	jar cfm $@ Manifest.txt @Classes.txt

Classes.txt:
	find com -name '*.class' -print >Classes.txt

jaybee: jaybee.jar
	cat Executable.txt >$@
	uuencode $^ <$^ >>$@
	chmod +x $@

clean:
	rm -rf $(ALL)
