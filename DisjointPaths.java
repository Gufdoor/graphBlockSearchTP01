import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * 
 * Public class for graph disjoint paths methods and attributes operations.
 */
public class DisjointPaths {
    List<Node> graph;

    /**
     * 
     * @param graph list of nodes contained on a graph
     */
    public DisjointPaths(List<Node> graph) {
        this.graph = graph;
    }

    /**
     * 
     * Method which verify if there are disjoint paths or cycles in a graph.
     * It constructs an adjacency matrix based on the graph and then iterates
     * through each pair of vertices to check if they have a common edge.
     */
    public void checkDisjunction() {
        int nodesAmount = graph.size();
        boolean[] visited = new boolean[nodesAmount];
        boolean[][] adjacencies = new boolean[nodesAmount][nodesAmount];

        // Builds adjacency matrix
        for (int i = 0; i < nodesAmount; i++) {
            for (int x = 0; x < graph.get(i).adjacencies.size(); x++) {
                int j = graph.get(i).adjacencies.get(x);
                adjacencies[i][j] = true;
                adjacencies[j][i] = true;
            }
        }

        boolean hasDisjunction = false;
        LinkedHashSet<Integer> nodes = new LinkedHashSet<Integer>();

        for (int i = 0; i < nodesAmount; i++) {
            for (int j = i + 1; j < nodesAmount; j++) {
                if (adjacencies[i][j]) { // Checks if there is a path between i and j
                    if (hasTwoDisjunctions(adjacencies, visited, i, j)) {
                        nodes.add(i);
                        hasDisjunction = true;
                    }

                    if (hasCycle(adjacencies, visited, i, j, -1)) {
                        nodes.add(i);
                        hasDisjunction = true;
                    }
                }
            }
        }

        if (hasDisjunction) {
            System.out.println("Method 01 -> Disjoint paths: " + nodes);
        } else {
            System.out.println("Method 01 ->  No cycles or disjoint paths found.");
        }
    }

    /**
     * 
     * This method is used in the context of checking for internally disjoint
     * paths in a graph. If all vertices on the path are visited, then the
     * paths are internally disjoint, and the method returns true. Otherwise,
     * it returns false.
     * 
     * @param adjacencies matrix of adjacencies on a graph
     * @param visited     list of visited nodes
     * @param u           node one
     * @param v           node two
     * @return true in case there's disjunction. Otherwise, false
     */
    private boolean hasTwoDisjunctions(boolean[][] adjacencies, boolean[] visited, int u, int v) {
        Arrays.fill(visited, false);
        depthFirstSearch(adjacencies, visited, u, v);

        if (!visited[v]) {
            return false;
        }

        // Executes DFS, marking every visited node
        Arrays.fill(visited, false);
        depthFirstSearch(adjacencies, visited, v, -1);

        // Checks if every node between u and v were visited on the second search
        for (int i = 0; i < adjacencies.length; i++) {
            if (visited[i] && adjacencies[u][i] && adjacencies[v][i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * 
     * This is a method to check if there is a cycle in a graph represented
     * by a boolean adjacency matrix. The method uses a recursive depth-first
     * search approach to visit all adjacent nodes of a given node, marking
     * each visited node in the visited array.
     * 
     * @param adjacencies matrix of adjacencies on a graph
     * @param visited     list of visited nodes
     * @param u           node one
     * @param v           node two
     * @return true in case there's a cycle. Otherwise, false
     */
    private boolean hasCycle(boolean[][] adjacencies, boolean[] visited, int u, int v, int parent) {
        visited[u] = true;

        for (int i = 0; i < adjacencies.length; i++) {
            if (adjacencies[u][i]) {
                if (!visited[i]) {
                    if (hasCycle(adjacencies, visited, i, v, u)) {
                        return true;
                    }
                } else if (i != parent && i == v) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 
     * This method executes a DFS. With it, we can obtain a graph
     * necessary data to execute other operations like to discover
     * how many articulations a graph has. It'll fulfill every
     * list attribute position with values which each index
     * corresponds to an specific node.
     * 
     * @param adjacencies matrix of adjacencies on a graph
     * @param visited     list of visited nodes
     * @param u           node one
     * @param v           node two
     */
    private void depthFirstSearch(boolean[][] adjacencies, boolean[] visited, int u, int v) {
        visited[u] = true;

        for (int i = 0; i < adjacencies.length; i++) {
            if (adjacencies[u][i] && (v == -1 || i != v)) {
                if (!visited[i]) {
                    depthFirstSearch(adjacencies, visited, i, v);
                }
            }
        }
    }
}
