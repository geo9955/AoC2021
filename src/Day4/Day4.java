package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day4 {

    final static int BOARD_HEIGHT = 5;
    final static int BOARD_WIDTH = 5;

    public static void part1() {
        File file = new File("src\\Day4\\input.txt");
        Scanner reader;
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting.");
            return;
        }

        String numberInput = reader.nextLine();
        int[] numbers = Arrays.stream(numberInput.split(",")).mapToInt(Integer::parseInt).toArray();

        ArrayList<Board> boards = createBoards(reader);

        boolean winnerFound = false;

        for(int number : numbers) {
            if(winnerFound)
                break;

            for(Board board : boards) {
                board.markNumber(number);
                if(board.checkForWin()) {
                    System.out.println("Board number " + board.boardNumber + " will win first.");
                    System.out.println("Its score is " + board.calculateScore(number) + " points.");
                    winnerFound = true;
                    break;
                }
            }
        }

        reader.close();
    }

    public static void part2() {

        File file = new File("src\\Day4\\input.txt");
        Scanner reader;
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting.");
            return;
        }

        String numberInput = reader.nextLine();
        int[] numbers = Arrays.stream(numberInput.split(",")).mapToInt(Integer::parseInt).toArray();

        ArrayList<Board> boards = createBoards(reader);
        int numOfWinners = 0;
        boolean loserFound = false;

        for(int number : numbers) {
            if(loserFound)
                break;

            for(Board board : boards) {
                if(board.hasWon)
                    continue;

                board.markNumber(number);
                if(board.checkForWin()) {
                    board.hasWon = true;
                    numOfWinners++;
                    if(numOfWinners == boards.size()) {
                        loserFound = true;
                        System.out.println("The last winner is board " + board.boardNumber + ".");
                        System.out.println("Its score is " + board.calculateScore(number));
                        break;
                    }
                }
            }
        }

        reader.close();
    }

    private static ArrayList<Board> createBoards(Scanner scanner) {
        ArrayList<Board> boards = new ArrayList<>();
        int boardNumber = 0;

        while(scanner.hasNextInt()) {
            boards.add(new Board(scanner, boardNumber));
            boardNumber++;
        }

        return boards;
    }


    private static class Board {

        class BingoNumber {
            int number;
            boolean called;
            public BingoNumber(int number) {
                this.number = number;
                this.called = false;
            }
        }

        BingoNumber[][] numbers = new BingoNumber[BOARD_HEIGHT][BOARD_WIDTH];
        int boardNumber;
        boolean hasWon;

        public Board(Scanner scanner, int boardNumber) {
            for(int i = 0; i < BOARD_HEIGHT; i++) {
                for(int j = 0; j < BOARD_WIDTH; j++) {
                    numbers[i][j] = new BingoNumber(scanner.nextInt());
                }
            }

            this.boardNumber = boardNumber;
            this.hasWon = false;
        }

        public void markNumber(int number) {
            for(int row = 0; row < BOARD_HEIGHT; row++) {
                for(int col = 0; col < BOARD_WIDTH; col++) {
                    if(numbers[row][col].number == number) {
                        numbers[row][col].called = true;
                        return;
                    }
                }
            }
        }

        public boolean checkForWin() {
            return checkCols() || checkRows();
        }

        public int calculateScore(int numCalled) {
            int sumUncalled = 0;

            for(int row = 0; row < BOARD_HEIGHT; row++) {
                for(int col = 0; col < BOARD_WIDTH; col++)
                    if(!numbers[row][col].called)
                        sumUncalled += numbers[row][col].number;
            }

            return sumUncalled * numCalled;
        }

        private boolean checkRows() {
            int numsCalled;
            for(int row = 0; row < BOARD_HEIGHT; row++) {
                numsCalled = 0;
                for(int col = 0; col < BOARD_WIDTH; col++) {
                    if(numbers[row][col].called)
                        numsCalled++;
                }

                if(numsCalled == BOARD_WIDTH)
                    return true;
            }

            return false;
        }

        private boolean checkCols() {
            int numsCalled;
            for(int col = 0; col < BOARD_WIDTH; col++) {
                numsCalled = 0;
                for(int row = 0; row < BOARD_HEIGHT; row++) {
                    if(numbers[row][col].called)
                        numsCalled++;
                }

                if(numsCalled == BOARD_HEIGHT)
                    return true;
            }

            return false;
        }
    }
}
