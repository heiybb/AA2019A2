import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectPermutation {
    private List<List<Object>> allPossible;

    private ObjectPermutation(List<Object> objectList) {
        allPossible = new ArrayList<>();
        perm(objectList, 0, objectList.size() - 1);
    }

    private void perm(List<Object> objectList, int start, int end) {
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
        A.add("D");
        A.add("E");
        A.add("F");
        A.add("G");

        List<List<Object>> test = new ObjectPermutation(A).getAllPossible();

        test.forEach(objects -> {
            objects.forEach(e->{
                System.out.print(e);
                System.out.print(" ");
            });
            System.out.println();
        });
        System.out.println(test.size());

    }
}