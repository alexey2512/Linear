package maths.linear;

public interface Vector extends Iterable<Double> {

    void set(int index, double value);

    double get(int index);

    int size();

    void clear();
}
