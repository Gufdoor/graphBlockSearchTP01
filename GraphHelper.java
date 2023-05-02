import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * Abstract class for graph helper methods.
 * Created to handle with situations were a problem is generic
 * for any graph purposes.
 */
abstract class GraphHelper {

    /**
     * 
     * This method uses ErdosRenyi model to build a random graph.
     * This implementation also guarantee how much dense the graph
     * can be.
     * 
     * @param nodesAmount amount of nodes intended for the random graph
     * @param probability factor for how much conected the graph should be
     * @return a list of nodes which is a graph itself
     */
    static List<Node> generateRandom(int nodesAmount, float probability) {
        final List<Node> graph = new ArrayList<Node>();
        double rnd = Math.random();

        for (int i = 0; i < nodesAmount; i++) {
            Node node = new Node(i);

            for (int j = 0; j < nodesAmount; j++) {
                if (j != i && rnd <= probability) {
                    node.adjacencies.add(j);
                }

                rnd = Math.random();
            }

            graph.add(node);
        }

        return graph;
    }

    /**
     * 
     * This method executes a Breadth-First Search (BFS) to check if a root
     * of a graph can reach it all, returning if it's connected or not.
     * 
     * @param graph list of nodes contained on a graph
     * @return return a boolean, indicating if a graph is connected or not
     */
    static boolean isConnected(List<Node> graph) {
        final int nodeAmount = graph.size();

        // If there isn't any node, 'tis a connected graph
        if (nodeAmount == 0) {
            return true;
        }

        boolean[] marked = new boolean[nodeAmount];
        Queue<Integer> queue = new LinkedList<>();
        int root = 0;
        int count = 1;

        marked[root] = true;
        queue.offer(root);

        // Visits every possible node from the root. 
        // If 'tis empty, it finished visiting all nodes that was reachable.
        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (int nodeAdj : graph.get(node).adjacencies) {
                if (nodeAdj < graph.get(node).id && !marked[nodeAdj]) {
                    marked[nodeAdj] = true;
                    queue.offer(nodeAdj);
                    count++;
                }
            }
        }

        return count == nodeAmount;
    }
}