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
        File file = new File("src\\Day3\\input.txt");
        Scanner reader;

        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting");
            return;
        }

        ArrayList<String> oxygenNums = new ArrayList<String>();
        while(reader.hasNextLine()) {
            oxygenNums.add(reader.nextLine());
        }

        ArrayList<String> co2Nums = (ArrayList<String>) oxygenNums.clone();
        int bitNum = 0, zeros = 0, ones = 0;
        char bitCriteria;

        while(oxygenNums.size() > 1) {
            for(String number : oxygenNums) {
                if(number.charAt(bitNum) == '0')
                    zeros++;
                else
                    ones++;
            }

            if(zeros > ones)
                bitCriteria = '0';
            else
                bitCriteria = '1';

            int finalBitNum = bitNum;
            char finalBitCriteria = bitCriteria;
            oxygenNums.removeIf(s -> s.charAt(finalBitNum) != finalBitCriteria);

            bitNum++;
            zeros = ones = 0;
        }

        bitNum = 0;

        while(co2Nums.size() > 1) {
            for(String number : co2Nums) {
                if(number.charAt(bitNum) == '0')
                    zeros++;
                else
                    ones++;
            }

            if(ones < zeros)
                bitCriteria = '1';
            else
                bitCriteria = '0';

            int finalBitNum1 = bitNum;
            char finalBitCriteria1 = bitCriteria;
            co2Nums.removeIf(s -> s.charAt(finalBitNum1) != finalBitCriteria1);
            bitNum++;
            zeros = ones = 0;
        }

        int oxyRating = Integer.parseInt(oxygenNums.get(0), 2);
        int co2Rating = Integer.parseInt(co2Nums.get(0), 2);

        System.out.println("Oxygen Rating" + oxyRating + " CO2 Rating: " + co2Rating);
        System.out.println("Life Support Rating: " + oxyRating * co2Rating);
    }
}
