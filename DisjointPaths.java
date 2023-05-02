import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

public class DisjointPaths {
    List<Node> graph;

    public DisjointPaths(List<Node> graph) {
        this.graph = graph;
    }

    public void checkDisjunction() {
        int nodesAmount = graph.size();
        boolean[] visited = new boolean[nodesAmount];
        boolean[][] adjacencies = new boolean[nodesAmount][nodesAmount];

        // constrói a matriz de adjacência a partir do bloco
        for (int i = 0; i < nodesAmount; i++) {
            for (int x = 0; x < graph.get(i).adjacencies.size(); x++) {
                int j = graph.get(i).adjacencies.get(x);
                adjacencies[i][j] = true;
                adjacencies[j][i] = true;
            }
        }

        boolean hasDisjunction = false;
        // verifica caminhos entre cada par de vértices
        LinkedHashSet<Integer> nodes = new LinkedHashSet<Integer>();

        for (int i = 0; i < nodesAmount; i++) {
            for (int j = i + 1; j < nodesAmount; j++) {
                if (adjacencies[i][j]) { // existe aresta entre i e j
                    // verifica se existem dois caminhos internamente disjuntos
                    if (hasTwoDisjunctions(adjacencies, visited, i, j)) {
                        nodes.add(i);
                        hasDisjunction = true;
                    }
                    // verifica se existe um ciclo
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

    private boolean hasTwoDisjunctions(boolean[][] adjacencies, boolean[] visited, int u, int v) {
        Arrays.fill(visited, false);

        // busca em profundidade do vértice u
        depthFirstSearch(adjacencies, visited, u, v);

        if (!visited[v]) {
            // se v não foi visitado na primeira busca, não há caminho de u a v
            return false;
        }

        // busca em profundidade a partir de v, marcando todos os vértices visitados
        Arrays.fill(visited, false);
        depthFirstSearch(adjacencies, visited, v, -1);

        // verifica se todos os vértices do caminho u-v foram visitados na segunda busca
        for (int i = 0; i < adjacencies.length; i++) {
            if (visited[i] && adjacencies[u][i] && adjacencies[v][i]) {
                return false; // caminhos não são disjuntos
            }
        }

        return true; // caminhos são disjuntos
    }

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
