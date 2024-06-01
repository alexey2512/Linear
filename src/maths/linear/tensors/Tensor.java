package maths.linear.tensors;

public interface Tensor extends Iterable<Double> {

    void set(double value, int... indexes);

    double get(int... indexes);

    int[] sizes();

    int rank();

    void clear();
}
