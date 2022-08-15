package Day2;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Day2 {

    public static void part1() {

        File file = new File("src\\Day2\\input.txt");
        Scanner reader;

        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting");
            return;
        }

        int hpos = 0, depth = 0, x;
        String[] inputs;
        String line, dir;

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

        System.out.println("Horizontal Position: " + hpos + " Depth: " + depth);
        System.out.println("Both: " + hpos * depth);

        reader.close();
    }

    public static void part2() {

    }
}
