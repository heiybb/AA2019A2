import map.Coordinate;
import map.PathMap;

import java.util.*;

public class Test {
    public Test(int i, int i1) {
    }

    public static void main(String[] args) {
        List<Coordinate> oriCells = new ArrayList<>();
        oriCells.add(new Coordinate(2, 0));

        List<Coordinate> desCells = new ArrayList<>();
        desCells.add(new Coordinate(0, 3));

        Set<Coordinate> impassableCells = new HashSet<>();
        impassableCells.add(new Coordinate(3, 2));
        impassableCells.add(new Coordinate(2, 2));
        impassableCells.add(new Coordinate(3, 3));
        impassableCells.add(new Coordinate(1, 2));
        impassableCells.add(new Coordinate(2, 3));
        impassableCells.add(new Coordinate(1, 3));
        impassableCells.add(new Coordinate(2, 4));
        impassableCells.add(new Coordinate(4, 2));

        Map<Coordinate, Integer> terrainCells = new HashMap<>();
        terrainCells.put(new Coordinate(1, 0), 12);
        terrainCells.put(new Coordinate(1, 1), 9);
        terrainCells.put(new Coordinate(0, 0), 6);
        terrainCells.put(new Coordinate(1, 2), 10);
        terrainCells.put(new Coordinate(0, 2), 8);

        List<Coordinate> waypointCells = new ArrayList<>();

        PathMap pathMap = new PathMap();
        pathMap.initMap(7, 7, oriCells, desCells, impassableCells, terrainCells, waypointCells);




    }
}
