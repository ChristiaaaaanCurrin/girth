import java.util.Arrays;

public class Graph {
  // instance variables
  protected int[][] adjacencyMatrix;
  protected int order;

  // constructors ------------------------------ 
  public Graph(int order) {
    adjacencyMatrix = new int[2*order][2*order];
    this.order = order;
  }

  public Graph(int order, int[][] adjacencyMatrix) {
    this.order = order;
    this.adjacencyMatrix = Arrays.copyOf(adjacencyMatrix, 2*order);
  }

  // mutator methods --------------------------- 
  public void deleteEdge(int v, int u) {
    adjacencyMatrix[v][u] = 0;
    adjacencyMatrix[u][v] = 0;
  }

  public void deleteDirectedEdge(int v, int u) {
    adjacencyMatrix[v][u] = 0;
  }

  public void deleteVertex(int v) {
    adjacencyMatrix[v] = adjacencyMatrix[order - 1];
    for (int i = 0; i < order; i++) {
      adjacencyMatrix[i][v] = adjacencyMatrix[i][order - 1]; 
    }
    order--;
  }

  public void contractEdge(int v, int u) {
    for (int i = 0; i < order; i++) {
      adjacencyMatrix[v][i] = Math.max(adjacencyMatrix[v][i], adjacencyMatrix[u][i]); 
    }
    deleteVertex(u);
  }

  public void addVertex() {
    order++;
    if (order >= adjacencyMatrix.length) {
      adjacencyMatrix = Arrays.copyOf(adjacencyMatrix, 2*order);
    }
  }

  public void addEdge(int v, int u) {
    adjacencyMatrix[v][u] = 1;
    adjacencyMatrix[u][v] = 1;
  }

  public void addDirectedEdge(int v, int u) {
    adjacencyMatrix[v][u] = 1;
  }

  public void addEdge(int v, int u, int w) {
    adjacencyMatrix[v][u] = w;
    adjacencyMatrix[u][v] = w;
  }

  public void addDirectedEdge(int v, int u, int w) {
    adjacencyMatrix[v][u] = w;
  }

  // graph quearies ---------------------------- 
  public boolean isAdjacent(int v, int u) {
    return 0 != adjacencyMatrix[v][u];
  }

  public int getEdge(int v, int u) {
    return adjacencyMatrix[v][u];
  }

  public int getOrder() {
    return order;
  }

  public int getSize() {
    int degreeSum = 0;
    for (int v = 0; v < order; v++) {
      for (int u = 0; u < order; u++) {
         if (0 != adjacencyMatrix[v][u]) {
           degreeSum++;
         }
      }
    }
    return degreeSum / 2;
  }

  public int getDegree(int v) {
    int degree = 0;
    for (int u = 0; u < order; u++) {
      if (0 != adjacencyMatrix[v][u]) {
        degree++;
      }
    }
    return degree;
  }

  public int getInDegree(int v) {
    int degree = 0;
    for (int u = 0; u < order; u++) {
      if (isAdjacent(u, v)) {
        degree++;
      }
    }
    return degree; 
  }

  public int getMinimumDegree() {
    int minDegree = Integer.MAX_VALUE;
    for (int v = 0; v < order; v++) {
      minDegree = Math.min(minDegree, getDegree(v));
    }
    return minDegree;
  }

  public int getMaximumDegree() {
    int maxDegree = 0; 
    for (int v = 0; v < order; v++) {
      maxDegree = Math.max(maxDegree, getDegree(v));
    }
    return maxDegree;
  }

  public int[] getDegreeSequence() {
    int[]  degreeSequence = new int[order];
    for (int v = 0; v < order; v++) {
      degreeSequence[v] = getDegree(v);
    }
    return degreeSequence;
  }

  public int[] getNeighborhood(int v) {
    int degree = 0;
    int[] neighborhood = new int[order];
    for (int u = 0; u < order; u++) {
      if (isAdjacent(v, u)) {
        neighborhood[degree] = u;
        degree++;
      }
    }
    return Arrays.copyOf(neighborhood, degree);
  }

  public int[] getInNeighborhood(int v) {
    int degree = 0;
    int[] neighborhood = new int[order];
    for (int u = 0; u < order; u++) {
      if (isAdjacent(u, v)) {
        neighborhood[degree] = u;
        degree++;
      }
    }
    return Arrays.copyOf(neighborhood, degree);
  }

  // subgraph and compelement generators ------- 
  public Graph getCopy() {
		return new Graph(order, adjacencyMatrix);
  }

  public Graph getComplement() {
    Graph complement = new Graph(order);
    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        if (false == isAdjacent(i, j)) {
          complement.addEdge(i,j);
        }
      }
    }
    return complement;
  }

  public Graph getInducedSubgraph(int[] vs) {
    Graph subGraph = new Graph(vs.length);
    for (int i = 0; i < vs.length; i++) {
      for (int j = 0; j < vs.length; j++) {
        subGraph.addEdge(i,j, getEdge(i, j));
      }
    }
    return subGraph;
  }

  // graph operators --------------------------- 
  public static Graph graphSum(Graph g1, Graph g2) {
    Graph h = new Graph(Math.max(g1.getOrder(), g2.getOrder()));
    for (int i = 0; i < h.getOrder(); i++) {
      for (int j = 0; j < h.getOrder(); j++) {
        h.addEdge(i, j, Math.max(g1.getEdge(i, j), g2.getEdge(i, j)));
      }
    }
    return h;
  }

  // elementary graphs ------------------------- 
  public static Graph completeGraph(int n) {
    Graph g = new Graph(n);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        g.addEdge(i,j);
      }
    }
    return g;
  }

  public static Graph completeGraph(int n, int m) {
    Graph g = new Graph(n+m);
    for (int i = 0; i < n; i++) {
      for (int j = n; j < n+m; j++) {
        g.addEdge(i,j);
      }
    }
    return g;
  }

  public static Graph pathGraph(int n) {
    Graph g = new Graph(n);
    for (int i = 1; i < n; i++) {
      g.addEdge(i-1, i);
    } 
    return g;
  }

  public static Graph cycleGraph(int n) {
    Graph g = pathGraph(n);
    g.addEdge(0, n-1);
    return g;
  }
}
