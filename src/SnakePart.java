import java.awt.Color;
import java.awt.Graphics;

public class SnakePart {
    private int x;
    private int y;
    
    public SnakePart(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void drawSnakePart(Graphics g) {
        g.setColor(Color.green);
        g.fillRect(x * 8, y * 8, 8, 8);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
