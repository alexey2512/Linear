import java.util.*;

public class Main {

    public static void main(String[] args) {
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
        List<List<List<Double>>> lst = new ArrayList<>();
        List<List<Double>> t1 = new ArrayList<>();
        t1.add(List.of(1d, 2d, 3d));
        List<List<Double>> t2 = new ArrayList<>();
        t2.add(List.of(4d, 5d, 6d));
        lst.add(t1);
        lst.add(t2);
    }
}
