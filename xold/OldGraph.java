import java.util.Arrays;
import java.util.ArrayList;

public class OldGraph {
  private Vertex[] vertexSet;
  private Edge[] edgeSet;

  public OldGraph(int order) {
    vertexSet = new Vertex[order];
    for (int i = 0; i < order; i++) {
      vertexSet[i] = new Vertex(Integer.toString(i));
    }
  }

  public OldGraph(Vertex[] vertices) {
    vertexSet = vertices;
    edgeSet = new Edge[0];
  }

  public OldGraph(Vertex[] vertices, Edge[] edges) {
    vertexSet = vertices;
    edgeSet = edges;
  }

  public int getOrder() {return vertexSet.length;}
  public int getSize() {return vertexSet.length;}
  public Vertex[] getVertexSet() {return vertexSet;}
  public   Edge[] getEdgeSet()   {return edgeSet;}

  public int getDegree(Vertex v) {
    int degree = 0;
    for (Edge e : edgeSet) {
      if (e.hasVertex(v)) {
        degree++;
      }
    }
    return degree;
  }
  
  public int getMaximumDegree() {
    int degree = 0;
    for (Vertex v : vertexSet) {
      degree = Math.max(degree, getDegree(v));
    }
    return degree;
  }

  public int getMinimumDegree() {
    int degree = 0;
    for (Vertex v : vertexSet) {
      degree = Math.min(degree, getDegree(v));
    }
    return degree;
  }

  public int[] getDegreeSequence() {
    int[] degreeSequence = new int[vertexSet.length];
    for (int i = 0; i < vertexSet.length; i++) {
      degreeSequence[i] = getDegree(vertexSet[i]);
    }
    return degreeSequence;
  }

  public boolean isAdjacent(Vertex v, Vertex u) {
    for (Edge e : edgeSet) {
      if (e.hasVertex(v) && e.hasVertex(u)) {
        return true;
      }
    }
    return false;
  }

  public Vertex[] getNeighborhood(Vertex v) {
    Vertex[] neighborhood = new Vertex[vertexSet.length];
    int degree = 0;
    for (Vertex u : vertexSet) {
      if (isAdjacent(v, u)) {
        neighborhood[degree] = u;
        degree++;
      }
    }
    return Arrays.copyOf(neighborhood, degree);
  }

  public boolean isConnected(Vertex v, Vertex u) {
    ArrayList<Vertex>   checked = new ArrayList();
    ArrayList<Vertex>  checking = new ArrayList();
    ArrayList<Vertex> nextCheck = new ArrayList();
    checking.add(v);
    while (checking.size() != 0) {
      for (Vertex x : checking) {
        if (isAdjacent(x, u)) {
          return true;
        }
        checked.add(x);
        for (Vertex y : getNeighborhood(x)) {
          if (checked.contains(y) == false) {
            nextCheck.add(y);
          }
        }
      }
      checking = nextCheck;
      nextCheck.clear();
    }
    return false;
  }

  public OldGraph getConnectedComponent(Vertex v) {
    ArrayList<Vertex>   checked = new ArrayList();
    ArrayList<Vertex>  checking = new ArrayList();
    ArrayList<Vertex> nextCheck = new ArrayList();
    checking.add(v);
    while (checking.size() != 0) {
      for (Vertex x : checking) {
        checked.add(x);
        for (Vertex y : getNeighborhood(x)) {
          if (checked.contains(y) == false) {
            nextCheck.add(y);
          }
        }
      }
      checking = nextCheck;
      nextCheck.clear();
    }
    return getInducedSubgraph(checked.toArray(new Vertex[checked.size()]));
  }

  public OldGraph getInducedSubgraph(Vertex[] vs) {
    int newSize = 0; 
    Edge[] inducedEdges = new Edge[edgeSet.length]; 
    for (Edge e : edgeSet) {
      for (Vertex v : vs) {
        for (Vertex u : vs) {
          if (e.hasVertex(v) && e.hasVertex(u)) {
            inducedEdges[newSize] = e;
            newSize++;
          }
        }
      }
    }
    return new OldGraph(vs, Arrays.copyOf(inducedEdges, newSize));
  }
}
