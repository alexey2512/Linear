package maths.linear;

public interface Matrix extends Iterable<Double> {

    int height();

    int width();

    double get(int i, int j);

    void set(int i, int j, double value);

    void clear();
}
