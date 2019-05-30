//import map.Coordinate;
//
//import java.util.*;
//
//public class DPQ {
//    ArrayList<Coordinate> coordinateArrayList = new ArrayList<>();
//
//    private int dist[];
//    private Set<Coordinate> settled;
//    private PriorityQueue<Coordinate> pq;
//    private int nodesAmount;
//    HashMap<Integer, List<Coordinate>> adj;
//
//    private DPQ(int nodesAmount) {
//        this.nodesAmount = nodesAmount;
//        dist = new int[nodesAmount];
//        settled = new HashSet<>();
//        pq = new PriorityQueue<>();
//    }
//
//    private void dijkstra(HashMap<Integer, List<Coordinate>> adj, int startIndex) {
//        this.adj = adj;
//
//        for (int i = 0; i < nodesAmount; i++) {
//            dist[i] = Integer.MAX_VALUE;
//        }
//
//        // Add source node to the priority queue
//        pq.add(coordinateArrayList.get(startIndex));
//
//        // Distance to the source is 0
//        dist[startIndex] = 0;
//        while (settled.size() != nodesAmount) {
//            // remove the minimum distance node
//            // from the priority queue
//            Coordinate u = pq.remove();
//
//            // adding the node whose distance is
//            // finalized
//            settled.add(u);
//
//            e_Neighbours(u);
//        }
//    }
//
//    private void e_Neighbours(Coordinate u) {
//        int edgeDistance;
//        int newDistance;
//
//        // All the neighbors of v
//        for (Coordinate neighbour : adj.get(coordinateArrayList.indexOf(u))) {
//            // If current node hasn't already been processed
//            if (!settled.contains(neighbour)) {
//                edgeDistance = neighbour.getTerrainCost();
//                newDistance = dist[coordinateArrayList.indexOf(u)] + edgeDistance;
//
//                // If new distance is cheaper in cost
//                if (newDistance < dist[neighbour.getTerrainCost()]) {
//                    dist[neighbour.getTerrainCost()] = newDistance;
//                }
//
//                // Add the current node to the queue
//                pq.add(neighbour);
//            }
//        }
//    }
//
//    // Driver code
//    public static void main(String arg[]) {
//        int V = 5;
//        int source = 0;
//
//        // Adjacency list representation of the
//        // connected edges
//        List<List<Node>> adj = new ArrayList<>();
//
//        // Initialize list for every node
//        for (int i = 0; i < V; i++) {
//            List<Node> item = new ArrayList<>();
//            adj.add(item);
//        }
//
//        // Inputs for the DPQ graph
//        adj.get(0).add(new Node("A", 9));
//        adj.get(0).add(new Node("B", 6));
//        adj.get(0).add(new Node("C", 5));
//        adj.get(0).add(new Node("D", 3));
//
//        adj.get(2).add(new Node("A", 2));
//        adj.get(2).add(new Node("C", 4));
//
//        // Calculate the single source shortest path
//        DPQ dpq = new DPQ(V);
//        dpq.dijkstra(adj, source);
//
//        // Print the shortest path to all the nodes
//        // from the source node
//        System.out.println("The shorted path from node :");
//        for (int i = 0; i < dpq.dist.length; i++) {
//            System.out.println(source + " to " + i + " is "
//                    + dpq.dist[i]);
//        }
//    }
//}