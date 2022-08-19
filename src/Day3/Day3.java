package Day3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Day3 {

    private static File file = new File("src\\Day3\\input.txt");
    private static Scanner reader;
    private static int bitNum, zeros, ones;

    /*
    Prints the power consumption of the sub by determining the most common bit in each position and adding it to
    the gamma string. Epsilon is then found by flipping the bits in gamma (i.e. the least common bit). Bitwise negation
    can't be used because of 2's complement stuff. Power consumption printed by multiplying epsilon and gamma.
     */
    public static void part1() {
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting");
            return;
        }

        ArrayList<String> numbers = new ArrayList<>();
        while(reader.hasNextLine()) {
            numbers.add(reader.nextLine());
        }

        String gammaString = "", epsilonString = "";

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
                gammaString += "0";
            else if (zeros < ones)
                gammaString += "1";
        }

        for(int i = 0; i < gammaString.length(); i++) {
            if(gammaString.charAt(i) == '0')
                epsilonString += "1";
            else
                epsilonString += "0";
        }

        int gamma = Integer.parseInt(gammaString, 2);
        int epsilon = Integer.parseInt(epsilonString, 2);

        System.out.println("Gamma: " + gamma + " Epsilon: " + epsilon);
        System.out.println("Power Consumption: " + gamma * epsilon);

        
        reader.close();
    }

    /*
    Calculates the sub's life support rating through a process like above, except if the numbers are equal
    it uses a 1 for the oxygen and a 0 for the co2.
     */
    public static void part2() {
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting");
            return;
        }

        ArrayList<String> oxygenNums = new ArrayList<>();
        while(reader.hasNextLine()) {
            oxygenNums.add(reader.nextLine());
        }

        ArrayList<String> co2Nums = (ArrayList) oxygenNums.clone();
        bitNum = zeros =  ones = 0;
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

            //copying values because lambdas won't accept non-final variables (effectively final is fine)
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

        System.out.println("Oxygen Rating: " + oxyRating + " CO2 Rating: " + co2Rating);
        System.out.println("Life Support Rating: " + oxyRating * co2Rating);
    }
}
