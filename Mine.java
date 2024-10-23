import java.util.Random;
import java.util.Scanner;

public class Mine {
    public static final char UNOPENED = '-';
    public static final char OPENED = '0';
    public static final char MINE = '*';
    public static final char FLAGGED = 'F';
    public static final int SIZE = 9;
    public static final int MINES = 10;
    
    public char[][] board;
    public  static boolean[][] revealed;
    public boolean[][] flagged;
    
    public Mine() {
        board = new char[SIZE][SIZE];
        revealed = new boolean[SIZE][SIZE];
        flagged = new boolean[SIZE][SIZE];
        initializeBoard();
    }
    
    public void initializeBoard() 
    {
        Random rand = new Random();
        int minesPlaced = 0;
        
        while (minesPlaced < MINES) {
            int x = rand.nextInt(SIZE);
            int y = rand.nextInt(SIZE);
            if (board[x][y] != MINE) 
            {
                board[x][y] = MINE;
                minesPlaced++;
            }
        }
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if (board[x][y] != MINE) {
                    board[x][y] = (char) ('0' + countMines(x, y));
                }
            }
        }
    }
    public int countMines(int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int nx = x + i;
                int ny = y + j;
                if (nx >= 0 && nx < SIZE && ny >= 0 && ny < SIZE && board[nx][ny] == MINE) {
                    count++;
                }
            }
        }
        return count;
    }
    
    public void printBoard() {
        System.out.print("  ");
        for (int i = 0; i < SIZE; i++) 
        {
            System.out.print(i + " ");
        }
        System.out.println();
        
        for (int i = 0; i < SIZE; i++) 
        {
            System.out.print(i + " ");
            for (int j = 0; j < SIZE; j++) 
            {
                if (flagged[i][j]) 
                {
                    System.out.print(FLAGGED + " ");
                } 
                else if (revealed[i][j]) 
                {
                    System.out.print(board[i][j] + " ");
                } 
                else 
                {
                    System.out.print(UNOPENED + " ");
                }
            }
            System.out.println();
        }
    }
    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.print("Enter command (o x y to open, f x y to flag): ");
            String command = scanner.nextLine();
            String[] parts = command.split(" ");
            if (parts.length != 3) {
                System.out.println("Invalid command!");
                continue;
            }
            String action = parts[0];
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            
            if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
                System.out.println("Coordinates out of bounds!");
                continue;
            }
            
            if (action.equals("o")) 
            {
                if (flagged[x][y]) 
                {
                    System.out.println("Cannot open flagged cell!");
                    continue;
                }
                if (board[x][y] == MINE) 
                {
                    System.out.println("Game Over! You hit a mine!");
                    revealed[x][y] = true;
                    printBoard();
                    break;
                }
                
            } 
            else if (action.equals("f")) 
            {
                flagged[x][y] = !flagged[x][y]; // Toggle flag
            } 
            else if(!action.equals("o")||!action.equals("f")||parts.length != 3)
            {
                System.out.println("Invalid command!");
            }
            
                revealCell(x, y);
                if (checkWin() == true) 
                {
                    printBoard();
                    System.out.println("Congratulations! You've cleared the minefield!");
                    break;
                }
            
        }
        scanner.close();
    }
    private static boolean checkWin() {
        int revealedCount = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (revealed[i][j]) {
                    revealedCount++;
                }
            }
        }
        if(revealedCount == SIZE * SIZE - MINES)  return true;
        
        return false;
    }
    
    public void revealCell(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || revealed[x][y] || flagged[x][y]) {
            return;
        }
        revealed[x][y] = true;
        if (board[x][y] == '0') 
        {
            for (int i = -1; i <= 1; i++) 
            {
                for (int j = -1; j <= 1; j++) 
                {
                    if (i == 0 && j == 0) continue;
                    revealCell(x + i, y + j);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        Mine game = new Mine();
        game.play();
    }
}
