package maths.linear;

import java.util.*;

public class ListVector extends AbstractVector {

    private final List<Double> list;
    private final int size;

    public ListVector() {
        this.size = 3;
        this.list = List.of(0d, 0d, 0d);
    }

    public ListVector(double[] array) {
        initError(array.length == 0, "from empty array");
        this.size = array.length;
        list = Arrays.asList(Arrays.stream(array).boxed().toArray(Double[]::new));
    }

    public ListVector(Iterable<Double> collection) {
        list = new ArrayList<>();
        for (double x : collection) {
            list.add(x);
        }
        initError(list.isEmpty(), "from empty iterable object");
        size = list.size();
    }

    public ListVector(int size) {
        initError(size <= 0, "with non positive size = " + size);
        this.size = size;
        list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(0d);
        }
    }

    public ListVector(int size, double value) {
        initError(size <= 0, "with non positive size = " + size);
        this.size = size;
        this.list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(value);
        }
    }

    private static void error(String message) {
        throw new IllegalArgumentException(message);
    }

    private static void initError(boolean condition, String message) {
        if (condition) {
            error("can not create vector " + message);
        }
    }

    private void check(int index) {
        if (index < 0 || index >= size) {
            error("index = " + index + " out of bounds for size = " + size);
        }
    }

    @Override
    public void set(int index, double value) {
        check(index);
        list.set(index, value);
    }

    @Override
    public double get(int index) {
        check(index);
        return list.get(index);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, size);
    }

}
