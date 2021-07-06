import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
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
    numResults = 10;

    Label title = new Label("==========Graph Details==========");
    getChildren().add(title);

    results = new Label[numResults];
    for (int i = 0; i < numResults; i++) {
      Label label = new Label("");
      results[i] = label;
      getChildren().add(label);
    }
    initialize();
  }

  public void update() {
    Graph<Vertex> graph = graphWrapper.getGraph();
    results[0].setText("order: " + new Integer(graph.getOrder()).toString());
    results[1].setText("size: " + new Integer(graph.getSize()).toString());
    results[2].setText("minimum degree: " + new Integer(graph.getMinimumDegree()).toString());
    results[3].setText("maximum degree: " + new Integer(graph.getMaximumDegree()).toString());
    if (graph.isForest()) {
      results[4].setText("graph is acyclic");
    } else {
      results[4].setText("girth: " + new Integer(graph.getGirth()).toString());
    }
    if (graph.isConnected()) {
      results[5].setText("diameter: " + new Integer(graph.getDiameter()).toString());
    } else {
      results[5].setText("graph is not connected");
    }
    results[6].setText("clique number: " + new Integer(graph.getCliqueNumber()).toString());
    int [] selectedVertices = new int[graph.getOrder()];
    int selected = 0;
    for (Vertex v : graph.getVertexSet()) {
      if (v.isSelected()) {
        selectedVertices[selected] = graph.vertex2int(v);
        selected++;
      }
    }
    selectedVertices = Arrays.copyOf(selectedVertices, selected);
    results[7].setText("selected: " + selected);
    if (graph.isClique(selectedVertices)) {
      results[8].setText("the selected vertices are a clique");
    } else {
      results[8].setText("the selected vertices are not a clique");
    }
    if (graph.isCoclique(selectedVertices)) {
      results[9].setText("the selected vertices are a coclique");
    } else {
      results[9].setText("the selected vertices are not a coclique");
    }
  }

  public void initialize() {update();}
}
