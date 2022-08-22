package Day8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day8 {

    private static File file = new File("src\\Day8\\input.txt");
    private static Scanner reader;

    public static void part1() {
        try{
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting.");
            return;
        }

        ArrayList<String[]> displays = new ArrayList<>();

        while(reader.hasNextLine())
            displays.add(reader.nextLine().split("\\|")[1].trim().split(" "));

        int uniqueSum = 0;

        for (String[] display: displays) {
            for(String digit : display)
                if(digit.length() != 5 && digit.length() != 6)
                    uniqueSum++;
        }

        System.out.println("There are " + uniqueSum + " digits with a unique pattern in the input.");
        reader.close();
    }

    public static void part2() {

    }
}
