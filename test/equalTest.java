import map.Coordinate;

public class equalTest {
    public static void main(String[] args) {
        Coordinate a = new Coordinate(1,2,true);
        Coordinate b = new Coordinate(1,2,true);
        if (a.equals(b)){
            System.out.println(1);
        }
        for (int i = 0; i < 8; i++) {
            if (i==5){
                break;
            }
            System.out.println(i);
        }
    }
}
