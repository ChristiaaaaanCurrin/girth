that:
	javac ArrayIndexOrdering.java Graph.java
	java GraphTester.java<input.txt> output.txt
	diff key.txt output.txt
	rm *.class
this:
	javac --module-path javafx-sdk-11.0.2/lib --add-modules ALL-MODULE-PATH ArrayIndexOrdering.java Graph.java DragController.java Girth.java
	java --module-path javafx-sdk-11.0.2/lib --add-modules ALL-MODULE-PATH Girth
