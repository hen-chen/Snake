// Food object for snake
import java.awt.Color;
import java.awt.Graphics;

public class Food {
    private int x; // can food have multiple values?
    private int y;  
    
    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void drawFood(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(x * 8, y * 8, 8, 8);
    }
    
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
