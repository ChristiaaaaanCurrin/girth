import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.Comparator;

public class Graph<V> {
  // instance variables ------------------------
  // ------------------------------------------- 
  private int[][] adjacencyMatrix;  // always bigger than the order of the graph in both dimensions
  private int order;                // order is the number of vertices in the graph
  private String cachedValues;      // contains a list of all the values that have been cached since the graph was last alterred
  private List<V> vertexSet;        // this can be used by other classes to keep track of the vertices

  // constructors ------------------------------ 
  // ------------------------------------------- 
  public Graph() {
    this(1, new int[2][2], new ArrayList<V>(1));
  }

  public Graph(int order) {
    this(order, new int[2*order][2*order], new ArrayList<V>(order));
  }

  private Graph(int order, int[][] adjacencyMatrix, List<V> vertexSet) {
    this.order = order;
    this.adjacencyMatrix = adjacencyMatrix;
		this.vertexSet = vertexSet;
    cachedValues = "";
  }

  // mutator methods --------------------------- 
  // ------------------------------------------- 
  public void setVertexSet(List<V> newSet) {
    vertexSet = newSet;
  }

  public void deleteEdge(int v, int u) {
    adjacencyMatrix[v][u] = 0;
    adjacencyMatrix[u][v] = 0;
    cachedValues = "";
  }

  public void deleteDirectedEdge(int v, int u) {
    adjacencyMatrix[v][u] = 0;
    cachedValues = "";
  }

  public void swapVertices(int v, int u) {
    int[] vN = adjacencyMatrix[v], uN = adjacencyMatrix[u];
    adjacencyMatrix[v] = uN; adjacencyMatrix[u] = vN;
    for (int i = 0; i < order; i++) {
      int iv = adjacencyMatrix[i][v], iu = adjacencyMatrix[i][u];
      adjacencyMatrix[i][v] = iu; adjacencyMatrix[i][u] = iv;
    }
  }

  public void deleteVertex(int v) {
    swapVertices(v, order-1);
    order--; // deleted vertex means that the order decreases by 1
    vertexSet.set(v, vertexSet.get(order));
    vertexSet.remove(order);
    addNeighborhood(order, getNeighborhood(order), 0); // clear neighborhood of deleted vertex
    cachedValues = "";
  }

  public void contractEdge(int v, int u) {
    for (int i = 0; i < order; i++) {
      adjacencyMatrix[v][i] = Math.max(adjacencyMatrix[v][i], adjacencyMatrix[u][i]); // combine all edges out of v and u
      adjacencyMatrix[i][v] = Math.max(adjacencyMatrix[i][v], adjacencyMatrix[i][u]); // combine all edges into v and u
    }
    deleteVertex(u); // vertex u is now redundant with vertex v
    cachedValues = "";
  }

  public void addVertex(V label) {
    order++;
    if (order >= adjacencyMatrix.length) {
      adjacencyMatrix = Arrays.copyOf(adjacencyMatrix, 2*order); // if adjacencyMatrix is not big enough, double it
      for (int v = 0; v < adjacencyMatrix.length; v++) {
        try {
          adjacencyMatrix[v] = Arrays.copyOf(adjacencyMatrix[v], 2*order); // also double all the neighborhood arrays
        } catch (NullPointerException e) {
          adjacencyMatrix[v] = new int[2*order];
        }
      }
    }
    vertexSet.add(label);
    cachedValues = "";
  }

  public void addVertex() {
    addVertex(null);
  }

  public void addEdge(int v, int u) {
    adjacencyMatrix[v][u] = 1;
    adjacencyMatrix[u][v] = 1;
    cachedValues = "";
  }

  public void addDirectedEdge(int v, int u) {
    adjacencyMatrix[v][u] = 1;
    cachedValues = "";
  }

  public void addEdge(int v, int u, int w) {
    adjacencyMatrix[v][u] = w;
    adjacencyMatrix[u][v] = w;
    cachedValues = "";
  }

  public void addDirectedEdge(int v, int u, int w) {
    adjacencyMatrix[v][u] = w;
    cachedValues = "";
  }

  public void addNeighborhood(int v, int[] us) {
    for (int u : us) {
      addEdge(v, u);
    }
    cachedValues = "";
  }

  public void addOutNeighborhood(int v, int[] us) {
    for (int u : us) {
      addDirectedEdge(v, u);
    }
    cachedValues = "";
  }

  public void addInNeighborhood(int v, int[] us) {
    for (int u : us) {
      addDirectedEdge(u, v);
    }
    cachedValues = "";
  }

  public void addNeighborhood(int v, int[] us, int w) {
    for (int u : us) {
      addEdge(v, u, w);
    }
    cachedValues = "";
  }

  public void addOutNeighborhood(int v, int[] us, int w) {
    for (int u : us) {
      addDirectedEdge(v, u, w);
    }
    cachedValues = "";
  }

  public void addInNeighborhood(int v, int[] us, int w) {
    for (int u : us) {
      addDirectedEdge(u, v, w);
    }
    cachedValues = "";
  }

  // graph quearies ---------------------------- 
  // ------------------------------------------- 
  public int vertex2int (Vertex v) {
    return vertexSet.indexOf(v);
  }

  private String string;
  public String toString() {
    if (!cachedValues.contains("string")) {
      string = "";
      for (int i = 0; i < order; i++) {
        for (int j = 0; j < order; j++) {
          string = string + getEdge(i,j) + "\t";
        }
        string = string + "\n";
      }
      cachedValues = cachedValues + "string";
    }
    return string;
  }

  public String fullArray() {
    String fullString = "";
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      for (int j = 0; j < adjacencyMatrix[i].length; j++) {
        fullString = fullString + getEdge(i,j) + "\t";
      }
      fullString = fullString + "\n";
    }
    return fullString;
  }

  public List<V> getVertexSet() {
    return vertexSet;
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

  private int size;
  public int getSize() {
    if (!cachedValues.contains("size")) {
      int degreeSum = 0;
      for (int v = 0; v < order; v++) {
        for (int u = 0; u < order; u++) {
           if (0 != adjacencyMatrix[v][u]) {
             degreeSum++;
           }
        }
      }
      size = degreeSum / 2;
      cachedValues = cachedValues + "size";
    }
    return size;
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

  private int minimumDegree;
  public int getMinimumDegree() {
    if (!cachedValues.contains("delta")) {
      minimumDegree = Integer.MAX_VALUE; // start by assuming all vertices have infinite degree
      for (int v = 0; v < order; v++) {
        minimumDegree = Math.min(minimumDegree, getDegree(v)); // correct for every new vertex with lower degree
      }
      cachedValues = cachedValues + "delta";
    }
    return minimumDegree;
  }

  private int maximumDegree;
  public int getMaximumDegree() {
    if (!cachedValues.contains("Delta")) {
      maximumDegree = 0; // start by assuming all vertices have 0 degree
      for (int v = 0; v < order; v++) {
        maximumDegree = Math.max(maximumDegree, getDegree(v)); // correct for every new vertex with higher degree
      }
      cachedValues = cachedValues + "Delta";
    }
    return maximumDegree;
  }

  private int[] degreeSequence;
  public int[] getDegreeSequence() {
    if (!cachedValues.contains("degreeSequence")) {
      degreeSequence = new int[order]; // as many degrees as vertices
      for (int v = 0; v < order; v++) {
        degreeSequence[v] = getDegree(v); // get degree for each vertex
      }
      cachedValues = cachedValues + "degreeSequence";
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

  public int[] getClosedNeighborhood(int v) { // returns array of all vertices that are adjacent to v
    int degree = 1;
    int[] neighborhood = new int[order]; // maximum neighborhood size is the entire graph
    neighborhood[0] = v;
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

  public int[] getDistances(int v) {
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
        if (!visited[w]) {
          visiting.add(w);
        }
      } 
    }
    return distances;
  }

  private int girth;
  public int getGirth() {
    if (!cachedValues.contains("girth")) {
      Graph g = getCopy();
      girth = Integer.MAX_VALUE;
      for (int v = 0; v < g.order; v++) {
        for (int u : g.getNeighborhood(v)) {
          g.deleteEdge(v, u);
          girth = Math.min(girth - 1, g.getDistance(v, u)) + 1;
          if (girth == 3) {
            cachedValues = cachedValues + "girth";
            return girth;
          }
        }
      }
      cachedValues = cachedValues + "girth";
    }
    return girth;
  }

  private int diameter;
  public int getDiameter() {
    if (!cachedValues.contains("diameter")) {
      diameter = 0;
      for (int i = 0; i < order; i++) {
        for (int j = 0; j < order; j++) {
          diameter = Math.max(diameter, getDistance(i,j));
        }
      }
      cachedValues = cachedValues + "diameter";
    }
    return diameter;
  }

  public boolean isClique() {
    for (int v = 0; v < order; v++) {
      for (int u = 0; u < order; u++) {
        if (!(v == u || isAdjacent(v, u))) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isClique(int[] vs) {
    for (int v : vs) {
      for (int u : vs) {
        if (!(v == u || isAdjacent(v, u))) {
          return false;
        }
      }
    }
    return true;
  }

  public boolean isCoclique(int[] vs) {
    for (int v : vs) {
      for (int u : vs) {
        if (isAdjacent(v, u)) {
          return false;
        }
      }
    }
    return true;
  }

  public int getCliqueNumber(int v) {
    int[] neighbors = getNeighborhood(v);
    return getInducedSubgraph(neighbors).getCliqueNumber() + 1;
  }

  public int getCliqueNumber() {
    if (isClique()) {
      return order;
    }
    int omega = 1;
    for (int v = 0; v < order; v++) {
      omega = Math.max(omega, getCliqueNumber(v));
    }
    return omega;
  }

  public int getCocliqueNumber(int v) {
    return getComplement().getCliqueNumber();
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

  public boolean isConnected(int [] vertices) {
    for (int i = 0; i < vertices.length; i++) {
      for (int j = i+1; j < vertices.length; j++) {
        if (!isConnected(vertices[i], vertices[j])) {
          return false;
        }
      }
    }
    return true;
  }
 
  public int[] getComponent(int v) {
    boolean[] visited = new boolean[order];
    LinkedList<Integer> visiting = new LinkedList();
    visiting.add(v);
    PriorityQueue<Integer> component = new PriorityQueue(order);
    component.add(v);
    int componentOrder = 0;
    
    while (visiting.size() > 0) {
      v = visiting.poll();
      visited[v] = true;
      componentOrder++;
      for (int u : getNeighborhood(v)) {
        if (!visited[u]) {
          visiting.add(u);
          component.add(u);
        }
      }
    }
    int[] returnArray = new int[componentOrder];
    for (int i = 0; i < componentOrder; i++) {
      returnArray[i] = component.poll();
    }
    return returnArray;
  }

  public int getComponentOrder(int v) {
    boolean[] visited = new boolean[order];
    LinkedList<Integer> visiting = new LinkedList();
    visiting.add(v);
    int componentOrder = 0;

    while (visiting.size() > 0) {
      v = visiting.poll();
      visited[v] = true;
      componentOrder++;
      for (int u : getNeighborhood(v)) {
        if (!visited[u]) {
          visiting.add(u);
        }
      }
    }
  return componentOrder;
  }

  public boolean isRegular(int k) {
    for (int v = 0; v < order; v++) {
      if (getDegree(v) != k) {
        return false;
      }
    }
    return true;
  }

  public boolean isRegular() {
    return isRegular(getDegree(0));
  }

  public boolean isCycle() {
    return isRegular(2) && isConnected();
  }

  public boolean isCubic() {
    return isRegular(3);
  }

  public boolean isPath() {
    return isPaths() && isConnected();
  }

  public boolean isPaths() {
    return 1 == getMinimumDegree() && 2 == getMaximumDegree();
  }

  public boolean isTree() {
    return getSize() == order - 1 && isConnected();
  }

  public boolean isForest() {
    return Integer.MAX_VALUE == getGirth();
  }

  // subgraph and compelement generators ------- 
  // ------------------------------------------- 
  public Graph getCopy() { // makes a perfect copy of the graph that is completely independent
    Graph copy = new Graph(order); // the constructor creates the empty graph order n by default
    for (int i = 0; i < order; i++) {
      for (int j = 0; j < order; j++) {
        copy.addEdge(i, j, getEdge(i, j)); // copy over all edges
      }
    }
    return copy;
  }

  public Graph getComplement() { // creates a copy of the graph where all the edges become non-edges and all the non-edges become eddges
    Graph complement = new Graph(order); // the constructor creates the empty graph order n by default
    for (int i = 0; i < order; i++) {
      for (int j = i+1; j < order; j++) {
        if (!isAdjacent(i, j) && i != j) {
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
        subGraph.addEdge(i,j, getEdge(vs[i], vs[j])); // add any existing edges between vertices of the subgraph
      }
    }
    return subGraph;
  }

  public Graph getNormalSpanningTree(int root) {
    Graph tree = new Graph(order);
    boolean[] visited = new boolean[order];
    LinkedList<Integer> path = new LinkedList();
    path.add(root);
    while (0 < path.size()) {
      int v = path.peek();
      visited[v] = true;
      boolean pathEnd = true;
      for (int u : getNeighborhood(v)) {
        if (!visited[u]) {
          tree.addEdge(v, u, getEdge(v,u));
          path.addFirst(u);
          pathEnd = false;
          break;
        }
      }
      if (pathEnd) {
        path.poll();
      }
    }
    return tree;
  }

  public Graph getLineGraph() {
    if (1 == order) {return new Graph();}
    boolean[][] visited = new boolean[order][order];
    int[] edgeStart = new int[order*order];
    int[] edgeEnd = new int[order*order];
    int size = 0;
    for (int v = 0; v < order; v++) {
      for (int u : getNeighborhood(v)) {
        if (!visited[v][u]) {
          edgeStart[size] = v;
          edgeEnd[size] = u;
          visited[u][v] = true;
          size++;
        }
      }
    }
    Graph lineGraph = new Graph(size);
    for (int e = 0; e < size; e++) {
      for (int f = 0; f < size; f++) {
        if ((edgeStart[e] == edgeStart[f] ^ edgeEnd[e] == edgeEnd[f]) || (edgeStart[e] == edgeEnd[f] ^ edgeEnd[e] == edgeStart[f])) {
          lineGraph.addEdge(e, f);
        }
      }
    }
   return lineGraph;
  }

  // graph operators --------------------------- 
  // ------------------------------------------- 

  public static boolean isEqual(Graph g1, Graph g2) {
    if (g1.getOrder() != g2.getOrder()) {
      return false;
    }
    for (int i = 0; i < g1.getOrder(); i++) {
      for (int j = 0; j < g1.getOrder(); j++) {
        if (g1.getEdge(i, j) != g2.getEdge(i, j)) {
          return false;
        }
      }
    }
    return true;
  }

  public static Graph graphSum(Graph g1, Graph g2) {
    Graph h = new Graph(Math.max(g1.getOrder(), g2.getOrder()));
    for (int i = 0; i < h.getOrder(); i++) {
      for (int j = 0; j < h.getOrder(); j++) {
        h.addEdge(i, j, Math.max(g1.getEdge(i, j), g2.getEdge(i, j)));
      }
    }
    return h;
  }

  public static Graph graphIntersection(Graph g1, Graph g2) {
    Graph h = new Graph(Math.min(g1.getOrder(), g2.getOrder()));
    for (int i = 0; i < h.getOrder(); i++) {
      for (int j = 0; j < h.getOrder(); j++) {
        h.addEdge(i, j, Math.min(g1.getEdge(i, j), g2.getEdge(i, j)));
      }
    }
    return h;
  }

  public static Graph graphSum(Graph g1, Graph g2, int shift) {
    Graph h = new Graph(Math.max(g1.getOrder(), g2.getOrder() + shift));
    for (int i = 0; i < g1.getOrder(); i++) {
      for (int j = 0; j < g1.getOrder(); j++) {
        h.addEdge(i, j, g1.getEdge(i,j));
      }
    }
    for (int i = 0; i < g2.getOrder(); i++) {
      for (int j = 0; j < g2.getOrder(); j++) {
        h.addEdge(i + shift, j + shift, g2.getEdge(i, j));
      }
    }
    return h;
  }

  // elementary graphs ------------------------- 
  // ------------------------------------------- 
  public static Graph emptyGraph(int n) {
    return new Graph(n);
  }

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

  public static Graph loopGraph(int n) {
    Graph g = new Graph(n);
    for (int i = 0; i < n; i++) {
      g.addEdge(i, i);
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

  public static Graph petersonGraph(int n, int k) {
    Graph g = new Graph(2 * n);
    for (int i = 0; i < n; i++) {
      g.addEdge(i, (i + 1) % n);
      g.addEdge(i, i + n);
      g.addEdge(i + n, ((i + k) % n) + n);
    }
    return g;
  }

  public static Graph petersonGraph() {
    return petersonGraph(5,2);
  }

  public static Graph mycielskiGraph(int n) {
    if (2 >= n) {
      return completeGraph(2);
    }
    Graph g = mycielskiGraph(n - 1);
    int m = g.getOrder();
    Graph h = completeGraph(m, 1);
    Graph s = graphSum(g, h, m);
    for (int v = 0; v < m; v++) {
      s.addNeighborhood(v + m, g.getNeighborhood(v));
    }
    return s;
  }
}
