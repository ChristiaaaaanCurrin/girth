public class GraphTester {
  public static void main(String[] args) {
    Graph g = Graph.completeGraph(3);
    System.out.println(g);
    System.out.println(g.getDistance(1,2));
    System.out.println(g.isConnected());
    g.addVertex();
    System.out.println(g.isConnected());
  }
}
