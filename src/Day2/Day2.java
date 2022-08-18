package Day2;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Day2 {

    private static File file = new File("src\\Day2\\input.txt");
    private static Scanner reader;
    private static String line, dir;
    private static String[] inputs;
    private static int hpos = 0, depth = 0, x;

    public static void part1() {
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting");
            return;
        }

        while(reader.hasNextLine()) {
            line = reader.nextLine();
            inputs = line.split(" ");
            dir = inputs[0];
            x = Integer.parseInt(inputs[1]);
            
            switch(dir) {
                case "forward":
                hpos += x;
                break;

                case "down":
                depth += x;
                break;

                case "up":
                depth -= x;
                break;
            }
        }

        printResults();

        reader.close();
    }

    public static void part2() {
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting");
            return;
        }

        int aim = 0;

        while(reader.hasNextLine()) {
            line = reader.nextLine();
            inputs = line.split(" ");
            dir = inputs[0];
            x = Integer.parseInt(inputs[1]);

            switch(dir) {
                case "forward":
                hpos += x;
                depth += aim * x;
                break;

                case "down":
                aim += x;
                break;

                case "up":
                aim -= x;
                break;
            }
        }

        printResults();

        reader.close();
    }

    private static void printResults() {
        System.out.println("Horizontal Position: " + hpos + " Depth: " + depth);
        System.out.println("Both: " + hpos * depth);
    }
}
