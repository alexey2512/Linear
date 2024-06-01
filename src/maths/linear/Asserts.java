package maths.linear;

import maths.exceptions.AccessException;
import maths.exceptions.InvalidObjectException;
import maths.linear.matrices.Matrix;
import maths.linear.vectors.Vector;

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
            invError("found vector with non positive size = " + size);
        }
        for (int i = 0; i < size; i++) {
            try {
                vector.get(i);
            } catch (Throwable e) {
                accError("invalid vector found: can not get value from i = " + i + " for size " + size);
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
            invError("found matrix with non positive height = " + height);
        } else if (width <= 0) {
            invError("found matrix with non positive width = " + width);
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                try {
                    matrix.get(i, j);
                } catch (Throwable e) {
                    accError("invalid matrix found: can not get value from i = " + i + ", j = " + j +
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

    private static void accError(String message) {
        throw new AccessException(message);
    }

    private static void invError(String message) {
        throw new InvalidObjectException(message);
    }
}