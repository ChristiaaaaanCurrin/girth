this:
	javac ArrayIndexOrdering.java Graph.java
	java GraphTester.java<input.txt> output.txt
	diff key.txt output.txt
	rm *.class
