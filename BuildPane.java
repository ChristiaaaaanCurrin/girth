import java.util.Scanner;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import java.util.List;
import java.util.LinkedList;

public class BuildPane extends VBox {
  private GraphWrapper graphWrapper;
  private String state;
  private ComboBox cbox;
  private Label warning;
  private TextField input;

  public BuildPane(GraphWrapper gw) {
    graphWrapper = gw;
    
    HBox editGraphHBox = new HBox();
    Button deleteButton = new Button("delete");
    Button addButton = new Button("add");
    Button buildButton;

    state = "complete";
    HBox newGraphHBox = new HBox();
    cbox = new ComboBox(FXCollections.observableArrayList("complete", "cycle", "path", "peterson", "empty", "mycielski",
                                                          "line", "complement"));
    input = new TextField();
    buildButton = new Button("build graph");
    warning = new Label("");

    setPadding(new Insets(20));
    setSpacing(10);
    editGraphHBox.setSpacing(10);
    newGraphHBox.setSpacing(10);
    cbox.getSelectionModel().selectFirst();
    warning.setTextFill(Color.RED);

    editGraphHBox.getChildren().add(deleteButton);
    editGraphHBox.getChildren().add(addButton);
    newGraphHBox.getChildren().add(cbox);
    newGraphHBox.getChildren().add(input);
    newGraphHBox.getChildren().add(buildButton);
    newGraphHBox.getChildren().add(warning);
    getChildren().add(editGraphHBox);
    getChildren().add(new Label("Graph Builder"));
    getChildren().add(newGraphHBox);

    cbox.setOnAction(new ComboBoxHandler());
    buildButton.setOnAction(new BuildButtonHandler());
    addButton.setOnAction(new EventHandler<ActionEvent> () {
      public void handle(ActionEvent e) {
        graphWrapper.addVertex();
      }
    });

    deleteButton.setOnAction(new EventHandler<ActionEvent> () {
      public void handle(ActionEvent e) {
        graphWrapper.deleteVertex();
      }
    });
  }

  private class ComboBoxHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      state = cbox.getValue().toString();
    }
  }

  private class BuildButtonHandler implements EventHandler<ActionEvent> {
    public void handle(ActionEvent event) {
      Scanner scanner = new Scanner(input.getText());
      warning.setText("");
      switch (state) {
        case "complete":
          if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            if (scanner.hasNextInt()) {
              int m = scanner.nextInt();
              graphWrapper.setGraph(Graph.completeGraph(n, m));
            }
            else {
              graphWrapper.setGraph(Graph.completeGraph(n));
            }
          }
          else {
            graphWrapper.setGraph(Graph.completeGraph(1));
          }
          break;
        case "cycle":
          if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            graphWrapper.setGraph(Graph.cycleGraph(n));
          }
          else {
            graphWrapper.setGraph(Graph.completeGraph(3));
          }
          break;
        case "path":
          if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            graphWrapper.setGraph(Graph.pathGraph(n));
          }
          else {
            graphWrapper.setGraph(Graph.completeGraph(1));
          }
          break;
        case "peterson":
          if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            if (scanner.hasNextInt()) {
              int m = scanner.nextInt();
              graphWrapper.setGraph(Graph.petersonGraph(n, m));
            }
            else {
              warning.setText("bad parameters - no graph built");
            }
          }
          else {
            graphWrapper.setGraph(Graph.petersonGraph());
          }
          break;
        case "empty":
          if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            graphWrapper.setGraph(Graph.emptyGraph(n));
          }
          else {
            graphWrapper.setGraph(Graph.emptyGraph(1));
          }
          break;
        case "mycielski":
          if (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            graphWrapper.setGraph(Graph.mycielskiGraph(n));
          }
          else {
            warning.setText("bad parameters - no graph built");
          }
          break;
        case "line":
          graphWrapper.setGraph(graphWrapper.getGraph().getLineGraph());
          break;
        case "complement":
          graphWrapper.setGraph(graphWrapper.getGraph().getComplement());
          break;
      }
    }
  }
}
