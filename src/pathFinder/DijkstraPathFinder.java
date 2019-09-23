package pathFinder;

import map.Coordinate;
import map.PathMap;

import java.io.Serializable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * @author Bobin Yuan s367794@student.rmit.edu.au
 */
public class DijkstraPathFinder implements PathFinder {

    private List<List<Coordinate>> pathRecordList;
    private ConcurrentHashMap<PreCooKey, int[]> predecessorMap;
    private long startTime;
    private long endTime;

    public DijkstraPathFinder(PathMap pathMap) {
        System.out.println("Start to find path");
        startTime = System.nanoTime();
        this.pathRecordList = new ArrayList<>();
        this.predecessorMap = new ConcurrentHashMap<>();

        /*
         * Filter those impassable Coordinates to avoid the scenario that file input is incorrect
         * This filter may cause the <Strong>list size to 0</Strong>
         */
        List<Coordinate> startNodeList = pathMap.originCells.stream()
                .filter(x -> !x.getImpassable())
                .collect(Collectors.toList());

        List<Coordinate> endNodeList = pathMap.destCells.stream()
                .filter(x -> !x.getImpassable())
                .collect(Collectors.toList());

        List<Coordinate> wayPointList = pathMap.waypointCells.stream()
                .filter(x -> !x.getImpassable())
                .collect(Collectors.toList());

        Graph graph = new Graph(pathMap);
        ArrayList<Coordinate> nodeList = graph.nodeList;
        HashMap<Coordinate, ArrayList<Coordinate>> adjMap = graph.adjMap;

        //This list contains all the node points that must pass/go through
        List<List<Coordinate>> allPassNodes = new ArrayList<>();

        //Combine the Start WayPoint End Node together
        //When startNodeList or endNodeList size is 0 the allPassNodes's size is also 0
        for (Coordinate start : startNodeList) {
            for (Coordinate end : endNodeList) {
                if (!wayPointList.isEmpty()) {
                    List<List<Coordinate>> wayPointPossible = new Permutation(wayPointList).getAll();
                    for (List<Coordinate> wpp : wayPointPossible) {
                        List<Coordinate> tmp = new ArrayList<>(Arrays.asList(start, end));
                        //Add the permutation between the Start Node & End Node
                        tmp.addAll(1, wpp);
                        allPassNodes.add(tmp);
                    }
                } else {
                    List<Coordinate> tmp = new ArrayList<>(Arrays.asList(start, end));
                    allPassNodes.add(tmp);
                }
            }
        }

//        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);

        //If allPassNodes's size is 0 then the pathRecord size is 0 so that path not found
        if (allPassNodes.size() != 0) {
            int limit = 1;
            for (List<Coordinate> passList : allPassNodes) {
                System.out.println(String.format("Remaining Possible Paths To Calculate: %s", allPassNodes.size() - limit++));

//                fixedThreadPool.execute(() -> {
                    ArrayList<Coordinate> combinedPath = new ArrayList<>();
                    for (int i = 1; i < passList.size(); i++) {
                        Coordinate startNode = passList.get(i - 1);
                        Coordinate endNode = passList.get(i);

                        if (adjMap.get(startNode).contains(endNode)) {
                            if (i == 1) {
                                combinedPath.add(startNode);
                            }
                            combinedPath.add(endNode);
                        } else {
                            //Potential HashMap concurrent issue
                            ArrayList<Coordinate> slicePath = dijkstra(nodeList, adjMap, startNode, endNode);

                            //Any slice path size equals to 0 will cause an unavailable path
                            if (slicePath.size() == 0) {
                                combinedPath.clear();
                                break;
                            }

                            //remove the first to combine with others
                            if (i != 1) {
                                combinedPath.addAll(slicePath.subList(1, slicePath.size()));
                            } else {
                                combinedPath.addAll(slicePath);
                            }
                        }

                    }

                    //Avoid the combinedPath size 0 issue
                    if (combinedPath.size() != 0) {
                        pathRecordList.add(combinedPath);
                    }
//                });

            }
            while (pathRecordList.size() != allPassNodes.size()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {
                }
            }
            System.out.println("Potential Paths Amount: " + pathRecordList.size());
        }
    }


    /**
     * Find the shortest path from the start node to all other nodes
     * Return the array which store index of node which is previously visited
     *
     * @param startNode the Start Node
     * @param endNode   the End Node
     * @return the array which store index of node which is previously visited
     */
    private ArrayList<Coordinate> dijkstra(ArrayList<Coordinate> nodeList, HashMap<Coordinate,
            ArrayList<Coordinate>> adjMap, Coordinate startNode, Coordinate endNode) {

        //Create a new List instance to avoid the Collections.swap cause Thread Exception
        ArrayList<Coordinate> syncList = new ArrayList<>(nodeList);

        //Swap the endNode with the last node of the nodeList
        Collections.swap(syncList, syncList.indexOf(endNode), syncList.size() - 1);
        //Swap the startNode with the first node of the nodeList
        Collections.swap(syncList, syncList.indexOf(startNode), 0);

        int size = syncList.size();
        int[] distances = new int[size];
        int[] predecessor = new int[size];
        boolean[] visited = new boolean[size];

        ArrayList<Coordinate> pathRecord = new ArrayList<>();

        PreCooKey preCooKey = new PreCooKey(startNode, endNode);

        //Check if the predecessor is already calculated before
        if (predecessorMap.containsKey(preCooKey)) {
            predecessor = predecessorMap.get(preCooKey);
            iteratePredecessor(pathRecord, syncList, predecessor, syncList.indexOf(endNode));
        } else {
            //Set all the un-visited Node's distance(From start node) as INFINITE
            for (int i = 0; i < size; i++) {
                distances[i] = Integer.MAX_VALUE;
            }

            //Set the Start Node is visited
            visited[0] = true;

            //Set Start Node's neighbour Node's distance(to Start Node) using their Terrain Cost
            //Set Start Node as these neighbour Nodes' predecessor
            ArrayList<Coordinate> adjList = adjMap.get(startNode);
            for (Coordinate coo : adjList) {
                int cooIndex = syncList.indexOf(coo);
                distances[cooIndex] = coo.getTerrainCost();
                predecessor[cooIndex] = 0;
            }

            for (int i = 1; i < size; i++) {
                int minDistanceFromStart = Integer.MAX_VALUE;
                int minDistanceIndex = -1;
                //Find the min distance Node from the rest of Nodes
                for (int j = 1; j < size; j++) {
                    if (!visited[j] && distances[j] < minDistanceFromStart) {
                        minDistanceFromStart = distances[j];
                        minDistanceIndex = j;
                    }
                }
                if (minDistanceIndex == -1) {
                    //Reach the end
                    break;
                }

                visited[minDistanceIndex] = true;
                Coordinate minDistanceNode = syncList.get(minDistanceIndex);

                //Update the Node's new distance to the Start Node
                for (Coordinate coo : adjMap.get(minDistanceNode)) {
                    int cooIndex = syncList.indexOf(coo);
                    if (visited[cooIndex]) {
                        continue;
                    }
                    int cost = coo.getTerrainCost();
                    int preDistance = distances[cooIndex];

                    if (cost != Integer.MAX_VALUE && (minDistanceFromStart + cost < preDistance)) {
                        distances[cooIndex] = minDistanceFromStart + cost;
                        predecessor[cooIndex] = minDistanceIndex;
                    }
                }
            }

            predecessorMap.put(preCooKey, predecessor);

            //If an End Node is surrounded by impassable Node
            //The predecessor index will be 0
            if (!adjMap.get(startNode).contains(endNode) && predecessor[syncList.indexOf(endNode)] == 0) {
                pathRecord = new ArrayList<>();
            } else {
                iteratePredecessor(pathRecord, syncList, predecessor, syncList.indexOf(endNode));
            }

        }

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
    private void iteratePredecessor(ArrayList<Coordinate> record, List<Coordinate> nodeList, int[] prev, int targetIndex) {
        if (targetIndex > 0) {
            iteratePredecessor(record, nodeList, prev, prev[targetIndex]);
        }
        record.add(nodeList.get(targetIndex));
    }


    @Override
    public List<Coordinate> findPath() {
        this.endTime = System.nanoTime();
        System.out.println(String.format("Time cost: %s", endTime - startTime));
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
        List<Coordinate> shortest = new ArrayList<>();

        if (allPath.size() > 0) {
            allPath.sort(new CoordinateListComparator<>());
            shortest = allPath.get(0);
            System.out.println("Path Cost " + shortest.stream().map(Coordinate::getTerrainCost).mapToInt(Integer::intValue).sum());
        }

        return shortest;
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

    /**
     * This class contains the Start Node and End Node
     * Used in the predecessorMap as its Key
     * In order to determinate whether the predecessor is already calculated before
     * For performance improvement and save the CPU time cost
     */
    private static class PreCooKey {
        Coordinate start;
        Coordinate end;

        PreCooKey(Coordinate start, Coordinate end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            PreCooKey preCooKey = (PreCooKey) o;

            if (!Objects.equals(start, preCooKey.start)) {
                return false;
            }
            return Objects.equals(end, preCooKey.end);
        }

        @Override
        public int hashCode() {
            int result = start != null ? start.hashCode() : 0;
            result = 31 * result + (end != null ? end.hashCode() : 0);
            return result;
        }
    }
}