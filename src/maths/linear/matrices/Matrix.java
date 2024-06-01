package maths.linear.matrices;

import java.util.Iterator;

public interface Matrix extends Iterable<Double> {

    int height();
    int width();
    void clear();
    double get(int i, int j);
    void set(int i, int j, double value);

}
