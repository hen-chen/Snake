import java.util.LinkedList;
import java.util.List;
import java.awt.Color;
import java.awt.Graphics;

// the Snake itself

public class Snake {
    private List<Point> snake;
       
    public Snake() {
        this.snake = new LinkedList<Point>();
        snake.add(new Point(5, 5));
    }
    
    public int getLength() {
        return snake.size();
    }
    
    public List<Point> getSnakeLocation() {
        List<Point> copy = new LinkedList<Point>();
        for (Point p : snake) {
            copy.add(p);
        }
        return copy;
    }
    public Point getSnakeHead() {
        return snake.get(0);
    }
    
    public Point getSnakeTail() {
        return snake.get(getLength() - 1);
    }
    public void snakeGrow(Point newTail) {
        snake.add(newTail);
    }
    public void changeSnake(List<Point> newSnake) {
        snake = newSnake;
        
    }
    public void moveSnake(Point dir) {
        int x = dir.getX();
        int y = dir.getY();
        List<Point> newSnake = new LinkedList<Point>();
        for (Point p : snake) {
            Point newPoint = new Point(p.getX() + x, p.getY() + y);
            newSnake.add(newPoint);
        }
        this.snake = newSnake;
    }
    
    public void drawSnake(Graphics g) {
        g.setColor(Color.green);
        for (Point p : snake) {
            g.fillRect(p.getX() * 5, p.getY() * 5, 5, 5);
        }
    }
}
