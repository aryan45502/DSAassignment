import java.util.LinkedList;
import java.util.Queue;

public class CustomMazeExplorer {
    // Method to find the minimum moves to collect all keys in the given maze
    public static int findMinimumMovesToCollectAllKeys(String[] grid) {
        // Get the number of rows and columns in the grid
        int rows = grid.length;
        int cols = grid[0].length();

        // Initialize the targetKeys variable to represent all keys that need to be collected
        int targetKeys = 0;

        // Initialize the starting position (startX, startY)
        int startX = 0, startY = 0;

        // Iterate through the grid to find the starting position and target keys
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char cell = grid[i].charAt(j);
                if (cell == 'S') {
                    startX = i;
                    startY = j;
                } else if (cell == 'E') {
                    // Mark the target exit door with a corresponding key
                    targetKeys |= (1 << ('f' - 'a'));
                } else if (cell >= 'a' && cell <= 'f') {
                    // Mark the keys in the maze
                    targetKeys |= (1 << (cell - 'a'));
                }
            }
        }

        // Create a queue for BFS traversal and a 3D array to track visited cells with keys
        Queue<int[]> queue = new LinkedList<>();
        boolean[][][] visited = new boolean[rows][cols][1 << 6]; // Using 6 bits to represent keys (a-f)

        // Add the starting position to the queue with initial keys and steps
        queue.offer(new int[] { startX, startY, 0, 0 });

        // Define possible directions (up, down, left, right)
        int[][] directions = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

        // Perform BFS traversal until all keys are collected or no valid moves are left
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int keys = current[2];
            int steps = current[3];

            // If all keys are collected, return the minimum steps
            if (keys == targetKeys) {
                return steps;
            }

            // Explore neighboring cells in all directions
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                // Check if the new position is within bounds and not a wall
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX].charAt(newY) != 'W') {
                    char cell = grid[newX].charAt(newY);

                    // Check if the cell is an exit, a passage, a key, or a door with the required key
                    if (cell == 'E' || cell == 'P' || (cell >= 'a' && cell <= 'f')
                            || (cell >= 'A' && cell <= 'F' && (keys & (1 << (cell - 'A'))) != 0)) {
                        int newKeys = keys;

                        // If the cell contains a key, update the keys variable
                        if (cell >= 'a' && cell <= 'f') {
                            newKeys |= (1 << (cell - 'a'));
                        }

                        // Check if the new position with keys has not been visited
                        if (!visited[newX][newY][newKeys]) {
                            // Mark the new position as visited and add it to the queue
                            visited[newX][newY][newKeys] = true;
                            queue.offer(new int[] { newX, newY, newKeys, steps + 1 });
                        }
                    }
                }
            }
        }

        // If no valid path is found, return -1
        return -1;
    }

    // Main method for testing the maze explorer
    public static void main(String[] args) {
        // Example maze represented as an array of strings
        String[] grid = { "SPaPP", "WWWPW", "bPAPB" };

        // Call the method to find the minimum moves to collect all keys
        int result = findMinimumMovesToCollectAllKeys(grid);

        // Print the result
        System.out.println("Minimum number of moves: " + result);
    }
}
