import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ImprovedAntColony {

  // Parameters for the ant colony optimization algorithm
  private static final int NUM_ANTS = 10;
  private static final double ALPHA = 1.0;
  private static final double BETA = 2.0;
  private static final double RHO = 0.1;
  private static final double Q = 100;
  private static final int MAX_ITERATIONS = 1000;

  // Variables to store the distance matrix, pheromone matrix, ants, and random number generator
  private int numCities;
  private double[][] distanceMatrix;
  private double[][] pheromoneMatrix;
  private List<ImprovedAnt> ants;
  private Random random;

  // Constructor for the ImprovedAntColony class
  public ImprovedAntColony(double[][] distanceMatrix) {
    this.distanceMatrix = distanceMatrix;
    this.numCities = distanceMatrix.length;
    this.pheromoneMatrix = new double[numCities][numCities];
    this.ants = new ArrayList<>();
    this.random = new Random();

    // Initialize pheromone matrix with a small initial value
    for (int i = 0; i < numCities; i++) {
      for (int j = 0; j < numCities; j++) {
        pheromoneMatrix[i][j] = 0.01;
      }
    }
  }

  // Main method to solve the TSP problem using ant colony optimization
  public int[] solve() {
    int[] bestTour = null;
    double bestTourLength = Double.POSITIVE_INFINITY;

    // Run the algorithm for a specified number of iterations
    for (int iter = 0; iter < MAX_ITERATIONS; iter++) {

      // Initialize ants for a new iteration
      initializeAnts();

      // Move ants to the next city and complete their tours
      for (ImprovedAnt ant : ants) {
        for (int i = 0; i < numCities - 1; i++) {
          ant.moveNextCity(pheromoneMatrix, distanceMatrix, ALPHA, BETA, random);
        }
        ant.completeTour(distanceMatrix);

        // Update the best tour if a shorter tour is found
        if (ant.getTourLength() < bestTourLength) {
          bestTourLength = ant.getTourLength();
          bestTour = ant.getTour();
        }
      }

      // Update pheromones based on ant tours
      updatePheromones();
    }

    // Return the best tour found
    return bestTour;
  }

  // Initialize ants for a new iteration
  private void initializeAnts() {
    ants.clear();
    for (int i = 0; i < NUM_ANTS; i++) {
      ants.add(new ImprovedAnt(numCities));
    }
  }

  // Update pheromones based on ant tours
  private void updatePheromones() {

    // Evaporate pheromones
    for (int i = 0; i < numCities; i++) {
      for (int j = 0; j < numCities; j++) {
        pheromoneMatrix[i][j] *= (1 - RHO);
      }
    }

    // Deposit pheromones based on ant tours
    for (ImprovedAnt ant : ants) {
      double pheromoneToAdd = Q / ant.getTourLength();
      int[] tour = ant.getTour();
      for (int i = 0; i < numCities - 1; i++) {
        pheromoneMatrix[tour[i]][tour[i + 1]] += pheromoneToAdd;
      }

      // Connect the last city to the first city in the tour
      pheromoneMatrix[tour[numCities - 1]][tour[0]] += pheromoneToAdd;
    }
  }

  // Inner class representing an ant
  private static class ImprovedAnt {
    private int numCities;
    private int[] tour;
    private boolean[] visited;
    private double tourLength;

    // Constructor to initialize an ant
    public ImprovedAnt(int numCities) {
      this.numCities = numCities;
      this.tour = new int[numCities];
      this.visited = new boolean[numCities];
      this.tourLength = 0;

      // Choose a random starting city for the ant
      int startCity = new Random().nextInt(numCities);
      tour[0] = startCity;
      visited[startCity] = true;
    }

    // Move the ant to the next city based on pheromone and distance information
    public void moveNextCity(double[][] pheromoneMatrix, double[][] distanceMatrix,
                             double alpha, double beta, Random random) {
      int currentCity = tour[numCities - countUnvisitedCities()];
      double[] probabilities = calculateProbabilities(currentCity, pheromoneMatrix, distanceMatrix, alpha, beta);
      int nextCity = selectNextCity(probabilities, random);
      tour[numCities - countUnvisitedCities()] = nextCity;
      visited[nextCity] = true;
      tourLength += distanceMatrix[currentCity][nextCity];
    }

    // Complete the tour by connecting the last city to the first city in the tour
    public void completeTour(double[][] distanceMatrix) {
      int lastCity = tour[numCities - 1];
      int firstCity = tour[0];
      tourLength += distanceMatrix[lastCity][firstCity];
    }

    // Getters for the tour and tour length
    public int[] getTour() {
      return tour;
    }

    public double getTourLength() {
      return tourLength;
    }

    // Count the number of unvisited cities
    private int countUnvisitedCities() {
      int count = 0;
      for (boolean cityVisited : visited) {
        if (!cityVisited) {
          count++;
        }
      }
      return count;
    }

    // Calculate probabilities for the next city based on pheromone and distance information
    private double[] calculateProbabilities(int currentCity, double[][] pheromoneMatrix,
                                            double[][] distanceMatrix, double alpha, double beta) {
      double[] probabilities = new double[numCities];
      double totalProbability = 0;
      for (int i = 0; i < numCities; i++) {
        if (!visited[i]) {
          double pheromone = Math.pow(pheromoneMatrix[currentCity][i], alpha);
          double distance = Math.pow(1.0 / distanceMatrix[currentCity][i], beta);
          probabilities[i] = pheromone * distance;
          totalProbability += probabilities[i];
        }
      }

      // Normalize probabilities
      for (int i = 0; i < numCities; i++) {
        if (!visited[i]) {
          probabilities[i] /= totalProbability;
        }
      }
      return probabilities;
    }

    // Select the next city based on the calculated probabilities
    private int selectNextCity(double[] probabilities, Random random) {
      double randomValue = random.nextDouble();
      double cumulativeProbability = 0;
      for (int i = 0; i < numCities; i++) {
        if (!visited[i]) {
          cumulativeProbability += probabilities[i];
          if (randomValue <= cumulativeProbability) {
            return i;
          }
        }
      }

      // In case of numerical errors, return the first unvisited city
      for (int i = 0; i < numCities; i++) {
        if (!visited[i]) {
          return i;
        }
      }
      return -1;
    }
  }

  // Main method for testing the ImprovedAntColony class
  public static void main(String[] args) {

    // Example distance matrix for testing
    double[][] distanceMatrix = {
        {0, 10, 15, 20},
        {10, 0, 35, 25},
        {15, 35, 0, 30},
        {20, 25, 30, 0}
    };

    // Create an instance of ImprovedAntColony and solve the TSP problem
    ImprovedAntColony aco = new ImprovedAntColony(distanceMatrix);
    int[] bestTour = aco.solve();

    // Print the best tour
    System.out.println("Best tour: ");
    for (int city : bestTour) {
      System.out.print(city + " ");
    }
    System.out.println();
  }
}
