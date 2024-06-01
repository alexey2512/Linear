package maths.linear.vectors;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public interface MutableVector extends Vector {

    default void applyElementByElement(Vector vector, BinaryOperator<Double> operator) {
        if (size() != vector.size()) {
            throw new IllegalArgumentException("incongruous size of argument: expected " + size() + ", actual " + vector.size());
        }
        for (int i = 0; i < size(); i++) {
            set(i, operator.apply(get(i), vector.get(i)));
        }
    }

    default void add(Vector vector) {
        applyElementByElement(vector, Double::sum);
    }

    default void subtract(Vector vector) {
        applyElementByElement(vector, (x, y) -> x - y);
    }

    default void multiply(Vector vector) {
        applyElementByElement(vector, (x, y) -> x * y);
    }

    default void divide(Vector vector) {
        applyElementByElement(vector, (x, y) -> x / y);
    }

    default void applyForEach(UnaryOperator<Double> operator) {
        for (int i = 0; i < size(); i++) {
            set(i, operator.apply(get(i)));
        }
    }

    default void vectorXScalar(double scalar) {
        applyForEach((a) -> a * scalar);
    }

}
