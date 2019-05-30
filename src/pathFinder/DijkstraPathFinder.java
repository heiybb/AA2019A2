package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.io.Serializable;
import java.util.*;

/**
 * @author Bobin Yuan s367794@student.rmit.edu.au
 */
public class DijkstraPathFinder implements PathFinder {

    private List<List<Coordinate>> pathRecordList;

    public DijkstraPathFinder(PathMap pathMap) {
        this.pathRecordList = new ArrayList<>();

        Graph graph = new Graph(pathMap);
        ArrayList<Coordinate> nodeList = graph.nodeList;
        HashMap<Coordinate, ArrayList<Coordinate>> adjMap = graph.adjMap;

        List<List<Coordinate>> allPassNodes = new ArrayList<>();


        //TODO Re-order the for-loop to boost
        for (Coordinate origin : pathMap.originCells) {
            if (pathMap.waypointCells.isEmpty()) {
                for (Coordinate dest : pathMap.destCells) {
                    List<Coordinate> tmp = new ArrayList<>(Arrays.asList(origin, dest));
                    allPassNodes.add(tmp);
                }
            } else {
                List<List<Coordinate>> wayPointPossible = new WayPointPermutation(pathMap.waypointCells).getAllPossible();
                for (List<Coordinate> wpp : wayPointPossible) {
                    for (Coordinate dest : pathMap.destCells) {
                        List<Coordinate> tmp = new ArrayList<>();
                        tmp.add(origin);
                        tmp.addAll(wpp);
                        tmp.add(dest);
                        allPassNodes.add(tmp);
                    }
                }
            }
        }

        for (List<Coordinate> passList : allPassNodes) {
            //TODO OPTIMIZE
            // FOR THOSE WHICH HAVE THE SAME START/SOURCE NODE/POINT CAN SHARE THE SAME PREVIOUS LIST
            ArrayList<Coordinate> combinedPath = new ArrayList<>();
            for (int i = 1; i < passList.size(); i++) {
                System.out.println();
                System.out.println("This turn slice path:");

                System.out.println("Start Node:");
                Coordinate startNode = passList.get(i - 1);
                System.out.println(startNode);

                System.out.println("End Node:");
                Coordinate endNode = passList.get(i);
                System.out.println(endNode);

                ArrayList<Coordinate> slicePath = dijkstra(nodeList, adjMap, startNode, endNode);

                //remove the first to combine with others
                if (i != 1) {
                    combinedPath.addAll(slicePath.subList(1, slicePath.size()));
                } else {
                    combinedPath.addAll(slicePath);
                }
            }
            pathRecordList.add(combinedPath);
        }
        System.out.println(pathRecordList.size());
    }


    /**
     * Find the shortest path from the start node to all other nodes
     * Return the array which store index of node which is previously visited
     *
     * @param startNode the source index of the original start node
     * @return the array which store index of node which is previously visited
     */
    private ArrayList<Coordinate> dijkstra(ArrayList<Coordinate> nodeList, HashMap<Coordinate,
            ArrayList<Coordinate>> adjMap, Coordinate startNode, Coordinate endNode) {

        long startTime = System.nanoTime();
        //Swap the endNode with the last node of the nodeList
        Collections.swap(nodeList, nodeList.indexOf(endNode), nodeList.size() - 1);
        //Swap the startNode with the first node of the nodeList
        Collections.swap(nodeList, nodeList.indexOf(startNode), 0);

        int size = nodeList.size();
        int[] distances = new int[size];
        int[] previous = new int[size];
        boolean[] visited = new boolean[size];

        //Set all the un-visited node point's distance(From start node) as INFINITE
        for (int i = 0; i < size; i++) {
            distances[i] = Integer.MAX_VALUE;
        }

        //Set the source/start node visited field as True
        visited[0] = true;

        ArrayList<Coordinate> adjList = adjMap.get(startNode);
        for (Coordinate coo : adjList) {
            int cooIndex = nodeList.indexOf(coo);
            distances[cooIndex] = coo.getTerrainCost();
            previous[cooIndex] = 0;
        }

        for (int i = 1; i < size; i++) {
            int minDistanceFromStart = Integer.MAX_VALUE;
            int minDistanceIndex = -1;
            for (int j = 1; j < size; j++) {
                if (!visited[j] && distances[j] < minDistanceFromStart) {
                    minDistanceFromStart = distances[j];
                    minDistanceIndex = j;
                }
            }
            if (minDistanceIndex == -1) {
                break;
            }
            visited[minDistanceIndex] = true;
            Coordinate minDistanceNode = nodeList.get(minDistanceIndex);

            for (Coordinate coo : adjMap.get(minDistanceNode)) {
                int cooIndex = nodeList.indexOf(coo);
                if (visited[cooIndex]) {
                    continue;
                }
                int cost = coo.getTerrainCost();
                int preDistance = distances[cooIndex];

                if (cost != Integer.MAX_VALUE && (minDistanceFromStart + cost < preDistance)) {
                    distances[cooIndex] = minDistanceFromStart + cost;
                    previous[cooIndex] = minDistanceIndex;
                }
            }
        }
        ArrayList<Coordinate> pathRecord = new ArrayList<>();
        getPreviousRecord(pathRecord, nodeList, previous, nodeList.indexOf(endNode));

        long endTime = System.nanoTime();
        long cost = (endTime - startTime);
        System.out.println("Time Cost: " + cost + " nano sec");

        return pathRecord;
    }


    /**
     * Recursive get the previous node of target node by going through the shortest path
     *
     * @param record      Store the path (From start node to the end node)
     * @param nodeList    The List which store all the nodes
     * @param prev        The array store the previous node index
     * @param targetIndex target node's index in the nodeList (usually be the last index of the nodeList)
     */
    private void getPreviousRecord(ArrayList<Coordinate> record, List<Coordinate> nodeList, int[] prev, int targetIndex) {
        //TODO Use HashMap to store the previous node table to boost the algorithm
        if (targetIndex > 0) {
            getPreviousRecord(record, nodeList, prev, prev[targetIndex]);
        }
        record.add(nodeList.get(targetIndex));
    }


    @Override
    public List<Coordinate> findPath() {
        return shortest(pathRecordList);
    }


    @Override
    public int coordinatesExplored() {
        return 0;
    }


    /**
     * Sort all paths and return the shortest one
     *
     * @param allPath All the possible paths
     * @return the shortest Path
     */
    private List<Coordinate> shortest(List<List<Coordinate>> allPath) {
        //sort use the ascending order by default
        //so that the Path of index 0 should be the shortest one
        allPath.sort(new CoordinateListComparator<>());
        return allPath.get(0);
    }


    /**
     * Costumed Comparator to compare the sum cost of the List<Coordinate>
     *
     * @param <T> Type should be Coordinate
     */
    private static class CoordinateListComparator<T extends Coordinate> implements Comparator<List<T>>, Serializable {
        @Override
        public int compare(List<T> path1, List<T> path2) {

            int sumCost1 = path1
                    .stream()
                    .map(T::getTerrainCost)
                    .mapToInt(Integer::intValue)
                    .sum();

            int sumCost2 = path2
                    .stream()
                    .map(T::getTerrainCost)
                    .mapToInt(Integer::intValue)
                    .sum();

            return Integer.compare(sumCost1, sumCost2);
        }
    }
}