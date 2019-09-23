import java.util.ArrayList;
import java.util.HashMap;

public class HashCapacityTest {
    public static void main(String[] args) {
        ArrayList<TT> tList = new ArrayList<>(200000);
        for (int i = 1; i < 200000; i++) {
            tList.add(new TT(i, i + 1));
        }

        long nullIntStart = System.nanoTime();
        HashMap<TT, Integer> nullIntCap = new HashMap<>();
        tList.forEach(tt -> {
            nullIntCap.put(tt, 0);
        });
        System.out.println(System.nanoTime() - nullIntStart);

        long IntStart = System.nanoTime();
        HashMap<TT, Integer> IntCap = new HashMap<>(200000);
        tList.forEach(tt -> {
            IntCap.put(tt, 0);
        });
        System.out.println(System.nanoTime() - IntStart);

    }

    private static class TT {
        int start;
        int end;

        TT(int s, int e) {
            this.start = s;
            this.end = e;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TT test = (TT) o;

            if (start != test.start) return false;
            return end == test.end;
        }

        @Override
        public int hashCode() {
            int result = start;
            result = 31 * result + end;
            return result;
        }
    }
}


