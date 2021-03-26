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
    graphWrapper = new GraphWrapper(new Graph(1));

    // create GraphPane
    GraphPane graphPane = new GraphPane();

    // create queryPane 
    VBox queryPane = new VBox();
    queryPane.getChildren().add(new Label("graph queries"));
    for (int i = 0; i < 9; i++) {
      QueryBox queryBox = new QueryBox(graphWrapper);
      queryPane.getChildren().add(queryBox);
    }

    BuildPane buildPane = new BuildPane(graphWrapper);

    // create rootPane and add children
    BorderPane rootPane = new BorderPane();
    rootPane.setCenter(graphPane);
    rootPane.setRight(queryPane);
    rootPane.setBottom(buildPane);

    // Create a scene and place rootPane in the stage
    Scene scene = new Scene(rootPane, 630, 540);
    primaryStage.setTitle("girth");
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  public static void main(String[] args) {
    Application.launch(args);
  }
}

