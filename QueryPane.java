import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;

public class QueryPane extends VBox implements Listener {
  private GraphWrapper graphWrapper;
  private Label[] results;

  public QueryPane(GraphWrapper gw) {
    graphWrapper = gw;
    setSpacing(10);
    setPadding(new Insets(10));

    Label title = new Label("====Graph Details====");
    getChildren().add(title);

    results = new Label[8];
    for (int i = 0; i < 8; i++) {
      Label label = new Label("");
      results[i] = label;
      getChildren().add(label);
    }
    initialize();
  }

  public void update() {
    Graph<Vertex> graph = graphWrapper.getGraph();
    int selected = 0;
    for (Vertex v : graph.getVertexSet()) {
      if (v.isSelected()) {selected++;}
    }
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
    results[7].setText("selected: " + selected);
  }

  public void initialize() {update();}
}
