package maths.linear;

public interface Tensor {

    void set(double value, int... indexes);

    double get(int... indexes);

    int[] sizes();

    int rank();

    void clear();
}
