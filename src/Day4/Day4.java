package Day4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day4 {

    private static File file = new File("src\\Day4\\input.txt");
    private static Scanner reader;

    /*
    Finds the board that wins first and calculates its score, printing it to the console.
     */
    public static void part1() {
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting.");
            return;
        }

        String numberInput = reader.nextLine();
        int[] numbers = Arrays.stream(numberInput.split(",")).mapToInt(Integer::parseInt).toArray();

        ArrayList<BingoCard> cards = createCards(reader);

        boolean winnerFound = false;

        for(int number : numbers) {
            if(winnerFound)
                break;

            for(BingoCard card : cards) {
                card.stampNumber(number);
                if(card.checkForWin()) {
                    System.out.println("Card number " + card.cardNumber + " will win first.");
                    System.out.println("Its score is " + card.calculateScore(number) + " points.");
                    winnerFound = true;
                    break;
                }
            }
        }

        reader.close();
    }

    /*
    Finds the board that wins *last* and calculates its score, printing it to the console
     */
    public static void part2() {
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting.");
            return;
        }

        String numberInput = reader.nextLine();
        int[] numbers = Arrays.stream(numberInput.split(",")).mapToInt(Integer::parseInt).toArray();

        ArrayList<BingoCard> cards = createCards(reader);
        int numOfWinners = 0;
        boolean loserFound = false;

        for(int number : numbers) {
            if(loserFound)
                break;

            for(BingoCard card : cards) {
                if(card.hasWon)
                    continue;

                card.stampNumber(number);
                if(card.checkForWin()) {
                    card.hasWon = true;
                    numOfWinners++;
                    if(numOfWinners == cards.size()) {
                        loserFound = true;
                        System.out.println("The last winner is card " + card.cardNumber + ".");
                        System.out.println("Its score is " + card.calculateScore(number) + ".");
                        break;
                    }
                }
            }
        }

        reader.close();
    }

    /*
    Returns an ArrayList of Boards created from the given input
     */
    private static ArrayList<BingoCard> createCards(Scanner scanner) {
        ArrayList<BingoCard> cards = new ArrayList<>();
        int boardNumber = 0;

        while(scanner.hasNextInt()) {
            cards.add(new BingoCard(scanner, boardNumber));
            boardNumber++;
        }

        return cards;
    }
}

/*
 Represents a Bingo card that can be stamped as numbers are called. Numbers are stored in a HashMap for quick access,
 as well as incremental aliases for those numbers to aid in row/column checking.
 */
class BingoCard {

    private final int BOARD_HEIGHT = 5;
    private final int BOARD_WIDTH = 5;
    private Map<Integer, BingoNumber> numbers = new HashMap<>();
    private Map<Integer, Integer> aliases = new HashMap<>();
    int cardNumber;
    boolean hasWon = false;

    public BingoCard(Scanner scanner, int cardNumber) {
        for(int i = 0; i < BOARD_HEIGHT * BOARD_WIDTH; i++) {
            int value = scanner.nextInt();
            numbers.put(value, new BingoNumber(value));
            aliases.put(i, value);
        }

        this.cardNumber = cardNumber;
    }

    /*
    Stamps a number if it is present on the Board
     */
    public void stampNumber(int number) {
        BingoNumber temp = numbers.get(number);

        if(temp != null) {
            temp.stamped = true;
        }
    }

    public boolean checkForWin() {
        return checkCols() || checkRows();
    }

    /*
    Returns true if every number in a row is stamped, and false otherwise
     */
    private boolean checkRows() {
        for(int i = 0; i < BOARD_WIDTH * BOARD_HEIGHT; i += BOARD_WIDTH) {
            int numsCalled = 0;
            for(int j = i; j < BOARD_WIDTH + i; j++) {
                if(numbers.get(aliases.get(j)).stamped)
                    numsCalled++;
                if(numsCalled == BOARD_WIDTH)
                    return true;
            }
        }

        return false;
    }

    /*
    Returns true if every number in a column is stamped, and false otherwise
     */
    private boolean checkCols() {
        for(int i = 0; i < BOARD_WIDTH; i++) {
            int numsCalled = 0;
            for(int j = i; j < BOARD_WIDTH * BOARD_HEIGHT; j += BOARD_WIDTH) {
                if(numbers.get(aliases.get(j)).stamped)
                        numsCalled++;
                if(numsCalled == BOARD_HEIGHT)
                    return true;
            }
        }

        return false;
    }

    /*
    Returns the sum of all uncalled numbers on the board, times the last number called
     */
    public int calculateScore(int numCalled) {
        int sumUncalled = 0;

        for(BingoNumber number : numbers.values())
            if(!number.stamped)
                sumUncalled += number.value;

        return sumUncalled * numCalled;
    }

    /*
    Represents an individual number on a Bingo card
     */
     private static class BingoNumber {
        int value;
        boolean stamped;
        public BingoNumber(int number) {
            this.value = number;
            this.stamped = false;
        }
    }
}