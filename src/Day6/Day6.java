package Day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day6 {



    /*
    Wrappers to pass the day's input to the logic
     */
    public static void part1() {
        final int DAYS_TO_PASS = 80;
        processFish(DAYS_TO_PASS);
    }

    public static void part2() {
        final int DAYS_TO_PASS = 256;
        processFish(DAYS_TO_PASS);
    }

    /*
    Instantiates an ArrayList of longs whose indices represent the number of Lanternfish with that many days left until
    they spawn. For each day, every index's value is placed in the index below it (wrapping to 8 if at 0). A number of
    fish equal to the spawned fish are added to 6, to represent the fish that did the spawning.
     */
    private static void processFish(final int DAYS_TO_PASS) {
        File file = new File("src\\Day6\\input.txt");
        Scanner reader;
        try{
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting.");
            return;
        }

        ArrayList<Long> school = new ArrayList<>(Collections.nCopies(9, 0L));
        int[] initialFish = Arrays.stream(reader.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        for(int fish : initialFish)
            school.set(fish, school.get(fish) + 1);

        for(int i = 1; i <= DAYS_TO_PASS; i++) {
            Collections.rotate(school, -1);
            school.set(6, school.get(6) + school.get(8));
        }

        long population = school.stream().mapToLong(Long::longValue).sum();
        System.out.println("The school has reached a size of " + population + " fish.");

        reader.close();
    }
}