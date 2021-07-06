JAVAFX_MODULE=--module-path javafx-sdk-11.0.2/lib --add-modules ALL-MODULE-PATH

.PHONY: test main clean package

clean:
	rm -f *.class

test: Graph.class
	java test/GraphTester.java<test/input.txt> test/output.txt
	diff test/key.txt test/output.txt

%.class: %.java clean
	javac $(JAVAFX_MODULE) $<

main: Girth.class
	java $(JAVAFX_MODULE) Girth

package:
	zip -r girth.zip *.java test report makefile
