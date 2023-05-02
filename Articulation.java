import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * Public class for graph articulation related methods and attributes.
 */
public class Articulation {
    private int time;
    private int[] low;
    private int[] discovery;
    private boolean[] visited;
    private boolean[] areArticulations;
    private List<Node> graph;

    /**
     * This constructor initializes class attributes and calls for 
     * a DFS search right after to populate the class with data for
     * any other necessary operation.
     * 
     * @param graph list of nodes contained on a graph
     */
    public Articulation(List<Node> graph) {
        final int nodeAmount = graph.size();

        this.time = 0;
        this.graph = graph;
        this.low = new int[nodeAmount];
        this.discovery = new int[nodeAmount];
        this.visited = new boolean[nodeAmount];
        this.areArticulations = new boolean[nodeAmount];

        handleDepthFirstSearch(nodeAmount);
    }

    /**
     * This method is only responsible for handling DFS calls and 
     * to guarantee visited list isn't empty.
     * 
     * @param nodeAmount node amount of a graph
     */
    private void handleDepthFirstSearch(int nodeAmount) {
        for (int i = 0; i < nodeAmount; i++) {
            visited[i] = false;
        }

        for (int i = 0; i < nodeAmount; i++) {
            if (!visited[i]) {
                depthFirstSearch(graph.get(i), i);
            }
        }
    }


    /**
     * This method executes a DFS. With it, we can obtain a graph
     * necessary data to execute other operations like to discover
     * how many articulations a graph has. It'll fulfill every 
     * list attribute position with values which each index
     * corresponds to an specific node.
     * 
     * @param node node to be analized
     * @param parent node's parent to be analized
     */
    private void depthFirstSearch(Node node, int parent) {
        visited[node.id] = true;
        discovery[node.id] = low[node.id] = ++time;
        int children = 0;

        for (int nodeAdj : node.adjacencies) {
            if (!visited[nodeAdj]) {
                children++;
                depthFirstSearch(graph.get(nodeAdj), node.id);
                low[node.id] = Math.min(low[node.id], low[nodeAdj]);

                if (discovery[node.id] <= low[nodeAdj] && parent != -1) {
                    areArticulations[node.id] = true;
                }
            } else if (nodeAdj != parent) {
                low[node.id] = Math.min(low[node.id], discovery[nodeAdj]);
            }
        }

        if (parent == -1 && children > 1) {
            areArticulations[node.id] = true;
        }
    }

    /**
     * 
     * @return a list of nodes ids that are articulations
     */
    public Set<Integer> searchArticulations() {
        Set<Integer> articulations = new HashSet<>();

        for (int i = 0; i < graph.size(); i++) {
            if (areArticulations[i]) {
                graph.remove(i);

                if (!GraphHelper.isConnected(graph)) {
                    articulations.add(i);
                }
            }
        }
        
        return articulations;
    }

    public void printBlocks(Set<Integer> articulations) {
        System.out.println("Method 02 -> Blocks amount: " + articulations.size());
        System.out.print("Method 02 -> Blocks: " + articulations);
    }
}