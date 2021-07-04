import java.awt.*;
import javax.swing.*;

// Run this for main game
public class Game implements Runnable {
    public void run() {
        JFrame frame = new JFrame("Snake");
        
        Main game = new Main();
        frame.add(game, BorderLayout.CENTER);
        
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}
