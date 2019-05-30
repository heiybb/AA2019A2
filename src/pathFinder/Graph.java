package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.ArrayList;
import java.util.HashMap;

class Graph {
    ArrayList<Coordinate> nodeList;
    HashMap<Coordinate, ArrayList<Coordinate>> adjMap;

    Graph(PathMap pathMap) {
        nodeList = new ArrayList<>();
        adjMap = new HashMap<>();

        for (int i = 0; i < pathMap.sizeR; i++) {
            for (int j = 0; j < pathMap.sizeC; j++) {
                Coordinate tmp = pathMap.cells[i][j];
                if (!tmp.getImpassable()) {
                    nodeList.add(tmp);
                    //initialize the empty adjMap LinkedList for every vertex
                    adjMap.put(tmp, new ArrayList<>());
                }
            }
        }

        adjMap.forEach((coo, adjList) -> {
            int row = coo.getRow();
            int column = coo.getColumn();

            //The adjacent down Coordinate
            Coordinate down = new Coordinate(row - 1, column);
            if (nodeList.contains(down)) {
                adjList.add(down);
            }

            //The adjacent top Coordinate
            Coordinate top = new Coordinate(row + 1, column);
            if (nodeList.contains(top)) {
                adjList.add(top);
            }

            //The adjacent left Coordinate
            Coordinate left = new Coordinate(row, column - 1);
            if (nodeList.contains(left)) {
                adjList.add(left);
            }

            //The adjacent right Coordinate
            Coordinate right = new Coordinate(row, column + 1);
            if (nodeList.contains(right)) {
                adjList.add(right);
            }
        });
    }
}