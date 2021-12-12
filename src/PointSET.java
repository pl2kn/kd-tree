import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import java.util.ArrayList;
import java.util.List;

public class PointSET {

  private final SET<Point2D> points;

  public PointSET() {
    points = new SET<>();
  }

  public boolean isEmpty() {
    return points.isEmpty();
  }

  public int size() {
    return points.size();
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    points.add(p);
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    return points.contains(p);
  }

  public void draw() {
    for (Point2D p : points) {
      p.draw();
    }
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }
    List<Point2D> list = new ArrayList<>();
    for (Point2D point : points) {
      if (rect.contains(point)) {
        list.add(point);
      }
    }
    return list;
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    if (isEmpty()) {
      return null;
    }
    Point2D nearest = points.min();
    double nearestDistance = Double.MAX_VALUE;
    for (Point2D point : points) {
      if (point.distanceTo(p) < nearestDistance) {
        nearest = point;
        nearestDistance = point.distanceTo(p);
      }
    }
    return nearest;
  }
}
