import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Girth extends Application {
  // instance varibles
  private GraphWrapper graphWrapper;
  
  public void start(Stage primaryStage) {
    graphWrapper = new GraphWrapper(Graph.completeGraph(3));

    // create drawing pane, query box, build pane
    DrawingPane drawingPane = new DrawingPane(graphWrapper);
    QueryPane queryBox = new QueryPane(graphWrapper);
    BuildPane buildPane = new BuildPane(graphWrapper);

    // add listeners to graph wrapper
    graphWrapper.addListener(drawingPane);
    graphWrapper.addListener(queryBox);

    // create rootPane and add children
    BorderPane rootPane = new BorderPane();
    rootPane.setCenter(drawingPane);
    rootPane.setRight(queryBox);
    rootPane.setBottom(buildPane);

    // Create a scene and place rootPane in the stage
    Scene scene = new Scene(rootPane, 830, 540);
    primaryStage.setTitle("girth");
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}

