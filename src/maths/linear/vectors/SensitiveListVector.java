package maths.linear.vectors;

import maths.linear.vectors.AbstractVector;

import java.util.List;

public class SensitiveListVector extends AbstractVector {

    private final List<Double> list;
    private final int size;

    public SensitiveListVector(List<Double> list) {
        if (list.isEmpty()) {
            error("can not create vector from empty array");
        }
        this.list = list;
        this.size = list.size();
    }

    private static void error(String message) {
        throw new IllegalArgumentException(message);
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
}
