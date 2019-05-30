import map.Coordinate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class XiaoHuiTest {
    public static void main(String[] args) {
        Graph graph = new Graph(7);
        initGraph(graph);
        int[] prevs = dijkstra(graph, 0);
        printPrevious(graph.vertexes, prevs, graph.vertexes.length - 1);
    }

    private static void printPrevious(Coordinate[] vertexes, int[] prev, int i) {
        if (i > 0) {
            printPrevious(vertexes, prev, prev[i]);
        }
        System.out.println(vertexes[i].toString());
    }

    private static class Graph {
        private Coordinate[] vertexes;
        private Map<Integer, LinkedList<Edge>> adj;

        Graph(int size) {
            vertexes = new Coordinate[size];
            adj = new HashMap<>(size);

            for (int i = 0; i < size; i++) {
                adj.put(i, new LinkedList<>());
            }
        }
    }

    private static class Edge {
        int index;
        int weight;

        Edge(int index, int weight) {
            this.index = index;
            this.weight = weight;
        }
    }

    public static int[] dijkstra(Graph graph, int startIndex) {
        int size = graph.vertexes.length;
        int[] distances = new int[size];
        int[] previous = new int[size];
        boolean[] access = new boolean[size];
        for (int i = 0; i < size; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        access[0] = true;
        LinkedList<Edge> edgesFromStart = graph.adj.get(startIndex);
        for (Edge edge : edgesFromStart) {
            distances[edge.index] = edge.weight;
            previous[edge.index] = 0;
        }
        for (int i = 1; i < size; i++) {
            int minDistanceFromStart = Integer.MAX_VALUE;
            int minDistanceIndex = -1;
            for (int j = 1; j < size; j++) {
                if (!access[j] && distances[j] < minDistanceFromStart) {
                    minDistanceFromStart = distances[j];
                    minDistanceIndex = j;
                }
            }
            if (minDistanceIndex == -1) {
                break;
            }
            access[minDistanceIndex] = true;
            for (Edge edge : graph.adj.get(minDistanceIndex)) {
                if (access[edge.index]) {
                    continue;
                }
                int weight = edge.weight;
                int preDistance = distances[edge.index];
                if (weight != Integer.MAX_VALUE && (minDistanceFromStart + weight < preDistance)) {
                    distances[edge.index] = minDistanceFromStart + weight;
                    previous[edge.index] = minDistanceIndex;
                }
            }
        }
        return previous;
    }

    private static void initGraph(Graph graph) {
//        graph.vertexes[0] = new Coordinate("A");
//        graph.vertexes[1] = new Coordinate("B");
//        graph.vertexes[2] = new Coordinate("C");
//        graph.vertexes[3] = new Coordinate("D");
//        graph.vertexes[4] = new Coordinate("E");
//        graph.vertexes[5] = new Coordinate("F");
//        graph.vertexes[6] = new Coordinate("G");

        graph.adj.get(0).add(new Edge(1, 5));
        graph.adj.get(0).add(new Edge(2, 2));

        graph.adj.get(1).add(new Edge(0, 5));
        graph.adj.get(1).add(new Edge(3, 1));
        graph.adj.get(1).add(new Edge(4, 6));

        graph.adj.get(2).add(new Edge(0, 2));
        graph.adj.get(2).add(new Edge(3, 6));
        graph.adj.get(2).add(new Edge(5, 8));

        graph.adj.get(3).add(new Edge(1, 1));
        graph.adj.get(3).add(new Edge(2, 6));
        graph.adj.get(3).add(new Edge(4, 1));
        graph.adj.get(3).add(new Edge(5, 2));

        graph.adj.get(4).add(new Edge(1, 6));
        graph.adj.get(4).add(new Edge(3, 1));
        graph.adj.get(4).add(new Edge(6, 7));

        graph.adj.get(5).add(new Edge(2, 8));
        graph.adj.get(5).add(new Edge(3, 2));
        graph.adj.get(5).add(new Edge(6, 3));

        graph.adj.get(6).add(new Edge(4, 7));
        graph.adj.get(6).add(new Edge(5, 3));
    }
}
