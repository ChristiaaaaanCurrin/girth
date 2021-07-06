public class Vertex {
  private boolean selected;
  private double radius;
  private double xCoord;
  private double yCoord;
  private String label;
  
  public Vertex() {
    this(0, 0, 1, "");
  }

  public Vertex(double x, double y) {
    this(x, y, 1, "");
  }

  public Vertex(double x, double y, double r) {
    this(x, y, r, "");
  }

  public Vertex(double x, double y, double r, String string) {
    radius = r; xCoord = x; yCoord = y; selected = false; label = string;
  }
  
  public double getX() {return xCoord;}
  public double getY() {return yCoord;}
  public double getRadius() {return radius;}
  public boolean isSelected() {return selected;}
  public String getLabel() {return label;}
  
  public void setCoords(double x, double y) {
    xCoord = x;
    yCoord = y;
  }
  
  public void setRadius(double r) {
    radius = r;
  }

  public void setSelect(boolean value) {
    selected = value;
  }

  public void toggleSelect() {
    selected = !selected;
  }

  public void setLabel(String newLabel) {
    label = newLabel;
  }
}
