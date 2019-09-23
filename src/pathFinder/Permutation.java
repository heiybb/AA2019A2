package pathFinder;

import map.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Generate the Way Points Coordinate permutation for Task D
 *
 * @author Bobin Yuan s3677943@student.rmit.edu.au
 */
public class Permutation {
    private List<List<Coordinate>> allPossible;

    public Permutation(List<Coordinate> wayPointList) {
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

    /**
     * @return return all the possible mix
     */
    public List<List<Coordinate>> getAll() {
        return allPossible;
    }
}