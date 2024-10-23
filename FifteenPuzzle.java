import java.util.Random;
import java.util.*;

public class FifteenPuzzle {
     int SIZE = 4;
     int[][] board = new int[SIZE][SIZE];
     int emptyRow, emptyCol;

    public FifteenPuzzle() {
        initializeBoard();
        shuffleBoard();
    }

    public void initializeBoard() {
        int value = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = value++;
            }
        }
        board[SIZE - 1][SIZE - 1] = 0;
        emptyRow = SIZE - 1;
        emptyCol = SIZE - 1;
    }

    private void shuffleBoard() {
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            int direction = rand.nextInt(4);
            switch (direction) {
                case 0 : move("UP"); break;
                case 1 : move("DOWN"); break;
                case 2 : move("LEFT"); break;
                case 3 : move("RIGHT");break;
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    System.out.print("   ");
                } else {
                    System.out.printf("%2d ", board[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean move(String direction) {
        int newRow = emptyRow;
        int newCol = emptyCol;

        switch (direction) 
        {
            case "UP" : newRow--; break;
            case "DOWN" : newRow++; break;
            case "LEFT" : newCol--; break;
            case "RIGHT" : newCol++; break;
            default: 
            {
                System.out.println("Invalid direction! Use UP, DOWN, LEFT, RIGHT.");
                return false;
            }
    
        }
        if (newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE) {
            board[emptyRow][emptyCol] = board[newRow][newCol];
            board[newRow][newCol] = 0;
            emptyRow = newRow;
            emptyCol = newCol;
            return true;
        }
        
        return false;
    }
    public boolean isSolved() {
        int value = 1;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == SIZE - 1 && j == SIZE - 1) {
                    return board[i][j] == 0;
                }
                if (board[i][j] != value++) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FifteenPuzzle puzzle = new FifteenPuzzle();

        System.out.println("Welcome to the 15 Puzzle Game!");
        puzzle.printBoard();

        while (!puzzle.isSolved()) {
            System.out.print("Enter your move (UP, DOWN, LEFT, RIGHT): ");
            String move = scanner.nextLine().toUpperCase();

            if (puzzle.move(move)) {
                puzzle.printBoard();
            } else {
                System.out.println("Invalid move, try again.");
            }
        }
        System.out.println("Congratulations! You've solved the puzzle!");
        scanner.close();
    }
}
