public class EngineBuildingOptimizer {

    // Method to calculate the minimum time to build engines
    public static int minTimeToBuild(int[] engines, int splitCost) {
        int totalTime = 0;

        // Iterate through each engine building time
        for (int engineTime : engines) {
            
            // Check if splitting the engine building time is beneficial
            if (splitCost + engineTime / 2 < engineTime) {
                
                // If splitting is beneficial, add the split cost to the total time
                totalTime += splitCost;
            } else {
                
                // If splitting is not beneficial, add the full engine building time to the total time
                totalTime += engineTime;
            }
        }

        // Return the total time to build all engines
        return totalTime;
    }

    // Main method for testing the minTimeToBuild function
    public static void main(String[] args) {
        int[] engines = {1, 2, 3}; // Array representing the building time for each engine
        int splitCost = 1; // Cost to split an engine building time

        // Display the minimum time to build engines
        System.out.println("Minimum time to build engines: " + minTimeToBuild(engines, splitCost));
    }
}
