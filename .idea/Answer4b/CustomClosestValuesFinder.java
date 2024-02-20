import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// Node class for the custom binary tree
class CustomTreeNode {
    int value;
    CustomTreeNode leftChild, rightChild;

    public CustomTreeNode(int value) {
        this.value = value;
        this.leftChild = this.rightChild = null;
    }
}

// Class to find the closest values to a target value in a binary search tree
public class CustomClosestValuesFinder {

    // Method to find the closest values to the target value
    public static List<Integer> findClosestValues(CustomTreeNode rootNode, double targetValue, int numberOfClosestValues) {
        List<Integer> closestValuesList = new ArrayList<>();
        
        // Check if the tree is empty or the number of closest values required is zero
        if (rootNode == null || numberOfClosestValues == 0)
            return closestValuesList;

        Stack<CustomTreeNode> nodeStack = new Stack<>();
        CustomTreeNode currentNode = rootNode;

        // Traverse the tree using iterative inorder traversal
        while (currentNode != null || !nodeStack.isEmpty()) {
            while (currentNode != null) {
                nodeStack.push(currentNode);
                currentNode = currentNode.leftChild;
            }

            // Pop the node from the stack
            currentNode = nodeStack.pop();
            
            // If the list of closest values is not full, add the current node's value
            if (closestValuesList.size() < numberOfClosestValues) {
                closestValuesList.add(currentNode.value);
            } else {
                // Calculate the differences between the current node and the target value,
                // and the maximum difference among the current closest values
                double currentDifference = Math.abs(currentNode.value - targetValue);
                double maxDifference = Math.abs(closestValuesList.get(0) - targetValue);

                // If the current node is closer than the farthest closest value, update the list
                if (currentDifference < maxDifference) {
                    closestValuesList.remove(0);
                    closestValuesList.add(currentNode.value);
                } else {
                    // If the current node is farther than the farthest closest value, break the loop
                    break; 
                }
            }

            // Move to the right child of the current node
            currentNode = currentNode.rightChild;
        }

        return closestValuesList;
    }

    // Main method for testing the closest values finder
    public static void main(String[] args) {
        // Create a sample binary search tree
        CustomTreeNode rootNode = new CustomTreeNode(4);
        rootNode.leftChild = new CustomTreeNode(2);
        rootNode.rightChild = new CustomTreeNode(5);
        rootNode.leftChild.leftChild = new CustomTreeNode(1);
        rootNode.leftChild.rightChild = new CustomTreeNode(3);

        double targetValue = 3.8;
        int numberOfClosestValues = 2;

        // Find and print the closest values to the target value
        List<Integer> closestValues = findClosestValues(rootNode, targetValue, numberOfClosestValues);
        System.out.println("Closest values to " + targetValue + " are: " + closestValues); // Output: [4, 5]
    }
}
