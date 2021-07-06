import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import java.util.Arrays;

public class QueryPane extends VBox implements Listener {
  private GraphWrapper graphWrapper;
  private Label[] results;
  private int numResults;

  public QueryPane(GraphWrapper gw) {
    graphWrapper = gw;
    setSpacing(10);
    setPadding(new Insets(10));
    initialize();
  }

  public void update() {
    Graph<Vertex> graph = graphWrapper.getGraph();
    getChildren().clear();
    getChildren().add(new Label("==========Graph Details=========="));
    getChildren().add(new Label("order: " + new Integer(graph.getOrder()).toString()));
    getChildren().add(new Label("size: " + new Integer(graph.getSize()).toString()));
    getChildren().add(new Label("minimum degree: " + new Integer(graph.getMinimumDegree()).toString()));
    getChildren().add(new Label("maximum degree: " + new Integer(graph.getMaximumDegree()).toString()));
    if (graph.isForest()) {
      getChildren().add(new Label("graph is acyclic"));
    } else {
      getChildren().add(new Label("girth: " + new Integer(graph.getGirth()).toString()));
    }
    if (graph.isConnected()) {
      getChildren().add(new Label("diameter: " + new Integer(graph.getDiameter()).toString()));
    } else {
      getChildren().add(new Label("graph is not connected"));
    }
    getChildren().add((new Label("clique number: " + new Integer(graph.getCliqueNumber()).toString())));
    int [] selectedVertices = new int[graph.getOrder()];
    int selected = 0;
    for (Vertex v : graph.getVertexSet()) {
      if (v.isSelected()) {
        selectedVertices[selected] = graph.vertex2int(v);
        selected++;
      }
    }
    selectedVertices = Arrays.copyOf(selectedVertices, selected);
    getChildren().add(new Label("selected: " + selected));
    if (graph.isClique(selectedVertices)) {
      getChildren().add(new Label("the selected vertices are a clique"));
    } else {
      getChildren().add(new Label("the selected vertices are not a clique"));
    }
    if (graph.isCoclique(selectedVertices)) {
      getChildren().add(new Label("the selected vertices are a coclique"));
    } else {
      getChildren().add(new Label("the selected vertices are not a coclique"));
    }
    if (graph.isConnected(selectedVertices)) {
      getChildren().add(new Label("the selected vertices are connected"));
    } else {
      getChildren().add(new Label("the selected vertices are not connected"));
    }
  }

  public void initialize() {update();}
}
