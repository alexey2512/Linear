package maths.linear.matrices;

import maths.linear.vectors.Vector;
import maths.linear.vectors.Vectors;
import maths.linear.vectors.ArrayVector;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public final class Matrices {

    private Matrices() {}

    private static Vector getRowColumn(Matrix matrix, int k, boolean isRow) {
        Vector result = new ArrayVector(isRow ? matrix.width() : matrix.height());
        for (int i = 0; i < result.size(); i++) {
            result.set(i, matrix.get(isRow ? k : i, isRow ? i : k));
        }
        return result;
    }

    public static Vector getRow(Matrix matrix, int i) {
        return getRowColumn(matrix, i, true);
    }

    public static Vector getColumn(Matrix matrix, int j) {
        return getRowColumn(matrix, j, false);
    }

    private static void setRowColumn(Matrix matrix, int k, Vector vector, boolean isRow, int param, String stringParam, String object) {
        if (vector.size() != param) {
            throw new IllegalArgumentException("can not set " + object + " with size = " +
                    vector.size() + " in matrix with " + stringParam + " = " + param);
        }
        for (int i = 0; i < param; i++) {
            matrix.set(isRow ? k : i, isRow ? i : k, vector.get(i));
        }
    }

    public static void setRow(Matrix matrix, int i, Vector row) {
        setRowColumn(matrix, i, row, true, matrix.width(), "width", "row");
    }

    public static void setColumn(Matrix matrix, int j, Vector column) {
        setRowColumn(matrix, j, column, false, matrix.height(), "height", "column");
    }

    public static void swapRows(Matrix matrix, int i1, int i2) {
        Vector temp = getRow(matrix, i1);
        setRow(matrix, i1, getRow(matrix, i2));
        setRow(matrix, i2, temp);
    }

    public static void swapColumns( Matrix matrix, int j1, int j2) {
        Vector temp = getColumn(matrix, j1);
        setColumn(matrix, j1, getColumn(matrix, j2));
        setColumn(matrix, j2, temp);
    }

    public static void swap( Matrix matrix, int i1, int j1, int i2, int j2) {
        double temp = matrix.get(i1, j1);
        matrix.set(i1, j1, matrix.get(i2, j2));
        matrix.set(i2, j2, temp);
    }

    public static void fill( Matrix matrix, double x) {
        for (int i = 0; i < matrix.height(); i++) {
            for (int j = 0; j < matrix.width(); j++) {
                matrix.set(i, j, x);
            }
        }
    }

    public static double[][] asArray( Matrix matrix) {
        double[][] result = new double[matrix.height()][matrix.width()];
        for (int i = 0; i < matrix.height(); i++) {
            for (int j = 0; j < matrix.width(); j++) {
                result[i][j] = matrix.get(i, j);
            }
        }
        return result;
    }

    public static List<List<Double>> asList( Matrix matrix) {
        List<List<Double>> result = new ArrayList<>();
        for (int i = 0; i < matrix.height(); i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < matrix.width(); j++) {
                temp.add(matrix.get(i, j));
            }
            result.add(temp);
        }
        return result;
    }

    @Contract("_ -> new")
    public static  Vector asVector( Matrix matrix) {
        List<Double> list = new ArrayList<>();
        for (double x : matrix) {
            list.add(x);
        }
        return new ArrayVector(list);
    }

    public static  Matrix subMatrix( Matrix matrix, int low, int left, int up, int right) {
        if (low < 0 || low > matrix.height() || up < 0 || up > matrix.height() || low >= up ||
                left < 0 || left > matrix.width() || right < 0 || right > matrix.width() || left >= right) {
            throw new IllegalArgumentException("incorrect bounds for matrix.sizes = (" + matrix.height() + ", " + matrix.width() + "): " +
                    "low = " + low + ", left = " + left + ", up = " + up + ", right = " + right);
        }
        Matrix result = new ArrayMatrix(up - low, right - left);
        for (int i = low; i < up; i++) {
            for (int j = left; j < right; j++) {
                result.set(i - low, j - left, matrix.get(i, j));
            }
        }
        return result;
    }

    public static  Matrix applyElementByElement( Matrix a,  Matrix b, BinaryOperator<Double> operator) {
        if (a.height() != b.height() || a.width() != b.width()) {
            throw new IllegalArgumentException("incongruous sizes of arguments: " +
                    "(" + a.height() + ", " + a.width() + "), (" + b.height() + ", " + b.width() + ")");
        }
        Matrix result = new ArrayMatrix(a.height(), a.width());
        for (int i = 0; i < a.height(); i++) {
            for (int j = 0; j < a.width(); j++) {
                result.set(i, j, operator.apply(a.get(i, j), b.get(i, j)));
            }
        }
        return result;
    }

    public static  Matrix add(Matrix a, Matrix b) {
        return applyElementByElement(a, b, Double::sum);
    }

    public static  Matrix subtract(Matrix a, Matrix b) {
        return applyElementByElement(a, b, (x, y) -> x - y);
    }

    public static  Matrix multiply(Matrix a, Matrix b) {
        return applyElementByElement(a, b, (x, y) -> x * y);
    }

    public static  Matrix divide(Matrix a, Matrix b) {
        return applyElementByElement(a, b, (x, y) -> x / y);
    }

    public static  Matrix applyForEach( Matrix matrix, UnaryOperator<Double> operator) {
        Matrix result = new ArrayMatrix(matrix.height(), matrix.width());
        for (int i = 0; i < matrix.height(); i++) {
            for (int j = 0; j < matrix.width(); j++) {
                result.set(i, j, operator.apply(matrix.get(i, j)));
            }
        }
        return result;
    }

    public static  Matrix matrixXScalar(Matrix matrix, double scalar) {
        return applyForEach(matrix, (a) -> a * scalar);
    }

    public static  Matrix copyOf(Matrix matrix) {
        return applyForEach(matrix, (a) -> a);
    }

    private static  Vector mxvOrVxm(Matrix matrix,  Vector vector,
                                            int required, int using, String stringRequired,
                                            BiFunction<Matrix, Integer, Vector> func) {
        if (required != vector.size()) {
            throw new IllegalArgumentException("incongruous matrix." + stringRequired + " and vector.size: " +
                    "for matrix." + stringRequired + " = " + required +
                    " expected vector.size = " + required + ", but found " + vector.size());
        }
        Vector result = new ArrayVector(using);
        for (int i = 0; i < using; i++) {
            result.set(i, Vectors.scalarMultiply(func.apply(matrix, i), vector));
        }
        return result;
    }

    public static  Vector matrixXVector(Matrix matrix, Vector vector) {
        return mxvOrVxm(matrix, vector, matrix.width(), matrix.height(), "width", Matrices::getRow);
    }

    public static  Vector vectorXMatrix(Vector vector, Matrix matrix) {
        return mxvOrVxm(matrix, vector, matrix.height(), matrix.width(), "height", Matrices::getColumn);
    }

    public static  Matrix matrixXMatrix( Matrix a,  Matrix b) {
        if (a.width() != b.height()) {
            throw new IllegalArgumentException("incongruous sizes of matrices: for first.width = "
                    + a.width() + " expected second.height = " + a.width() + " but found " + b.height());
        }
        Matrix result = new ArrayMatrix(a.height(), b.width());
        for (int i = 0; i < a.height(); i++) {
            for (int j = 0; j < b.width(); j++) {
                result.set(i, j, Vectors.scalarMultiply(getRow(a, i), getColumn(b, j)));
            }
        }
        return result;
    }

    private static void applyLinearCombination(Matrix matrix, double scalar, int row, int col) {
        setRow(matrix, row,
                Vectors.subtract(
                    getRow(matrix, row),
                    Vectors.vectorXScalar(getRow(matrix, col), scalar)));
    }

    public static double determinant( Matrix matrix) {
        if (matrix.height() != matrix.width()) {
            throw new IllegalArgumentException("can not get determinant of non square matrix with sizes: " +
                    "(" + matrix.height() + ", " + matrix.width() + ")");
        }
        int n = matrix.height();
        Matrix mat = copyOf(matrix);
        double mul = 1;
        for (int col = 0; col < n - 1; col++) {
            if (mat.get(col, col) == 0) {
                boolean allZeros = true;
                for (int row = col + 1; row < n; row++) {
                    if (mat.get(row, col) != 0) {
                        swapRows(mat, row, col);
                        mul *= -1;
                        allZeros = false;
                        break;
                    }
                }
                if (allZeros) {
                    return 0;
                }
            }
            for (int row = col + 1; row < n; row++) {
                if (mat.get(row, col) != 0) {
                    applyLinearCombination(mat, mat.get(row, col) / mat.get(col, col), row, col);
                }
            }
        }
        double result = 1;
        for (int i = 0; i < n; i++) {
            result *= mat.get(i, i);
        }
        return result * mul;
    }

    public static  Matrix transpose( Matrix matrix) {
        Matrix result = new ArrayMatrix(matrix.width(), matrix.height());
        for (int i = 0; i < matrix.height(); i++) {
            for (int j = 0; j < matrix.width(); j++) {
                result.set(j, i, matrix.get(i, j));
            }
        }
        return result;
    }

    public static  Matrix unitMatrix(int n) {
        Matrix result = new ArrayMatrix(n, n);
        for (int i = 0; i < n; i++) {
            result.set(i, i, 1);
        }
        return result;
    }

    public static  Matrix zeroMatrix(int n) {
        return new ArrayMatrix(n, n);
    }

    private static void gaussForInverse(Matrix left, Matrix right, int n, boolean underDiag) {
        for (int col = 0; col < n; col++) {
            int low = underDiag ? col + 1 : 0;
            int up = underDiag ? n : col;
            if (left.get(col, col) == 0) {
                boolean allZeros = true;
                for (int row = low; row < up; row++) {
                    if (left.get(row, col) != 0) {
                        swapRows(left, row, col);
                        swapRows(right, row, col);
                        allZeros = false;
                        break;
                    }
                }
                if (allZeros) {
                    throw new AssertionError();
                }
            }
            for (int row = low; row < up; row++) {
                if (left.get(row, col) != 0) {
                    double sc = left.get(row, col) / left.get(col, col);
                    applyLinearCombination(left, sc, row, col);
                    applyLinearCombination(right, sc, row, col);
                }
            }
        }
    }

    public static Matrix inverseMatrix(Matrix matrix) {
        Objects.requireNonNull(matrix);
        if (matrix.height() != matrix.width()) {
            throw new IllegalArgumentException("can not get inverse matrix of non square matrix with sizes: " +
                    "(" + matrix.height() + ", " + matrix.width() + ")");
        }
        int n = matrix.height();
        Matrix left = copyOf(matrix);
        Matrix right = unitMatrix(n);
        try {
            gaussForInverse(left, right, n, true);
            gaussForInverse(left, right, n, false);
        } catch (AssertionError e) {
            return zeroMatrix(n);
        }
        for (int row = 0; row < n; row++) {
            setRow(right, row, Vectors.vectorXScalar(getRow(right, row), 1 / left.get(row, row)));
        }
        return right;
    }
}
