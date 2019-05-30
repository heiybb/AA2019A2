package pathFinder;

import map.Coordinate;

import java.util.*;

class WayPointPermutation {
    private List<List<Coordinate>> allPossible;

    WayPointPermutation(List<Coordinate> wayPointList) {
        this.allPossible = new ArrayList<>();
        perm(wayPointList, 0, wayPointList.size() - 1);
    }

    private void perm(List<Coordinate> wayPointList, int start, int end) {
        if (start == end) {
            List<Coordinate> temp = new ArrayList<>(wayPointList);
            allPossible.add(temp);
        } else {
            for (int i = start; i <= end; i++) {
                Collections.swap(wayPointList, start, i);
                perm(wayPointList, start + 1, end);
                Collections.swap(wayPointList, start, i);
            }
        }
    }

    List<List<Coordinate>> getAllPossible() {
        return allPossible;
    }
}