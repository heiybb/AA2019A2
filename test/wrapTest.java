import map.Coordinate;

import java.util.LinkedList;
import java.util.List;

public class wrapTest {
    public static void main(String[] args) {
        List<Coordinate> oriCells = new LinkedList<>();
        List<Coordinate> desCells = new LinkedList<>();
        List<Coordinate> wayPointCells = new LinkedList<>();
        oriCells.add(new Coordinate(1, 2, true));
        oriCells.add(new Coordinate(2, 2, true));
        oriCells.add(new Coordinate(3, 2, true));

        desCells.add(new Coordinate(5, 6, true));
        desCells.add(new Coordinate(8, 4, true));
        desCells.add(new Coordinate(1, 6, true));

        List<List<Coordinate>> allCheckWay = new LinkedList<>();
        for (Coordinate origin : oriCells) {
            if (wayPointCells.isEmpty()) {

                for (Coordinate dest : desCells) {
                    List<Coordinate> tmp = new LinkedList<>();
                    tmp.add(origin);
                    tmp.add(dest);
                    allCheckWay.add(tmp);
                }
            } else {
                for (Coordinate wayPoint : wayPointCells) {
                    for (Coordinate dest : desCells) {
                        List<Coordinate> tmp = new LinkedList<>();
                        tmp.add(origin);
                        tmp.add(wayPoint);
                        tmp.add(dest);
                        allCheckWay.add(tmp);
                    }
                }
            }

        }
        System.out.println(1);

    }

}
