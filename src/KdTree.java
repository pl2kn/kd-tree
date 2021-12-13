import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

  private static final boolean X_AXIS = true;
  private static final boolean Y_AXIS = false;

  private Node root;
  private int size;

  public boolean isEmpty() {
    return size == 0;
  }

  public int size() {
    return size;
  }

  public void insert(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    root = insert(root, p, X_AXIS);
  }

  private Node insert(Node node, Point2D p, boolean axis) {
    if (node == null) {
      size++;
      return new Node(p, axis);
    }
    if (node.axis == X_AXIS && p.x() > node.point.x() || node.axis == Y_AXIS && p.y() > node.point.y()) {
      node.right = insert(node.right, p, !node.axis);
    } else {
      node.left = insert(node.left, p, !node.axis);
    }
    return node;
  }

  public boolean contains(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    return contains(root, p);
  }

  private boolean contains(Node node, Point2D p) {
    if (node == null) {
      return false;
    }
    if (node.point.equals(p)) {
      return true;
    }
    if ((node.axis == X_AXIS && p.x() > node.point.x()) || (node.axis == Y_AXIS && p.y() > node.point.y())) {
      return contains(node.right, p);
    }
    return contains(node.left, p);
  }

  public void draw() {
    draw(root, new RectHV(0, 0, 1, 1));
  }

  private void draw(final Node node, RectHV field) {
    if (node == null) {
      return;
    }
    Point2D lineStart;
    Point2D lineEnd;
    if (node.axis == X_AXIS) {
      final double xPoint = node.point.x();
      StdDraw.setPenColor(StdDraw.RED);
      lineStart = new Point2D(xPoint, field.ymax());
      lineEnd = new Point2D(xPoint, field.ymin());
    } else {
      final double yPoint = node.point.y();
      StdDraw.setPenColor(StdDraw.BLUE);
      lineStart = new Point2D(field.xmax(), yPoint);
      lineEnd = new Point2D(field.xmin(), yPoint);
    }
    lineStart.drawTo(lineEnd);
    StdDraw.setPenColor(StdDraw.BLACK);
    node.point.draw();
    draw(node.left, getLeftRect(node, field));
    draw(node.right, getRightRect(node, field));
  }

  public Iterable<Point2D> range(RectHV rect) {
    if (rect == null) {
      throw new IllegalArgumentException();
    }
    Queue<Point2D> queue = new Queue<>();
    range(root, rect, new RectHV(0, 0, 1, 1), queue);
    return queue;
  }

  private void range(Node node, RectHV rect, RectHV pointRect, Queue<Point2D> queue) {
    if (node == null) {
      return;
    }
    if (rect.intersects(pointRect)) {
      Point2D point = node.point;
      if (rect.contains(point)) {
        queue.enqueue(point);
      }
      if (node.left != null) {
        range(node.left, rect, getLeftRect(node.left, pointRect), queue);
      }
      if (node.right != null) {
        range(node.right, rect, getRightRect(node.right, pointRect), queue);
      }
    }
  }

  private RectHV getLeftRect(Node node, RectHV rect) {
    if (node.axis == X_AXIS) {
      return new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
    }
    return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
  }

  private RectHV getRightRect(Node node, RectHV rect) {
    if (node.axis == X_AXIS) {
      return new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
    }
    return new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
  }

  public Point2D nearest(Point2D p) {
    if (p == null) {
      throw new IllegalArgumentException();
    }
    return null;
  }


  private class Node {

    private final Point2D point;
    private final boolean axis;
    private Node left;
    private Node right;

    public Node(Point2D point, boolean axis) {
      this.axis = axis;
      this.point = point;
    }
  }
}
