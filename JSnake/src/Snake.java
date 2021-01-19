import java.awt.*;

public class Snake implements MapConfig {

    private Point head;
    private Point tail;
    private Cell direction;

    public Snake(Point head, Point tail, Cell direction) {
        this.head = head;
        this.tail = tail;
        this.direction = direction;
    }

    public Point getHead() {
        return head;
    }

    public void setHead(Point head) {
        this.head = head;
    }

    public Point getTail() {
        return tail;
    }

    public void setTail(Point tail) {
        this.tail = tail;
    }

    public Cell getDirection() {
        return direction;
    }

    public void setDirection(Cell direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "head=" + head +
                ", tail=" + tail +
                ", direction=" + direction +
                '}';
    }
}

