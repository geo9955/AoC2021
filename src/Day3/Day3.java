package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3 {

    public static void part1() {

        File file = new File("src\\Day3\\input.txt");
        Scanner reader;

        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting");
            return;
        }

        ArrayList<String> numbers = new ArrayList<String>();
        while(reader.hasNextLine()) {
            numbers.add(reader.nextLine());
        }

        int bitNum, zeros, ones;
        String gamma = "", epsilon = "";

        for(bitNum = 0; bitNum < numbers.get(0).length(); bitNum++) {
            zeros = 0;
            ones = 0;
            for(String number : numbers) {
                if(number.charAt(bitNum) == '0')
                    zeros++;
                else
                    ones++;
            }

            if(zeros > ones)
                gamma += "0";
            else if (zeros < ones)
                gamma += "1";
            
        }

        for(int i = 0; i < gamma.length(); i++) {
            if(gamma.charAt(i) == '0')
                epsilon += "1";
            else
                epsilon += "0";
        }

        int gammaInt, epsilonInt;

        gammaInt = Integer.parseInt(gamma, 2);
        epsilonInt = Integer.parseInt(epsilon, 2);

        System.out.println("Gamma: " + gammaInt + " Epsilon: " + epsilonInt);
        System.out.println("Power Consumption: " + gammaInt * epsilonInt);

        
        reader.close();
    }
    
    public static void part2() {
        
    }
}
