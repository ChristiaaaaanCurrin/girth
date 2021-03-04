import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Graph extends Application {
  public void start(Stage stage) {
    StackPane rootPane = new StackPane();
    rootPane.getChildren().add(gui);       
    Scene scene = new Scene(rootPane, 630, 540);
    stage.setScene(scene);
    stage.setTitle("girth");
    stage.show();
  }
  
  public static void main(String[] args) {
    Appliction.launch(args);
  } 
}
