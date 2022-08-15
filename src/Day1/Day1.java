package Day1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day1 {
    public static void part1() {
        File file = new File("src\\Day1\\input.txt");
        Scanner reader;

        try{
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting");
            return;
        }

        int increases = 0;
        int previous = reader.nextInt();
        int current;

        do {
            current = reader.nextInt();

            if(current > previous) {
                increases++;
            }

            previous = current;
        } while(reader.hasNextInt());
        reader.close();

        System.out.println(increases);
    }

    public static void part2() {
        File file = new File("src\\Day1\\input.txt");
        Scanner reader;

        try{
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting");
            return;
        }

        int increases = 0;
        int a = reader.nextInt();
        int b = reader.nextInt();
        int c = reader.nextInt();
        int d;
        
        do {
            d = reader.nextInt();

            if(b + c + d > a + b + c) {
                increases++;
            }

            a = b;
            b = c;
            c = d;
        } while(reader.hasNextInt());
        reader.close();

        System.out.println(increases);
    }
}
