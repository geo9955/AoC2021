package Day5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

public class Day5 {

    public static void part1() { processVents(false); }

    public static void part2() { processVents(true); }

    /*
    Since part 1 and part 2 do the same thing, but part 1 has a restriction on diagonals, the logic is here instead
    Sets up the file and reader, and then for each input either add the vent (if diagonals are allowed)
    or adds the vent if it's not diagonal (if diagonals are not allowed)
     */
    private static void processVents(boolean allowDiagonal) {
        File file = new File("src\\Day5\\input.txt");
        VentMap vents = new VentMap();
        Scanner reader;
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting.");
            return;
        }

        while(reader.hasNextLine()) {
            int[] numbers = Stream.of(reader.nextLine().replaceAll("\\s", "").split(",|->"))
                    .mapToInt(Integer::parseInt).toArray();
            //pls java add destructuring
            int x1 = numbers[0];
            int y1 = numbers[1];
            int x2 = numbers[2];
            int y2 = numbers[3];

            if(allowDiagonal)
                vents.addLine(x1, x2, y1, y2);
            else if(x1 == x2 || y1 == y2)
                vents.addLine(x1, x2, y1, y2);
        }

        System.out.println("Number of dangerous vents: " + vents.getDangerCount());
        reader.close();
    }
}

class VentMap {
    Map<String, MutableInt> vents = new HashMap<>();

    /*
    Adds a line of vents to the vent map. Determines the line's slope, then adds each individual vent in the line,
    before adding the final vent. The final vent is separate because I couldn't think of a different way to allow the loop
    to both increment and decrement without a mess of ifs.
     */
    public void addLine(int x1, int x2, int y1, int y2) {
        int xMod = x2 < x1 ? -1 : 1;
        int yMod = y2 < y1 ? -1 : 1;

        while(x1 != x2 || y1 != y2) {
            if(x1 == x2)
                xMod = 0;
            if(y1 == y2)
                yMod = 0;

            addVent(x1, y1);
            x1 += xMod;
            y1 += yMod;
        }

        addVent(x1, y1);
    }

    /*
    Adds an individual vent to the vent map if it doesn't already exist there, or increments that vent's value if it does.
    Also prints a corresponding message to the console for debugging purposes and because I think it's neat.
    Only for use in addLine()
     */
    private void addVent(int i, int j) {
        String key = "(" + i + "," + j + ")";
        MutableInt vent = vents.get(key);

        if(vent == null) {
            System.out.println("Adding " + key);
            vents.put(key, new MutableInt());
        }
        else {
            System.out.println("Incrementing " + key);
            vent.increment();
        }
    }

    /*
    Gets the values present in the vent map and then discards all values less than the danger threshold.
    Returns the number of remaining vents (i.e. those that have several crossings over them)
     */
    public int getDangerCount() {
        final int DANGER_THRESHOLD = 2;

        Collection<MutableInt> dangerousVents = vents.values();
        dangerousVents.removeIf(s -> s.get() < DANGER_THRESHOLD);

        return dangerousVents.size();
    }

    /*
    Wrapper class because Integers are immutable and you can't give a Map a primitive
     */
    private class MutableInt {
        int value = 1;
        public void increment() {++value;}
        public int get() {return value;}
    }
}
