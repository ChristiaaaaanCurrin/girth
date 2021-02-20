import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Comparator;

public class Graph {
  // instance variables ------------------------
  // ------------------------------------------- 
  protected int[][] adjacencyMatrix; // this array is always bigger than the order of the graph in both dimensions
  protected int order;               // order is the number of vertices in the graph

  // constructors ------------------------------ 
  // ------------------------------------------- 
  public Graph(int order) {
    adjacencyMatrix = new int[2*order][2*order];
    this.order = order;
  }

  public Graph(int order, int[][] adjacencyMatrix) {
    this.order = order;
    this.adjacencyMatrix = Arrays.copyOf(adjacencyMatrix, 2*order);
  }

  // mutator methods --------------------------- 
  // ------------------------------------------- 
  public void deleteEdge(int v, int u) {
    adjacencyMatrix[v][u] = 0;
    adjacencyMatrix[u][v] = 0;
  }

  public void deleteDirectedEdge(int v, int u) {
    adjacencyMatrix[v][u] = 0;
  }

  public void deleteVertex(int v) {
    adjacencyMatrix[v] = adjacencyMatrix[order - 1]; // replace deleted vertex with last vertex
    for (int i = 0; i < order; i++) {
      adjacencyMatrix[i][v] = adjacencyMatrix[i][order - 1]; // replace deleted vertex with last vertex for all  neighborhoods
    }
    order--; // deleted vertex means that the order decreases by 1
  }

  public void contractEdge(int v, int u) {
    for (int i = 0; i < order; i++) {
      adjacencyMatrix[v][i] = Math.max(adjacencyMatrix[v][i], adjacencyMatrix[u][i]); // combine all edges out of v and u
      adjacencyMatrix[i][v] = Math.max(adjacencyMatrix[i][v], adjacencyMatrix[i][u]); // combine all edges into v and u
    }
    deleteVertex(u); // vertex u is now redundant with vertex v
  }

  public void addVertex() {
    order++;
    if (order >= adjacencyMatrix.length) {
      adjacencyMatrix = Arrays.copyOf(adjacencyMatrix, 2*order); // if adjacencyMatrix is not big enough, double it
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

  public void swapVertices(int v, int u) {
    int[] vN = adjacencyMatrix[v], uN = adjacencyMatrix[u];
    adjacencyMatrix[v] = uN; adjacencyMatrix[u] = vN;
  }

  // graph quearies ---------------------------- 
  // ------------------------------------------- 
  public String toString() {
    String edges = "";
    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        edges = edges + getEdge(i,j) + "\t";
      }
      edges = edges + "\n";
    }
    return edges;
  }

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
    int minDegree = Integer.MAX_VALUE; // start by assuming all vertices have infinite degree
    for (int v = 0; v < order; v++) {
      minDegree = Math.min(minDegree, getDegree(v)); // correct for every new vertex with lower degree
    }
    return minDegree;
  }

  public int getMaximumDegree() {
    int maxDegree = 0; // start by assuming all vertices have 0 degree
    for (int v = 0; v < order; v++) {
      maxDegree = Math.max(maxDegree, getDegree(v)); // correct for every new vertex with higher degree
    }
    return maxDegree;
  }

  public int[] getDegreeSequence() {
    int[]  degreeSequence = new int[order]; // as many degrees as vertices
    for (int v = 0; v < order; v++) {
      degreeSequence[v] = getDegree(v); // get degree for each vertex
    }
    return degreeSequence; // I am not sorting the degree sequence from least to greatest
  }

  public int[] getNeighborhood(int v) { // returns array of all vertices that are adjacent to v
    int degree = 0;
    int[] neighborhood = new int[order]; // maximum neighborhood size is the entire graph
    for (int u = 0; u < order; u++) {
      if (isAdjacent(v, u)) {
        neighborhood[degree] = u; // add all adjacent vertices to neighborhood
        degree++; // bigger neighborhood = bigger degree
      }
    }
    return Arrays.copyOf(neighborhood, degree); // return smallest possible array 
  }

  public int[] getInNeighborhood(int v) { // exactly the same as getNeighborhood, but reverses direction for directed graphs
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

  public int getDistance(int v, int u) {
    boolean [] visited = new boolean[order];
    int[] distances = new int[order];
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[v] = 0;
    ArrayIndexOrdering c = new ArrayIndexOrdering(distances);
    PriorityQueue<Integer> visiting = new PriorityQueue(order, c); 
    visiting.add(v);
    while (visiting.size() > 0) {
      v = visiting.poll();
      visited[v] = true;
      for (int w : getNeighborhood(v)) {
        distances[w] = Math.min(distances[w], distances[v] + getEdge(v, w));
        if (u == w) {
          return distances[u];
        }
        if (!visited[w]) {
          visiting.add(w);
        }
      } 
    }
    return distances[u];
  }

  public boolean isConnected(int v, int u) {
    boolean [] visited = new boolean[order];
    LinkedList<Integer> visiting = new LinkedList(); 
    visiting.add(v);
    while (visiting.size() > 0) {
      v = visiting.poll();
      visited[v] = true;
      for (int w : getNeighborhood(v)) {
        if (u == w) {
          return true; 
        }
        if (!visited[w]) {
          visiting.add(w);
        }
      } 
    }
    return false;
  }

  public boolean isConnected() {
    boolean [] visited = new boolean[order];
    LinkedList<Integer> visiting = new LinkedList(); 
    visiting.add(0);
    while (visiting.size() > 0) {
      Integer v = visiting.poll();
      visited[v] = true;
      for (int u : getNeighborhood(v)) {
        if (!visited[u]) {
          visiting.add(u);
        }
      } 
    }
    for (int u = 0; u < order; u++) {
      if (!visited[u]) {
        return false;
      }
    }
    return true;
  }

  // subgraph and compelement generators ------- 
  // ------------------------------------------- 
  public Graph getCopy() { // makes a perfect copy of the graph that is completely independent
		return new Graph(order, adjacencyMatrix);
  }

  public Graph getComplement() { // creates a copy of the graph where all the edges become non-edges and all the non-edges become eddges
    Graph complement = new Graph(order); // the constructor creates the empty graph order n by default
    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        if (false == isAdjacent(i, j)) {
          complement.addEdge(i,j); // only add edges for non-edges
        }
      }
    }
    return complement;
  }

  public Graph getInducedSubgraph(int[] vs) { // create subgraph with a set of vertices and all the edges between them
    Graph subGraph = new Graph(vs.length);
    for (int i = 0; i < vs.length; i++) {
      for (int j = 0; j < vs.length; j++) {
        subGraph.addEdge(i,j, getEdge(i, j)); // add any existing edges between vertices of the subgraph
      }
    }
    return subGraph;
  }

  // graph operators --------------------------- 
  // ------------------------------------------- 
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
  // ------------------------------------------- 
  public static Graph completeGraph(int n) {
    Graph g = new Graph(n);
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        if (i != j) {
          g.addEdge(i,j);
        }
      }
    }
    return g;
  }

  public static Graph completeGraph(int n, int m) {
    Graph g = new Graph(n+m);
    for (int i = 0; i < n; i++) {
      for (int j = n; j < n+m; j++) {
        if (i != j) {
          g.addEdge(i,j);
        }
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
