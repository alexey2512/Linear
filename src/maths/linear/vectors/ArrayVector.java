package maths.linear.vectors;

import java.util.*;

public class ArrayVector extends AbstractVector {

    private final double[] array;
    private final int size;

    public ArrayVector() {
        this.size = 3;
        this.array = new double[3];
    }

    public ArrayVector(double[] array) {
        initError(array.length == 0, "from empty array");
        this.size = array.length;
        this.array = Arrays.copyOf(array, size);
    }

    public ArrayVector(Iterable<Double> collection) {
        List<Double> temp = new ArrayList<>();
        for (double x : collection) {
            temp.add(x);
        }
        initError(temp.isEmpty(), "from empty iterable object");
        size = temp.size();
        array = new double[size];
        for (int i = 0; i < size; i++) {
            array[i] = temp.get(i);
        }
    }

    public ArrayVector(int size) {
        initError(size <= 0, "with non positive size = " + size);
        this.size = size;
        this.array = new double[size];
    }

    public ArrayVector(int size, double value) {
        initError(size <= 0, "with non positive size = " + size);
        this.size = size;
        this.array = new double[size];
        Arrays.fill(array, value);
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
        array[index] = value;
    }

    @Override
    public double get(int index) {
        check(index);
        return array[index];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(array), size);
    }
}
