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

  public void selectAll() {
    for (Vertex v : graph.getVertexSet()) {
      v.setSelect(true);
    }
    update();
  }

  public void deselectAll() {
    for (Vertex v : graph.getVertexSet()) {
      v.setSelect(false);
    }
    update();
  }

  public void invertSelection() {
    for (Vertex v : graph.getVertexSet()) {
      v.toggleSelect();
    }
    update();
  }

  public void addVertex() {
    addVertex(new Vertex());
    update();
  }

  public void addVertex(Vertex v) {
    Platform.runLater(new Runnable() {
      public void run() {
        graph.addVertex(v);
        for (int i = 0; i < graph.getOrder(); i++) {
          if (graph.getVertexSet().get(i).isSelected()) {
            graph.addEdge(graph.getOrder() - 1, i);
          }
        }
        update();
      }
    });
  }

  public void deleteVertex(Vertex v) {
    Platform.runLater(new Runnable() {
      public void run() {
        graph.deleteVertex(graph.vertex2int(v));
        update();
      }
    });
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

  public void loopVertex() {
    for (Vertex v : graph.getVertexSet()) {
      if (v.isSelected()) {
        int i = graph.vertex2int(v);
        graph.addEdge(i, i);
        update();
      }
    }
  }

  public void loopVertex(Vertex v) {
    int i = graph.vertex2int(v);
    graph.addEdge(i, i);
    update();
  }

  public void completeSubgraph() {
    for (Vertex v : graph.getVertexSet()) {
      if (v.isSelected()) {
        int i = graph.vertex2int(v);
        for (Vertex u : graph.getVertexSet()) {
          if (u.isSelected() && v != u) {
            int j = graph.vertex2int(u);
            graph.addEdge(i, j);
            update();
          }
        }
      }
    }
  }

  public void emptySubgraph() {
    for (Vertex v : graph.getVertexSet()) {
      if (v.isSelected()) {
        int i = graph.vertex2int(v);
        for (Vertex u : graph.getVertexSet()) {
          if (u.isSelected() && v != u) {
            int j = graph.vertex2int(u);
            graph.deleteEdge(i, j);
          }
        }
      }
    }
    update();
  }
  
  public void inducedSubgraph() {
    for (Vertex v : graph.getVertexSet()) {
      if (!v.isSelected()) {
        deleteVertex(v);
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
