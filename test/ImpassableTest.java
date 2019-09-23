import java.util.ArrayList;
import java.util.stream.Collectors;

public class ImpassableTest {
    public static void main(String[] args) {
        ArrayList<YinYue> test = new ArrayList<>();
        test.add(new YinYue(1, true));
        test.add(new YinYue(2, false));
        test.add(new YinYue(3, false));
        test.add(new YinYue(4, true));
        test.add(new YinYue(5, true));

        System.out.println(test.stream().filter(x -> !x.pass).collect(Collectors.toList()).size());
    }

    static class YinYue {
        private int name;
        private boolean pass;

        YinYue(int name, boolean pass) {
            this.name = name;
            this.pass = pass;
        }
    }
}
