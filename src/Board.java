// Tile class for Snake

public class Board {
    private int[][] board;
    
    public Board() {
        this.board = new int[MainGame.ARENA_WIDTH][MainGame.ARENA_HEIGHT];
    }
    
    public int[][] getCurrentBoard() {
        int[][] copy = board; //encapsulation
        return copy;
    }
    
    public void setBoardFood(int value) {
        if (value == 1) {
            for (int row = 0; row < MainGame.ARENA_WIDTH; row++) {
                for (int col = 0; col < MainGame.ARENA_HEIGHT; col++) {
                    if (col == 0) {
                        board[row][col] = value;
                    }
                }
            }
        }
        System.out.println("Not Done");
    }
    
    public boolean isFoodOnBoard() {
        for (int row = 0; row < MainGame.ARENA_WIDTH; row++) {
            for (int col = 0; col < MainGame.ARENA_HEIGHT; col++) {
                if (board[row][col] == 1) {  // 0 is nothing, and 1 is food. snake 2
                    return true;
                }
            }
        }
        return false;
    }
    public void setBoard(Point p, int i) {
        int x = p.getX();
        int y = p.getY();
        this.board[y][x] = i;
    }
    public void setFood(Point p, int food) {
        int x = p.getX();
        int y = p.getY();
        for (int row = p.getY() - 5; row < p.getY() + 5; row++) {
            for (int col = p.getX() - 5; col < p.getX() + 5; col++) {
                this.board[row][col] = food;
            }
        }
//        for (int i = 0; i < 5; i++) {
//            this.board[y][x + i] = food;
//            this.board[y][x - i] = food; // null PE
//            this.board[y + i][x] = food;
//            this.board[y - i][x] = food;
//        }
        
    }
}
