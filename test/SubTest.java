import java.util.ArrayList;

public class SubTest {
    public static void main(String[] args) {
        ArrayList<String> test = new ArrayList<>();
        test.add("A");
        test.add("B");
        test.add("C");
        test.add("D");
        test.add("E");
        ArrayList<String> t2 = new ArrayList<>(test.subList(1, test.size()));
        for (String e:t2
             ) {
            System.out.println(e);

        }

    }
}
