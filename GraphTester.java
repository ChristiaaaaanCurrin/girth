public class GraphTester {
  public static void main(String[] args) {
    Graph g = Graph.completeGraph(3);
    System.out.println(g);
    System.out.println(g.getDistance(1,2));
    System.out.println(g.isConnected());
    g.addVertex();
    System.out.println(g.isConnected());
    Graph h = new Graph(1);
    System.out.println(h.isConnected());
    h = Graph.completeGraph(2);
    System.out.println(h.isConnected());
  }
}
