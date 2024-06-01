package maths.linear.matrices;

import maths.linear.Asserts;
import maths.linear.matrices.Matrix;

import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public interface MutableMatrix extends Matrix {

    default void applyElementByElement(Matrix matrix, BinaryOperator<Double> operator) {
        Asserts.requireNonNull(matrix, operator);
        if (height() != matrix.height() || width() != matrix.width()) {
            throw new IllegalArgumentException("incongruous size of argument: expected " +
                    "(" + height() + ", " + width() + "), actual (" + matrix.height() + ", " + matrix.width() + ")");
        }
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                set(i, j, operator.apply(get(i, j), matrix.get(i, j)));
            }
        }
    }

    default void add(Matrix matrix) {
        applyElementByElement(matrix, Double::sum);
    }

    default void subtract(Matrix matrix) {
        applyElementByElement(matrix, (x, y) -> x - y);
    }

    default void multiply(Matrix matrix) {
        applyElementByElement(matrix, (x, y) -> x * y);
    }

    default void divide(Matrix matrix) {
        applyElementByElement(matrix, (x, y) -> x / y);
    }

    default void applyForEach(UnaryOperator<Double> operator) {
        Objects.requireNonNull(operator);
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                set(i, j, operator.apply(get(i, j)));
            }
        }
    }

    default void matrixXScalar(double scalar) {
        applyForEach((a) -> a * scalar);
    }

}
