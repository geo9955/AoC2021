package Day6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day6 {

    private static File file = new File("src\\Day6\\input.txt");
    private static Scanner reader;
    private static int DAYS_TO_PASS = 80;

    public static void part1() {
        try{
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting.");
            return;
        }

        ArrayList<LanternFish> school = new ArrayList<>();
        ArrayList<LanternFish> fishToAdd;
        String[] initialDays = reader.nextLine().split(",");
        Arrays.stream(initialDays).mapToInt(Integer::parseInt).forEach(daysToSpawn -> school.add(new LanternFish(daysToSpawn)));

        for(int i = 1; i <= DAYS_TO_PASS; i++) {
            fishToAdd = new ArrayList<>();
            for (LanternFish fish : school) {
                fish.attemptToSpawn(fishToAdd);
            }
            school.addAll(fishToAdd);
        }

        System.out.println("The school has reached a size of " + school.size() + " fish.");


        //int[] startingFish = Arrays.stream(fishInput.split(",")).mapToInt(Integer::parseInt).toArray();
        //ArrayList<LanternFish> school = new ArrayList<>();
        //for(int daysToSpawn : startingFish)
        //    school.add(new LanternFish(daysToSpawn));
        reader.close();
    }

    public static void part2() {

    }
}

class LanternFish {
    private int daysToSpawn;

    public LanternFish() {
        daysToSpawn = 8;
    }

    public LanternFish(int daysToSpawn) {
        this.daysToSpawn = daysToSpawn;
    }

    public void attemptToSpawn(List<LanternFish> fish) {
        if(daysToSpawn == 0) {
            fish.add(new LanternFish());
            daysToSpawn = 6;
        }

        else
            daysToSpawn--;
    }

    public int getDaysToSpawn() {
        return daysToSpawn;
    }
}