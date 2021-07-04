import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

// Test cases for Snake

public class MainTest {
    private Main main;
    
    @BeforeEach
    public void setUp() {
        main = new Main();
    }
    
    @Test
    public void testEat() {
        Food f = new Food(23, 24);
        main.testSpawnFood(f);
        main.testLeftMovement();
        main.eatFood();
        List<Food> list = main.getFoodList();
        assertTrue(!list.contains(f));
    }
    @Test
    public void didNotEat() {
        Food f = new Food(24, 25);
        main.testSpawnFood(f);
        main.testLeftMovement();
        List<Food> list = main.getFoodList();
        assertTrue(list.contains(f));
    }
    @Test
    public void testEatSpawn() {
        Food f = new Food(23, 24);
        main.testSpawnFood(f);
        main.testLeftMovement();
        main.eatFood();
        List<Food> list = main.getFoodList();
        assertTrue(!list.contains(f));
        main.spawnFood();
        assertFalse(main.isFoodEmpty());
    }
    @Test
    public void crashX() {
        for (int i = 0; i < 24; i++) {
            main.testLeftMovement();
        }
        main.collisonWithWall();
        boolean lost = main.testLoseGame();
        assertTrue(!lost);
    }
    @Test
    public void crashY() {
        for (int i = 0; i < 24; i++) {
            main.testUpMovement();
        }
        main.collisonWithWall();
        boolean lost = main.testLoseGame();
        assertTrue(!lost);
    }
    @Test
    public void moveLeft() {
        int originX = main.getHeadX();
        int originY = main.getHeadY();
        main.testLeftMovement();
        int newX = main.getHeadX();
        int newY = main.getHeadY();
        assertEquals(newY, originY);
        assertEquals(originX - 1, newX);
    }
    @Test
    public void moveUp() {
        int originX = main.getHeadX();
        int originY = main.getHeadY();
        main.testUpMovement();
        int newX = main.getHeadX();
        int newY = main.getHeadY();
        assertEquals(newX, originX);
        assertEquals(originY - 1, newY);
    }
    @Test
    public void moveDown() {
        int originX = main.getHeadX();
        int originY = main.getHeadY();
        main.testDownMovement();
        int newX = main.getHeadX();
        int newY = main.getHeadY();
        assertEquals(newX, originX);
        assertEquals(originY + 1, newY);
    }
    @Test
    public void moveRight() {
        int originX = main.getHeadX();
        int originY = main.getHeadY();
        main.testRightMovement();
        int newX = main.getHeadX();
        int newY = main.getHeadY();
        assertEquals(newY, originY);
        assertEquals(originX + 1, newX);
    }
}


    