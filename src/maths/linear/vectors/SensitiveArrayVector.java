package maths.linear.vectors;

import maths.linear.vectors.AbstractVector;

public class SensitiveArrayVector extends AbstractVector {

    private final double[] array;
    private final int size;

    public SensitiveArrayVector(double[] array) {
        if (array.length == 0) {
            error("can not create vector from empty array");
        }
        this.size = array.length;
        this.array = array;
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
}
