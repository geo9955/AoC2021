package Day7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.IntStream;

public class Day7 {

    public static void part1() { processCrabs(false); }

    public static void part2() { processCrabs(true ); }

    /*
    Gets the position of all crabs, finds the range of positions that should be tested, and groups crabs by their positions.
    Then it finds and prints the most efficient position to move to, and how much it costs, depending on how much it costs
    to make a move.
     */
    public static void processCrabs(boolean isExpensive) {
        File file = new File("src\\Day7\\input.txt");
        Scanner reader;
        try {
            reader = new Scanner(file);
        } catch(FileNotFoundException e) {
            System.out.println("File not found. Aborting.");
            return;
        }

        int[] crabs = Arrays.stream(reader.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int[] positionsToTest = getPositionsToTest(crabs);
        Map<Integer, Integer> crabMap = groupCrabsByPosition(crabs);
        AbstractMap.SimpleEntry<Integer, Integer> lowestFuelPosition = lowestFuelPositionAndCost(crabMap, positionsToTest, isExpensive);

        System.out.println("The most efficient position to move to is " + lowestFuelPosition.getKey() + " and the cost to get there is " + lowestFuelPosition.getValue() + " units of fuel.");

        reader.close();
    }

    /*
    Consolidates crabs into groups based on their position. Returns a Map of unique positions and the number of crabs therein.
     */
    private static Map<Integer, Integer> groupCrabsByPosition(int[] crabs) {
        Map<Integer, Integer> crabMap = new HashMap<>();
        for(int crab : crabs) {
            Integer numCrabs = crabMap.get(crab);
            if(numCrabs != null)
                crabMap.replace(crab, numCrabs+1);
            else
                crabMap.put(crab, 1);
        }

        return crabMap;
    }

    /*
    Returns a key-value pair representing the most efficient position for all crabs to move to, and the total fuel cost to move there
     */
    private static AbstractMap.SimpleEntry<Integer, Integer> lowestFuelPositionAndCost(Map<Integer, Integer> crabMap, int[] positionsToTest, boolean isExpensive) {
        AbstractMap.SimpleEntry<Integer, Integer> minFuelEntry = null;

        for(int toPosition : positionsToTest) {
            int fuelSum = 0;
            for(Map.Entry<Integer, Integer> fromPosition : crabMap.entrySet()) {
                int posDiff = Math.abs(fromPosition.getKey() - toPosition);
                int numCrabs = fromPosition.getValue();
                int cost = isExpensive ? costToMoveNSteps(posDiff) : posDiff;
                fuelSum += cost * numCrabs;
            }

            if(minFuelEntry == null || fuelSum < minFuelEntry.getValue())
                minFuelEntry = new AbstractMap.SimpleEntry<>(toPosition, fuelSum);
        }

        assert minFuelEntry != null;
        return minFuelEntry;
    }

    /*
    Returns the sum of the first n natural numbers
     */
    private static int costToMoveNSteps(int n) { return (n * (n + 1) / 2); }

    /*
    Returns an array containing all the positions between the minimum and maximum positions occupied by any crab, inclusive.
     */
    private static int[] getPositionsToTest(int[] crabs) {
        Integer minPos = null;
        Integer maxPos = null;
        for(int position : crabs) {
            if(minPos == null || position < minPos)
                minPos = position;
            if(maxPos == null || position > maxPos)
                maxPos = position;
        }

        return IntStream.iterate(minPos, i -> i+1).limit(maxPos + 1).toArray();
    }
}
