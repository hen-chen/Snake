import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.util.List;

@SuppressWarnings("serial")
public class Main extends JPanel {
    public static final int ARENA_WIDTH = 400;
    public static final int ARENA_HEIGHT = 400;
    
    public static final int INTERVAL = 100;
    public static final int INTERVAL1 = 10;
    public static final int TIMER = 1000;
    
    private List<SnakePart> snake;
    private List<Food> food;
    
    private boolean loseGame;
    
    private int[][] board;
    
    private boolean rDir;
    private boolean lDir;
    private boolean uDir;
    private boolean dDir;
    
    private int currHeadX;
    private int currHeadY;
    
    private int numSeconds;

    public Main() {
        rDir = false;
        lDir = false;
        uDir = false;
        dDir = false;
        loseGame = false;
        board = new int[50][50];
        snake = new LinkedList<SnakePart>();
        food = new LinkedList<Food>();
        this.currHeadX = 24;
        this.currHeadY = 24;
        numSeconds = 10;
        setPreferredSize(new Dimension(502, 500));
        Timer timer = new Timer(INTERVAL, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                move();              
            }
        });
        Timer timerFast = new Timer(INTERVAL1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tickFast();              
            }
        });
        Timer timerNormal = new Timer(TIMER, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (numSeconds > 0 && !loseGame) {
                    decSeconds(); 
                }          
            }
        });
        timer.start();
        timerFast.start();
        timerNormal.start();
        setFocusable(true);
        
        snake.add(new SnakePart(currHeadX, currHeadY));
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    lDir = true;
                    rDir = false;
                    uDir = false;
                    dDir = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    lDir = false;
                    rDir = true;
                    uDir = false;
                    dDir = false;
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    lDir = false;
                    rDir = false;
                    uDir = false;
                    dDir = true;
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    lDir = false;
                    rDir = false;
                    uDir = true;
                    dDir = false;
                }
            }
        });
    }
    // Decrease time
    public void decSeconds() {
        numSeconds--;
        if (numSeconds == 0) {
            loseGame = true;
        }
    }
    // check for collision
    public void collisonWithWall() {    
        if (board[currHeadY][currHeadX] == 2) {
            loseGame = true;
        }
    }
    
    public void collsionWithSnake() {
        List<SnakePart> copy = new LinkedList<SnakePart>();
        for (int i = 1; i < snake.size(); i++) {
            copy.add(snake.get(i));
        }
        for (int i = 0; i < copy.size(); i++) {
            if (currHeadX == copy.get(i).getX() && currHeadY == copy.get(i).getY() 
                    && snake.size() > 2) {
                loseGame = true;
            }
        }
    }
    // snake eats the food
    public void eatFood() {
        for (Food f : food) {
            int xFood = f.getX();
            int yFood = f.getY();
            if (currHeadX == xFood && currHeadY == yFood) {
                food.remove(f);
                String direction = getDirection();
                if (direction.equals("Up")) {
                    snake.add(new SnakePart(xFood, yFood + 1));
                    numSeconds += 4;
                } else if (direction.equals("Down")) {
                    snake.add(new SnakePart(xFood, yFood - 1));
                    numSeconds += 4;
                } else if (direction.equals("Left")) {
                    snake.add(new SnakePart(xFood + 1, yFood));
                    numSeconds += 4;
                } else if (direction.equals("Right")) {
                    snake.add(new SnakePart(xFood - 1, yFood));
                    numSeconds += 4;
                }
            }
        }
    }
    // moves all of the snake's segments
    public void move() {
        if (!loseGame) {
            if (rDir) {
                currHeadX++;
            } else if (lDir) {
                currHeadX--;
            } else if (uDir) {
                currHeadY--;
            } else if (dDir) {
                currHeadY++;
            }
            
            List<SnakePart> copy = new LinkedList<SnakePart>();
            for (SnakePart p : snake) {
                copy.add(p);
            }
            for (int i = 1; i < snake.size(); i++) {
                int newX = snake.get(i - 1).getX();
                int newY = snake.get(i - 1).getY();
                SnakePart newSnake = new SnakePart(newX, newY);
                copy.set(i, newSnake);
            }
            // at these point all parts are new but head
            for (int i = 1; i < snake.size(); i++) {
                snake.set(i, copy.get(i));
            }
            SnakePart newPart = new SnakePart(currHeadX, currHeadY);
            snake.set(0, newPart);
            repaint();
        }
    }
    public void tickFast() {
        collisonWithWall();
        collsionWithSnake();
        eatFood();
        repaint();
        spawnFood();
    }

    public void drawGrid(Graphics g) {
        for (int row = 0; row < 50; row++) {
            for (int col = 0; col < 50; col++) {
                if (col == 0 || row == 0 || row == 49 || col == 49) {
                    board[row][col] = 2;
                }
            }
        }
        g.setColor(Color.black);
        for (int i = 0; i < ARENA_WIDTH; i += 8) {
            g.drawLine(0, i, ARENA_WIDTH, i);
            g.drawLine(i, 0, i, ARENA_HEIGHT);
        }
        g.fillRect(0, 0, 8, 400);
        g.fillRect(0, 0, 400, 8);
        g.fillRect(392, 0, 8, 400);
        g.fillRect(0, 392, 400, 8);
    }
    
    public String getDirection() {
        if (lDir) {
            return "Left";
        } else if (rDir) {
            return "Right";
        } else if (uDir) {
            return "Up";
        } else if (dDir) {
            return "Down";
        } else {
            return null;
        }
    }
    public void spawnFood() {
        if (food.isEmpty()) {
            int newX = (int) (Math.random() * 50);
            int newY = (int) (Math.random() * 50);
            if (newX > 0 && newX < 49 && newY > 0 && newY < 49) {
                int sum = 0;
                for (SnakePart p : snake) {
                    if (p.getX() != newX && p.getY() != newY) {
                        sum++;
                    }
                }
                if (sum == snake.size()) {
                    food.add(new Food(newX, newY));
                }
            }
        }
    }
    
    public void drawScore(Graphics g) {
        g.drawString("Score: " + (snake.size() - 1), 420, 200);
    }
    public void drawTime(Graphics g) {
        g.drawString("Time left: " + numSeconds, 420, 170);
    }
    public void drawInstructions(Graphics g) {
        g.drawString("Instructions: Move with arrow keys as a snake. Eating food increases size"
                , 0, 415);
        g.drawString("and gives more time. If you collide with the Walls or snake body", 0, 430);
        g.drawString("or if time runs out, you lose. The goal is to get a high score.", 0, 445);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).drawSnakePart(g);
        }
        for (int i = 0; i < food.size(); i++) {
            food.get(i).drawFood(g);
        }
        g.setColor(Color.black);
        drawScore(g);
        drawTime(g);
        drawInstructions(g);
    }
    
    // These methods are used for testing
    public void testLeftMovement() {
        this.lDir = true;
        move();
    }
    public void testUpMovement() {
        this.uDir = true;
        move();
    }
    public void testRightMovement() {
        this.rDir = true;
        move();
    }
    public void testDownMovement() {
        this.dDir = true;
        move();
    }
    
    public void testSpawnFood(Food f) {
        food.add(f);
    }
    public List<Food> getFoodList() {
        List<Food> copy = new LinkedList<Food>();
        for (Food f : food) {
            copy.add(f);
        }
        return copy;
    }
    public boolean testLoseGame() {
        return loseGame;
    }
    public boolean isFoodEmpty() {
        return food.isEmpty();
    }
    public int getHeadX() {
        return currHeadX;
    }
    public int getHeadY() {
        return currHeadY;
    }
}
