import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

public class GraphPane extends AnchorPane {
  private Graph graph;
  private Circle[] vertices;

  public GraphPane() {
    Graph graph = new Graph(3);
    Circle vertex = new Circle(100, 100, 10);
    getChildren().add(vertex);
  } 
}
