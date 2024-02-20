import java.util.*;

class Edge {
  int source, destination, weight;

  Edge(int source, int destination, int weight) {
    this.source = source;
    this.destination = destination;
    this.weight = weight;
  }
}

public class Graphedge{
  // Kruskal's Minimum Spanning Tree algorithm
  public static List<Edge> kruskalMST(List<Edge> edges, int vertices) {
    List<Edge> result = new ArrayList<>();
    
    // Sort the edges based on weights in ascending order
    Collections.sort(edges, Comparator.comparingInt(e -> e.weight));
    
    // Union-Find data structure for cycle detection
    UnionFind unionFind = new UnionFind(vertices);
    
    // Iterate through sorted edges and add to result if no cycle is formed
    for (Edge edge : edges) {
      int sourceRep = unionFind.find(edge.source);
      int destRep = unionFind.find(edge.destination);
      if (sourceRep != destRep) {
        result.add(edge);
        unionFind.union(sourceRep, destRep);
      }
    }
    return result;
  }

  // Union-Find data structure for cycle detection
  static class UnionFind {
    private int[] parent;

    public UnionFind(int size) {
      parent = new int[size];
      for (int i = 0; i < size; i++) {
        parent[i] = i;
      }
    }

    // Find operation with path compression
    public int find(int vertex) {
      if (parent[vertex] != vertex) {
        parent[vertex] = find(parent[vertex]);
      }
      return parent[vertex];
    }

    // Union operation
    public void union(int x, int y) {
      int rootX = find(x);
      int rootY = find(y);
      parent[rootX] = rootY;
    }
  }

  // Priority Queue implemented as Min Heap
  static class PriorityQueueMinHeap {
    private final List<Edge> heap;

    PriorityQueueMinHeap() {
      heap = new ArrayList<>();
    }

    // Add an edge to the heap while maintaining the heap property
    void add(Edge edge) {
      heap.add(edge);
      int current = heap.size() - 1;
      while (current > 0 && heap.get(current).weight < heap.get((current - 1) / 2).weight) {
        Collections.swap(heap, current, (current - 1) / 2);
        current = (current - 1) / 2;
      }
    }

    // Remove the minimum-weight edge from the heap
    Edge removeMin() {
      if (heap.isEmpty()) {
        throw new NoSuchElementException("Priority queue is empty");
      }
      Edge min = heap.get(0);
      heap.set(0, heap.get(heap.size() - 1));
      heap.remove(heap.size() - 1);
      minHeapify(0);
      return min;
    }

    // Maintain the heap property after removal of the minimum-weight edge
    private void minHeapify(int index) {
      int left = 2 * index + 1;
      int right = 2 * index + 2;
      int smallest = index;

      if (left < heap.size() && heap.get(left).weight < heap.get(smallest).weight) {
        smallest = left;
      }
      if (right < heap.size() && heap.get(right).weight < heap.get(smallest).weight) {
        smallest = right;
      }
      if (smallest != index) {
        Collections.swap(heap, index, smallest);
        minHeapify(smallest);
      }
    }

    // Check if the priority queue is empty
    boolean isEmpty() {
      return heap.isEmpty();
    }
  }

  public static void main(String[] args) {
    // Example usage of Kruskal's algorithm on a graph and Priority Queue
    List<Edge> edges = new ArrayList<>();
    edges.add(new Edge(0, 1, 10));
    edges.add(new Edge(0, 2, 6));
    edges.add(new Edge(0, 3, 5));
    edges.add(new Edge(1, 3, 15));
    edges.add(new Edge(2, 3, 4));

    int vertices = 4;
    List<Edge> minimumSpanningTree = kruskalMST(edges, vertices);

    System.out.println("Edges in the Minimum Spanning Tree:");
    for (Edge edge : minimumSpanningTree) {
      System.out.println(edge.source + " - " + edge.destination + " : " + edge.weight);
    }

    PriorityQueueMinHeap priorityQueue = new PriorityQueueMinHeap();
    priorityQueue.add(new Edge(0, 1, 10));
    priorityQueue.add(new Edge(0, 2, 6));
    priorityQueue.add(new Edge(0, 3, 5));
    priorityQueue.add(new Edge(1, 3, 15));
    priorityQueue.add(new Edge(2, 3, 4));

    System.out.println("Edges removed from Priority Queue:");
    while (!priorityQueue.isEmpty()) {
      Edge minEdge = priorityQueue.removeMin();
      System.out.println(minEdge.source + " - " + minEdge.destination + " : " + minEdge.weight);
    }
  }
}
