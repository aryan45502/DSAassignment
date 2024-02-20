import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SecretSharingSystem {

  // Method to find individuals who eventually know the secret
  public static List<Integer> findSecretRecipients(int totalIndividuals, int[][] sharingIntervals, int initialPerson) {
    // Set to store unique secret recipients
    Set<Integer> secretRecipients = new HashSet<>();
    // Add the initial person to the set, as they start with the secret
    secretRecipients.add(initialPerson);

    // Iterate through sharing intervals
    for (int[] interval : sharingIntervals) {
      int intervalStart = interval[0];
      int intervalEnd = interval[1];

      // Check if the initial person is within the current sharing interval
      if (intervalStart <= initialPerson && initialPerson <= intervalEnd) {
        // If yes, add all individuals to the set, as the secret is shared with everyone
        for (int i = 0; i < totalIndividuals; i++) {
          secretRecipients.add(i);
        }
      }
    }

    // Convert the set to a list and return
    return new ArrayList<>(secretRecipients);
  }

  // Main method to demonstrate the functionality
  public static void main(String[] args) {
    // Input parameters
    int totalIndividuals = 5;
    int[][] sharingIntervals = { { 0, 2 }, { 1, 3 }, { 2, 4 } };
    int initialPerson = 0;

    // Find secret recipients using the method
    List<Integer> secretRecipients = findSecretRecipients(totalIndividuals, sharingIntervals, initialPerson);

    // Display the result
    System.out.println("Individuals who eventually know the secret:");
    for (int recipient : secretRecipients) {
      System.out.print(recipient + " ");
    }
  }
}
