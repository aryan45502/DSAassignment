public class DressEqualizer {

    // Method to calculate the minimum moves required to equalize the dresses array
    public int minMovesToEqualize(int[] dresses) {
        int n = dresses.length; // Get the length of the dresses array
        int totalDresses = 0; // Variable to store the total number of dresses

        // Calculate the total number of dresses in the array
        for (int dress : dresses) {
            totalDresses += dress;
        }

        // Check if it's possible to equalize the dresses, i.e., if the total can be evenly distributed
        if (totalDresses % n != 0) {
            return -1; // If not, return -1 indicating it's not possible
        }

        int target = totalDresses / n; // Calculate the target number of dresses each person should have
        int moves = 0; // Variable to store the total moves required
        int[] prefixSum = new int[n + 1]; // Array to store the prefix sum of dresses

        // Calculate the prefix sum of dresses array
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + dresses[i - 1];
        }

        // Iterate through the dresses array to calculate the moves required
        for (int i = 0; i < n; i++) {
            int expected = target * (i + 1); // Calculate the expected sum at position i
            int actual = prefixSum[i + 1]; // Get the actual sum at position i
            moves += Math.abs(expected - actual); // Accumulate the absolute difference as moves
        }

        // Return the total moves required divided by 2 (as each move affects two people)
        return moves / 2;
    }

    // Main method for testing the DressEqualizer class
    public static void main(String[] args) {
        DressEqualizer equalizer = new DressEqualizer();
        int[] dresses = {1, 0, 5};
        System.out.println("Minimum moves required: " + equalizer.minMovesToEqualize(dresses));
    }
}
