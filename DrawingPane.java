import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;

public class DrawingPane extends Canvas implements Listener {
  private GraphWrapper graphWrapper;
  private double LINE_WIDTH = 5, VERTEX_RADIUS = 10;
  private Color VERTEX_COLOR = Color.BLUE, SELECTED_COLOR = Color.RED, EDGE_COLOR = Color.BLACK;
  private String state;
  private Vertex dragVertex;

  public DrawingPane(GraphWrapper gw) {
    super(400, 400);
    graphWrapper = gw;
    setFocusTraversable(true);
    setOnMouseClicked(new MouseClickHandler());
    setOnMousePressed(new MousePressHandler());
    setOnMouseDragged(new MouseDragHandler());
    setOnMouseReleased(new MouseReleaseHandler());
    initialize();
  } 

  public void update() {
    GraphicsContext gc = getGraphicsContext2D();
    gc.clearRect(0, 0, getWidth(), getHeight());
    gc.setFill(VERTEX_COLOR); gc.setStroke(EDGE_COLOR);
    gc.setLineWidth(LINE_WIDTH);
    for (int i = 0; i < graphWrapper.getGraph().getOrder(); i++) {
      Vertex v = getVertex(i);
      for (int j : graphWrapper.getGraph().getNeighborhood(i)) {
        if (i < j) {
          Vertex u = getVertex(j);
          gc.strokeLine(v.getX(), v.getY(), u.getX(), u.getY());
        } else if (i == j) {
          gc.strokeOval(v.getX(), v.getY() - 2 * v.getRadius(), 4 * v.getRadius(), 4 * v.getRadius());
        }
      }
      if (v.isSelected()) {gc.setFill(SELECTED_COLOR);}
      gc.fillOval(v.getX() - v.getRadius(), v.getY() - v.getRadius(), 2 * v.getRadius(), 2 * v.getRadius());
      gc.setFill(EDGE_COLOR);
      gc.setFont(new Font(10));
      gc.fillText(v.getLabel(), v.getX(), v.getY());
      gc.setFill(VERTEX_COLOR); gc.setStroke(EDGE_COLOR);
    }
  }

  public Vertex getVertex(int i){
    Vertex v;
    List<Vertex> vs = graphWrapper.getGraph().getVertexSet();
    if((v = vs.get(i)) == null){
      v = new Vertex(10, 10, VERTEX_RADIUS);
      vs.set(i, v);
    }
    return v;
	}

  public void initialize() {
    Graph<Vertex> g = graphWrapper.getGraph();
    List<Vertex> vs = g.getVertexSet();
    int order = g.getOrder();
    double x = getWidth() / 2, y = getHeight() / 2,
           dtheta = 2 * Math.PI / order, radius = 3 * Math.min(getWidth(), getHeight()) / 8, theta = 0;
    for (int v = 0; v < g.getOrder(); v++) {
       vs.add(new Vertex(x + radius * Math.cos(theta), y + radius * Math.sin(theta), VERTEX_RADIUS));
       theta += dtheta;
    }
    update();
  }

  private class MouseClickHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent e) {
      List<Vertex> vs = graphWrapper.getGraph().getVertexSet();
      double xe = e.getX(), ye = e.getY();
      for (Vertex v : vs) {
        double xv = v.getX(), yv = v.getY();
        if (Math.sqrt(Math.pow(Math.abs(xe - xv), 2) + Math.pow(Math.abs(ye - yv), 2)) < v.getRadius()) {
          v.toggleSelect();
          graphWrapper.update();
          return;
        }
      }
      for (Vertex v : vs) {
        v.setSelect(false);
      }
      graphWrapper.update();
    }
  }

  private boolean dragging;
  private class MouseDragHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent e) {
      dragging = true;
      if (null != dragVertex) {
        dragVertex.setCoords(e.getX(), e.getY());
      } 
      update();
    }
  }

  private class MousePressHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent e) {
      List<Vertex> vs = graphWrapper.getGraph().getVertexSet();
      double xe = e.getX(), ye = e.getY();
      for (Vertex v : vs) {
        double xv = v.getX(), yv = v.getY();
        if (Math.sqrt(Math.pow(Math.abs(xe - xv), 2) + Math.pow(Math.abs(ye - yv), 2)) < v.getRadius()) {
          dragVertex = v; 
          v.toggleSelect();
          dragging = false;
          return;
        }
      }
      for (Vertex v : vs) {
        v.setSelect(false);
      }
      graphWrapper.update();
    }
  }

  private class MouseReleaseHandler implements EventHandler<MouseEvent> {
    public void handle(MouseEvent e) {
      if (null != dragVertex && !dragging) {
        dragVertex.toggleSelect();
      }
      dragging = false;
      dragVertex = null;
    }
  }   

  @Override
  public boolean isResizable() {
    return true;
  }

  @Override
  public double maxHeight(double width) {
    return Double.POSITIVE_INFINITY;
  }

  @Override
  public double maxWidth(double height) {
    return Double.POSITIVE_INFINITY;
  }

  @Override
  public double minWidth(double height) {
    return 1D;
  }

  @Override
  public double minHeight(double width) {
    return 1D;
  }

  @Override
  public void resize(double width, double height) {
    this.setWidth(width);
    this.setHeight(height);
  }
}
