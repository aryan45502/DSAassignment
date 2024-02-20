public class DecorCostCalculator {

  // Function to find the minimum cost of decorating venues
  public static int findMinimumCost(int[][] venueThemes) {

    // Check for valid input
    if (venueThemes == null || venueThemes.length == 0 || venueThemes[0].length == 0) {
      return 0; // Return 0 for invalid input
    }

    // Get the number of venues and themes
    int numVenues = venueThemes.length;
    int numThemes = venueThemes[0].length;

    // Array to store minimum costs for each venue and theme combination
    int[][] minCosts = new int[numVenues][numThemes];
    System.arraycopy(venueThemes[0], 0, minCosts[0], 0, numThemes);

    // Dynamic programming approach to find the minimum cost
    for (int venueIndex = 1; venueIndex < numVenues; venueIndex++) {
      for (int themeIndex = 0; themeIndex < numThemes; themeIndex++) {
        minCosts[venueIndex][themeIndex] = Integer.MAX_VALUE;

        // Iterate over previous themes to find the minimum cost
        for (int prevThemeIndex = 0; prevThemeIndex < numThemes; prevThemeIndex++) {
          if (prevThemeIndex != themeIndex) {
            minCosts[venueIndex][themeIndex] = Math.min(minCosts[venueIndex][themeIndex],
                minCosts[venueIndex - 1][prevThemeIndex]);
          }
        }
        
        // Add the cost of decorating the current venue with the current theme
        minCosts[venueIndex][themeIndex] += venueThemes[venueIndex][themeIndex];
      }
    }

    // Find the minimum cost among the last row of the minCosts array
    int minCost = Integer.MAX_VALUE;
    for (int cost : minCosts[numVenues - 1]) {
      minCost = Math.min(minCost, cost);
    }

    // Return the minimum cost
    return minCost;
  }

  // Main method to demonstrate the functionality
  public static void main(String[] args) {
    
    // Example input for venueThemes
    int[][] venueThemes = { { 1, 3, 2 }, { 4, 6, 8 }, { 3, 1, 5 } };

    // Call the function to find the minimum cost
    int minCost = findMinimumCost(venueThemes);

    // Print the result
    System.out.println("The minimum cost of venue decoration is: " + minCost); 
  }
}
