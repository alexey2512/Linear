package maths.linear;

import java.util.Objects;

public final class Asserts {

    private Asserts() {}

    public static void requireNonNull(Object... objects) {
        for (Object object : objects) {
            Objects.requireNonNull(object);
        }
    }

    public static void checkVectorAccess(Vector vector) {
        Objects.requireNonNull(vector);
        int size = vector.size();
        if (size <= 0) {
            error("found vector with non positive size = " + size);
        }
        for (int i = 0; i < size; i++) {
            try {
                vector.get(i);
            } catch (Throwable e) {
                error("invalid vector found: can not get value from i = " + i + " for size " + size);
            }
        }
    }

    public static void checkAllVectors(Vector... vectors) {
        for (Vector vector : vectors) {
            checkVectorAccess(vector);
        }
    }

    public static void checkMatrixAccess(Matrix matrix) {
        Objects.requireNonNull(matrix);
        int height = matrix.height();
        int width = matrix.width();
        if (height <= 0) {
            error("found matrix with non positive height = " + height);
        } else if (width <= 0) {
            error("found matrix with non positive width = " + width);
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    matrix.get(i, j);
                } catch (Throwable e) {
                    error("invalid matrix found: can not get value from i = " + i + ", j = " + j +
                            " for sizes (" + height + ", " + width + ")");
                }
            }
        }
    }

    public static void checkAllMatrices(Matrix... matrices) {
        for (Matrix matrix : matrices) {
            checkMatrixAccess(matrix);
        }
    }

    private static void error(String message) {
        throw new IllegalArgumentException(message);
    }

}
