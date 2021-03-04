public class GraphTester {
  public static void main(String[] args) {
    Graph g = Graph.completeGraph(3);
    infoDump(g);
    g.addVertex();
    infoDump(g);
    Graph h = Graph.generalizedPetersonGraph(5, 2);
    infoDump(h);
    Graph t = h.getNormalSpanningTree(0);
    infoDump(t);
  }

  public static void infoDump(Graph g) {
    System.out.println("====INFO DUMP====");
    System.out.print(g);
    System.out.println("order: " + g.getOrder());
    System.out.println("size: " + g.getSize());
    System.out.print("degree sequence: {");
    for (int d : g.getDegreeSequence()) {System.out.print(d + ", ");}
    System.out.println("}");
    System.out.println("is connected: " + g.isConnected());
    System.out.println("girth: " + g.getGirth());
  }
}
