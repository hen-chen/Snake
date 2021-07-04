import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

// main snake game

@SuppressWarnings("serial")
public class MainGame extends JPanel {
    private Board board;
    private Snake snake;
    
    private boolean loseGame;
    
    private Point direction;
    
    private Point currNewTail;
    
    public static final int INTERVAL = 50;
    
    public static final int ARENA_WIDTH = 400;
    public static final int ARENA_HEIGHT = 400;
    
    
    public MainGame() {
        loseGame = false;
        setPreferredSize(new Dimension(ARENA_WIDTH, ARENA_HEIGHT));
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tick();
            }
        });
        timer.start();
        setFocusable(true);
        board = new Board();
        snake = new Snake();
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    direction = Direction.LEFT;
                    System.out.println(5);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    direction = Direction.RIGHT;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    direction = Direction.DOWN;
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    direction = Direction.UP;
                }
            }
        });
        while (!loseGame) {
            checkCollisonWithWall();
            checkCollisonWithSnake();
            foodSpawn();
            if (getSnakeTailIfNearFood()) {
                eatSnake(); // this prob won't work
            }
            repaint();
            if (loseGame) {
                System.out.println("LOST GAME");
            }
        }
    }
    
    public void tick() {
        if (direction != null) {
            snake.moveSnake(direction);
            repaint();
        }
    }
    
    public void checkCollisonWithWall() {
        for (Point p : snake.getSnakeLocation()) {
            if (p.getX() < 0 || p.getX() > ARENA_WIDTH || p.getY() < 0 || p.getY() > ARENA_WIDTH) {
                loseGame = true;
                System.out.println(5);
            }
        }
    }
    public void checkCollisonWithSnake() {
        if (direction != null) {
            Point head = snake.getSnakeHead();
            Point newPoint = new Point(head.getX() + direction.getX(),
                    head.getY() + direction.getY());
            for (Point p : snake.getSnakeLocation()) {
                if (newPoint.equals(p)) {
                    loseGame = true;
                    System.out.println(5);
                }
            } 
        }
    }
    public void foodSpawn() { //make this random
        if (!board.isFoodOnBoard()) {
            int[][] currBoard = board.getCurrentBoard();
            for (int row = 0; row < ARENA_WIDTH; row++) {
                for (int col = 0; col < ARENA_WIDTH; col++) {
                    if (currBoard[row][col] != 0) {
                        Point p = new Point(row, col);
                        board.setBoard(p, 1);
                    }
                }
            }
        }
    }
    public boolean getSnakeTailIfNearFood() {
        boolean isNear = false;
        Point head = snake.getSnakeHead();
        int[][] currBoard = board.getCurrentBoard();
        int headX = head.getX();
        int headY = head.getY();
        for (int row = 0; row < ARENA_WIDTH; row++) {
            for (int col = 0; col < ARENA_WIDTH; col++) {
                int currPoint = currBoard[row][col];
                Point current = new Point(col, row);
                if (currPoint == 1 && (headY + 1 == current.getY() || headY - 1 == current.getY() 
                        || headX - 1 == current.getX() || headX + 1 == current.getX())) {
                    isNear = true;
                    currNewTail = current;
                }
            }
        }
        return isNear; // REMEBER TO ACTUALLY USE THIS 
    }
    public void eatSnake() {
        Point head = snake.getSnakeHead();
        int[][] currBoard = board.getCurrentBoard();
        for (int row = 0; row < ARENA_WIDTH; row++) {
            for (int col = 0; col < ARENA_WIDTH; col++) {
                if (currBoard[row][col] == 1 && row == head.getY() && col == head.getX()) {
                    snake.snakeGrow(currNewTail);
                }
            }
        }
    }
    
    public void drawFood(Graphics g) {
        int[][] currBoard = board.getCurrentBoard();
        for (int row = 0; row < ARENA_WIDTH; row++) {
            for (int col = 0; col < ARENA_WIDTH; col++) {
                if (currBoard[row][col] == 1) {
                    g.setColor(Color.yellow);
                    g.fillRect(5, 5, 5, 5);
                }
            }
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawFood(g);
        snake.drawSnake(g);
    }
    
}
