public class GraphTester {
  public static void main(String[] args) {
    Graph g = Graph.completeGraph(3);
    System.out.println(g);
    System.out.println(g.getDistance(1,2));
    g.addVertex();
    Graph h = Graph.graphSum(Graph.cycleGraph(6), new Graph(11));
    h = Graph.completeGraph(6);
  }
}
