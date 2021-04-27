import java.util.List;
import java.util.ArrayList;
import javafx.application.Platform;

public class GraphWrapper {
  private Graph<Vertex> graph;
  private List<Listener> listeners;

  public GraphWrapper(Graph<Vertex> g) {
    graph = g;
    listeners = new ArrayList();
  }

  public void setGraph(Graph<Vertex> g) {
    graph = g;
    initialize();
  }

  public void addVertex() {
    graph.addVertex(new Vertex(10, 10, 10));
    for (int i = 0; i < graph.getOrder(); i++) {
      if (graph.getVertexSet().get(i).isSelected()) {
        graph.addEdge(graph.getOrder() - 1, i);
      }
    }
    update();
  }

  public void deleteVertex() {
    for (Vertex v : graph.getVertexSet()) {
      if (v.isSelected()) {
        Platform.runLater(new Runnable() {
          public void run() {
            graph.deleteVertex(graph.vertex2int(v));
            update();
          }
        });
      }
    }
  }

  public Graph getGraph() {
    return graph;
  }

  public void addListener(Listener l) {
    listeners.add(l);
  }

  public void removeListener(Listener l) {
    listeners.remove(l);
  }

  public void initialize() {
    for (Listener l : listeners) {
      l.initialize();
    }
  }

  public void update() {
    for (Listener l : listeners) {
      l.update();
    }
  }
}
