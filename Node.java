import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Node class created to manage a node id and 'tis adjacencies.
 */
class Node {
    public int id;
    public List<Integer> adjacencies;

    public Node() {
        this.id = -1;
        this.adjacencies = new ArrayList<Integer>();
    }

    /**
     * 
     * @param id has node id, which is equivalent to 'tis label.
     */
    public Node(int id) {
        this.id = id;
        this.adjacencies = new ArrayList<Integer>();
    }
}
