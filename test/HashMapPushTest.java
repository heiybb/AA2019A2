import java.util.HashMap;

public class HashMapPushTest {
    public static void main(String[] args) {
        HashMap<Integer,Integer> test = new HashMap<>();
        test.put(1,2);
        test.replace(1,3);
        test.replace(2,3);
        System.out.println(test.get(1));
        System.out.println(test.get(2));

    }
}
