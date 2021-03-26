import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;

public class QueryBox extends HBox {
  private GraphWrapper graphWrapper;
  private String state;
  private Button button;
  private ComboBox cbox;
  private Label result;

  public QueryBox(GraphWrapper gw) {
    graphWrapper = gw;
    setSpacing(10);
    setPadding(new Insets(10));
    state = "order";
    cbox = new ComboBox(FXCollections.observableArrayList("order", "size", "delta", "Delta", "girth", "diameter"));
    button = new Button("=");
    result = new Label("");

    cbox.getSelectionModel().selectFirst();

    getChildren().add(cbox);
    getChildren().add(button);
    getChildren().add(result);

    cbox.setOnAction(new ComboBoxHandler());
    button.setOnAction(new ButtonHandler());
  }
  
  private class ComboBoxHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      state = cbox.getValue().toString();
      new ButtonHandler().handle(event);
    }
  }

  private class ButtonHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      Graph graph = graphWrapper.getGraph();
      switch (state) {
        case "order":
          result.setText(new Integer(graph.getOrder()).toString());
          break;
        case "size":
          result.setText(new Integer(graph.getSize()).toString());
          break;
        case "delta":
          result.setText(new Integer(graph.getMinimumDegree()).toString());
          break;
        case "Delta":
          result.setText(new Integer(graph.getMaximumDegree()).toString());
          break;
        case "girth":
          result.setText(new Integer(graph.getGirth()).toString());
          break;
        case "diameter":
          result.setText(new Integer(graph.getDiameter()).toString());
          break;
      }
    }
  }
}
