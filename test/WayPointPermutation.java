import java.util.*;

public class WayPointPermutation {
    private static List<List<Object>> allPossible;

    private WayPointPermutation(List<Object> objectList) {
        allPossible = new ArrayList<>();
        perm(objectList, 0, objectList.size() - 1);
    }

    private static void perm(List<Object> objectList, int start, int end) {
        if (start == end) {
            objectList.forEach(System.out::print);
            System.out.println();
            ArrayList<Object> temp = new ArrayList<>(objectList);
            allPossible.add(temp);
        } else {
            for (int i = start; i <= end; i++) {
                Collections.swap(objectList, start, i);
                perm(objectList, start + 1, end);
                Collections.swap(objectList, start, i);
            }
        }
    }

    private List<List<Object>> getAllPossible() {
        return allPossible;
    }

    public static void main(String[] args) {
        List<Object> A = new ArrayList<>();
        A.add("A");
        A.add("B");
        A.add("C");

        List<List<Object>> test = new WayPointPermutation(A).getAllPossible();

        test.forEach(objects -> {
            objects.forEach(e->{
                System.out.print(e);
                System.out.print(" ");
            });
            System.out.println();
        });

    }
}