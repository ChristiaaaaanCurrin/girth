test:
	javac ArrayIndexOrdering.java Graph.java
	java GraphTester.java<test/input.txt> test/output.txt
	diff test/key.txt test/output.txt
this:
	javac --module-path javafx-sdk-11.0.2/lib --add-modules ALL-MODULE-PATH Girth.java
	java --module-path javafx-sdk-11.0.2/lib --add-modules ALL-MODULE-PATH Girth
