package maths.linear;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public final class Vectors {

    private Vectors() {}

    public static void swap(Vector vector, int i, int j) {
        double temp = vector.get(i);
        vector.set(i, vector.get(j));
        vector.set(j, temp);
    }

    public static double[] asArray(Vector vector) {
        double[] result = new double[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            result[i] = vector.get(i);
        }
        return result;
    }

    public static List<Double> asList(Vector vector) {
        List<Double> result = new ArrayList<>();
        for (double x : vector) {
            result.add(x);
        }
        return result;
    }

    public static void fill(Vector vector, double x) {
        for (int i = 0; i < vector.size(); i++) {
            vector.set(i, x);
        }
    }

    public static void shift(Vector vector, int k) {
        double[] temp = asArray(vector);
        int size = vector.size();
        for (int i = 0; i < size; i++) {
            vector.set((i + k >= 0 ? (i + k) % size : (size - (i + k) % size) % size), temp[i]);
        }
    }

    public static void reverse(Vector vector) {
        double[] temp = asArray(vector);
        for (int i = 0; i < vector.size(); i++) {
            vector.set(i, (vector.size()) - i - 1);
        }
    }

    public static Vector concat(Vector a, Vector b) {
        Vector result = new ArrayVector(a.size() + b.size());
        for (int i = 0; i < a.size(); i++) {
            result.set(i, a.get(i));
        }
        for (int i = 0; i < b.size(); i++) {
            result.set(i + a.size(), b.get(i));
        }
        return result;
    }

    public static Vector subVector(Vector vector, int start, int end) {
        if (start < 0 || start > vector.size() || end < 0 || end > vector.size() || start > end) {
            throw new IllegalArgumentException("incorrect start and end bounds for vector.size = " + vector.size() + ": start = " + start + ", end = " + end);
        }
        Vector result = new ArrayVector(end - start);
        for (int i = start; i < end; i++) {
            result.set(i - start, vector.get(i));
        }
        return result;
    }

    public static Vector applyElementByElement(Vector a, Vector b, BinaryOperator<Double> operator) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("unequal sizes of arguments: " + a.size() + ", " + b.size());
        }
        Vector result = new ArrayVector(a.size());
        for (int i = 0; i < a.size(); i++) {
            result.set(i, operator.apply(a.get(i), b.get(i)));
        }
        return result;
    }

    public static Vector add(Vector a, Vector b) {
        return applyElementByElement(a, b, Double::sum);
    }

    public static Vector subtract(Vector a, Vector b) {
        return applyElementByElement(a, b, (x, y) -> x - y);
    }

    public static Vector multiply(Vector a, Vector b) {
        return applyElementByElement(a, b, (x, y) -> x * y);
    }

    public static Vector divide(Vector a, Vector b) {
        return applyElementByElement(a, b, (x, y) -> x / y);
    }

    public static Vector applyForEach(Vector vector, UnaryOperator<Double> operator) {
        Vector result = new ArrayVector(vector.size());
        for (int i = 0; i < vector.size(); i++) {
            result.set(i, operator.apply(vector.get(i)));
        }
        return result;
    }

    public static Vector vectorXScalar(Vector vector, double scalar) {
        return applyForEach(vector, (a) -> a * scalar);
    }

    public static Vector copyOf(Vector vector) {
        return applyForEach(vector, (a) -> a);
    }

    public static double scalarMultiply(Vector a, Vector b) {
        if (a.size() != b.size()) {
            throw new IllegalArgumentException("unequal sizes of arguments: " + a.size() + ", " + b.size());
        }
        double result = 0;
        for (int i = 0; i < a.size(); i++) {
            result += a.get(i) * b.get(i);
        }
        return result;
    }

    public static double scalarMultiplyByMetric(Vector a, Vector b, Matrix metric) {
        if (a.size() != metric.height() || b.size() != metric.width()) {
            throw new IllegalArgumentException("incongruous size of metric: for first.size = " + a.size() +
                    " and second.size = " + b.size() + " expected metric sizes = (" + a.size() + ", " + b.size() + "), " +
                    "but found (" + metric.height() + ", " + metric.width() + ")");
        }
        double result = 0;
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                result += a.get(i) * b.get(j) * metric.get(i, j);
            }
        }
        return result;
    }

    public static Vector vectorMultiply(Vector... vectors) {
        int size = vectors.length + 1;
        for (int i = 0; i < size - 1; i++) {
            if (vectors[i].size() != size) {
                throw new IllegalArgumentException("incongruous count of arguments and size of argument " + i + ": " +
                        "for count = " + (size - 1) + " all arguments must have size = " + size + " but found " + vectors[i].size());
            }
        }
        Matrix mat = new ArrayMatrix(size - 1, size - 1);
        for (int i = 0; i < size - 1; i++) {
            for (int j = 1; j < size; j++) {
                mat.set(i, j - 1, vectors[i].get(j));
            }
        }
        Vector result = new ArrayVector(size);
        for (int i = 0; i < size; i++) {
            result.set(i, Matrices.determinant(mat) * (((i + 1) % 2) * 2 - 1));
            if (i != size - 1) {
                for (int j = 0; j < size - 1; j++) {
                    mat.set(j, i, vectors[j].get(i));
                }
            }
        }
        return result;
    }

    public static Matrix vectorXVector(Vector a, Vector b) {
        Matrix result = new ArrayMatrix(a.size(), b.size());
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                result.set(i, j, a.get(i) * b.get(j));
            }
        }
        return result;
    }

    public static boolean isLinearIndependent(Vector... vectors) {
        int size = vectors[0].size();
        int count = vectors.length;
        for (int i = 1; i < count; i++) {
            if (size != vectors[i].size()) {
                throw new IllegalArgumentException("unequal sizes of vectors: size of 0th is " + size +
                        " but found size = " + vectors[i].size() + " for vector number " + i);
            }
        }
        for (int start = 0; start <= size - count; start++) {
            Matrix mat = new ArrayMatrix(count, count);
            for (int i = 0; i < count; i++) {
                for (int j = 0; j < count; j++) {
                    mat.set(i, j, vectors[j].get(i + start));
                }
            }
            if (Matrices.determinant(mat) != 0) {
                return true;
            }
        }
        return false;
    }

}
