public class GraphTester {
  public static void main(String[] args) {
    Graph g = Graph.completeGraph(3);
    infoDump(g);
    g.addVertex();
    infoDump(g);
    Graph h = Graph.petersonGraph(5, 2);
    infoDump(h);
    Graph t = h.getNormalSpanningTree(0);
    infoDump(t);
    Graph l = t.getLineGraph();
    infoDump(l);
    l = Graph.mycielskiGraph(3);
    infoDump(l);
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
    System.out.println("is tree: " + g.isTree());
    System.out.println("is forest: " + g.isForest());
    System.out.println("is path: " + g.isPath());
    System.out.println("is paths: " + g.isPaths());
    System.out.println("is cycle: " + g.isCycle());
    System.out.println("girth: " + g.getGirth());
    System.out.println("is clique: " + g.isClique());
    System.out.println("clique number: " + g.getCliqueNumber());
  }
}
