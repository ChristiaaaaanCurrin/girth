import java.util.Arrays;

public class Graph {
  // instance variables
  protected int[][] adjacencyMatrix;

  // constructors ------------------------------ 
  public Graph(int order) {
    adjacencyMatrix = new int[order][order];
  }

  public Graph(int[][] adjacencyMatrix) {
    this.adjacencyMatrix = adjacencyMatrix;
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
    adjacencyMatrix[v] = adjacencyMatrix[adjacencyMatrix.length - 1];
    adjacencyMatrix = Arrays.copyOf(adjacencyMatrix, adjacencyMatrix.length - 1);
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      adjacencyMatrix[i][v] = adjacencyMatrix[i][adjacencyMatrix.length - 1];
      adjacencyMatrix[i] = Arrays.copyOf(adjacencyMatrix[i], adjacencyMatrix.length- 1);
    }
  }

  public void contractEdge(int v, int u) {
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      adjacencyMatrix[v][i] = Math.max(adjacencyMatrix[v][i], adjacencyMatrix[u][i]); 
    }
    deleteVertex(u);
  }

  public void addVertex() {
    adjacencyMatrix = Arrays.copyOf(adjacencyMatrix, adjacencyMatrix.length+1);
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      adjacencyMatrix[i] = Arrays.copyOf(adjacencyMatrix[i], adjacencyMatrix.length+1);
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
    return adjacencyMatrix.length;
  }

  public int getSize() {
    int degreeSum = 0;
    for (int[] neighborhood : adjacencyMatrix) {
      for (int u : neighborhood) {
         if (0 != u) {
           degreeSum++;
         }
      }
    }
    return degreeSum / 2;
  }

  public int getDegree(int v) {
    int degree = 0;
    for (int u : adjacencyMatrix[v]) {
      if (0 != u) {
        degree++;
      }
    }
    return degree;
  }

  public int getInDegree(int v) {
    int degree = 0;
    for (int u = 0; u < adjacencyMatrix.length; u++) {
      if (isAdjacent(u, v)) {
        degree++;
      }
    }
    return degree; 
  }

  public int getMinimumDegree() {
    int minDegree = Integer.MAX_VALUE;
    for (int v = 0; v < adjacencyMatrix.length; v++) {
      minDegree = Math.min(minDegree, getDegree(v));
    }
    return minDegree;
  }

  public int getMaximumDegree() {
    int maxDegree = 0; 
    for (int v = 0; v < adjacencyMatrix.length; v++) {
      maxDegree = Math.max(maxDegree, getDegree(v));
    }
    return maxDegree;
  }

  public int[] getDegreeSequence() {
    int[]  degreeSequence = new int[adjacencyMatrix.length];
    for (int v = 0; v < adjacencyMatrix.length; v++) {
      degreeSequence[v] = getDegree(v);
    }
    return degreeSequence;
  }

  public int[] getNeighborhood(int v) {
    int degree = 0;
    int[] neighborhood = new int[adjacencyMatrix.length];
    for (int u = 0; u < adjacencyMatrix.length; u++) {
      if (isAdjacent(v, u)) {
        neighborhood[degree] = u;
        degree++;
      }
    }
    return Arrays.copyOf(neighborhood, degree);
  }

  public int[] getInNeighborhood(int v) {
    int degree = 0;
    int[] neighborhood = new int[adjacencyMatrix.length];
    for (int u = 0; u < adjacencyMatrix.length; u++) {
      if (isAdjacent(u, v)) {
        neighborhood[degree] = u;
        degree++;
      }
    }
    return Arrays.copyOf(neighborhood, degree);
  }

  // subgraph and compelement generators ------- 
  public Graph getCopy() {
		return new Graph(Arrays.copyOf(adjacencyMatrix, adjacencyMatrix.length));
  }

  public Graph getComplement() {
    Graph complement = new Graph(getOrder());
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      for (int j = 0; j < adjacencyMatrix.length; j++) {
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
