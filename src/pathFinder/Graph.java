package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is used to convert to PathMap to the Graph that Dij algorithm can identify
 *
 * @author Bobin Yuan s3677943@student.rmit.edu.au
 */
class Graph {
    ArrayList<Coordinate> nodeList;
    HashMap<Coordinate, ArrayList<Coordinate>> adjMap;

    Graph(PathMap pathMap) {
        long start = System.nanoTime();
        nodeList = new ArrayList<>(pathMap.sizeC * pathMap.sizeR);
//        nodeList = new ArrayList<>();
        int initCap = (int) Math.pow(2, Math.ceil(Math.log(pathMap.sizeC * pathMap.sizeR) / Math.log(2)));
        adjMap = new HashMap<>(initCap);
//        adjMap = new HashMap<>();
        System.out.println("Assign init cap for both");

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
            if (pathMap.isPassable(row - 1, column)) {
                adjList.add(pathMap.cells[row - 1][column]);
            }

            //The adjacent top Coordinate
            if (pathMap.isPassable(row + 1, column)) {
                adjList.add(pathMap.cells[row + 1][column]);
            }

            //The adjacent left Coordinate
            if (pathMap.isPassable(row, column - 1)) {
                adjList.add(pathMap.cells[row][column - 1]);
            }

            //The adjacent right Coordinate
            if (pathMap.isPassable(row, column + 1)) {
                adjList.add(pathMap.cells[row][column + 1]);
            }
        });
        long end = System.nanoTime();
        System.out.println("Graph Convert Time Cost: " + (end - start));
    }
}