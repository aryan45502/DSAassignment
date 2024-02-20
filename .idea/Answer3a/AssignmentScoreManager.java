import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The AssignmentScoreManager class manages assignment scores and calculates the median score.
 */
public class AssignmentScoreManager {
  private List<Double> scores; 

  /**
   * Constructs an AssignmentScoreManager with an empty list of scores.
   */
  public AssignmentScoreManager() {
    scores = new ArrayList<>();
  }

  /**
   * Adds a score to the list and ensures the scores are sorted.
   * @param score The score to be added.
   */
  public void addScore(double score) {
    scores.add(score); 
    Collections.sort(scores); 
  }

  /**
   * Calculates and returns the median score from the list of scores.
   * @return The median score.
   * @throws IllegalStateException if no scores are available.
   */
  public double getMedianScore() {
    int size = scores.size();
    if (size == 0) {
      throw new IllegalStateException("No scores available.");
    }

    int middleIndex = size / 2; 

    if (size % 2 == 0) {
      // If the number of scores is even, calculate the average of the two middle scores.
      double lowerMedian = scores.get(middleIndex - 1);
      double upperMedian = scores.get(middleIndex);
      return (lowerMedian + upperMedian) / 2.0;
    } else {
      // If the number of scores is odd, return the middle score.
      return scores.get(middleIndex);
    }
  }

  /**
   * The main method demonstrates the functionality of the AssignmentScoreManager class.
   * @param args Command line arguments (not used in this example).
   */
  public static void main(String[] args) {
    // Create an instance of AssignmentScoreManager.
    AssignmentScoreManager scoreManager = new AssignmentScoreManager();

    // Add scores to the manager and calculate the median after each addition.
    scoreManager.addScore(85.5);
    scoreManager.addScore(92.3);
    scoreManager.addScore(77.8);
    scoreManager.addScore(90.1);
    double median1 = scoreManager.getMedianScore();
    System.out.println("Median score 1: " + median1);

    // Add more scores and calculate the updated median.
    scoreManager.addScore(81.2);
    scoreManager.addScore(88.7);
    double median2 = scoreManager.getMedianScore();
    System.out.println("Median score 2: " + median2);
  }
}
