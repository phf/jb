# Produces the jar file. In a truly roundabout way. Broken tools be broken.
jar: Classes.txt
	jar cfm jaybee.jar Manifest.txt @Classes.txt

Classes.txt:
	find com -name '*.class' -print >Classes.txt

clean:
	rm -rf Classes.txt jaybee.jar
