public class Edge {
  private Vertex[] vertexSet;

  public Edge (Vertex v, Vertex u) {
    vertexSet = new Vertex[2];
    vertexSet[0] = v;
    vertexSet[1] = u;
  }

  public Vertex[] getVertexSet() {return vertexSet;}

  public boolean hasVertex(Vertex v) {
    for (Vertex u : vertexSet) {
      if (u ==v) {return true;}
    }
    return false;
  }
}
