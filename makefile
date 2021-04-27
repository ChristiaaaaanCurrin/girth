JAVAFX_MODULE=--module-path javafx-sdk-11.0.2/lib --add-modules ALL-MODULE-PATH

.PHONY: test main clean package

clean:
	rm *.class
	rm *.jar

test: Graph.class
	java test/GraphTester.java<test/input.txt> test/output.txt
	diff test/key.txt test/output.txt

%.class: %.java
	javac $(JAVAFX_MODULE) $<

main: Girth.class
	java $(JAVAFX_MODULE) Girth

package:
	zip -r girth.zip *.java test report makefile
