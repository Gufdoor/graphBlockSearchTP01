import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * Graph practice lesson n01 - PUC MG
 * @author Daniel Lucas Murta
 * @author Gabriel Luna dos Anjos
 * 
 * Managing blocks search on graphs.
 */
public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Insert nodes amount: ");
        int nodeAmount = input.nextInt();

        while (nodeAmount <= 0) {
            System.out.print("Number of nodes must be positive. Insert nodes amount: ");
            nodeAmount = input.nextInt();
        }

        System.out.print("Insert edges probability (value must be in 0 < x <= 1): ");
        float probability = input.nextFloat();

        while (probability <= 0 || probability > 1) {
            System.out.print("Probability must be in 0 < x <= 1. Insert edges probability: ");
            probability = input.nextFloat();
        }

        input.close();

        final List<Node> graph = GraphHelper.generateRandom(nodeAmount, probability);

        // Handle method 1
        DisjointPaths test = new DisjointPaths(graph);
        test.checkDisjunction();

        // Handle method 2
        Articulation articulation = new Articulation(graph);
        Set<Integer> articulations = articulation.searchArticulations();
        articulation.printBlocks(articulations);
    }
}
