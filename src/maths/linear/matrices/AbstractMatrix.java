package maths.linear.matrices;

import maths.exceptions.InitializationException;
import maths.linear.vectors.Vector;
import maths.linear.Asserts;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractMatrix implements MutableMatrix {

    protected final int height;
    protected final int width;

    protected AbstractMatrix(int height, int width) {
        if (height <= 0 || width <= 0) {
            initError("can not create matrix with sizes: (" + height + ", " + width + ")");
        }
        this.height = height;
        this.width = width;
    }

    protected AbstractMatrix(double[][] srcArray) {
        if (srcArray.length == 0) {
            initError("can not create matrix from empty array");
        }
        height = srcArray.length;
        width = srcArray[0].length;
        if (width == 0) {
            initError("can not create matrix when row0 is empty");
        }
        for (int i = 1; i < height; i++) {
            if (srcArray[i].length != width) {
                initError("can not create matrix when two of rows have unequal sizes: " +
                        "row0.size = " + width + ", row" + i + ".size = " + srcArray[i].length);
            }
        }
    }

    protected AbstractMatrix(List<List<Double>> srcList) {
        if (srcList.isEmpty()) {
            initError("can not create matrix from empty list");
        }
        height = srcList.size();
        width = srcList.getFirst().size();
        if (width == 0) {
            initError("can not create matrix when row0 is empty");
        }
        for (int i = 1; i < height; i++) {
            if (srcList.get(i).size() != width) {
                initError("can not create matrix when two of rows have unequal sizes: " +
                        "row0.size = " + width + ", row" + i + ".size = " + srcList.get(i).size());
            }
        }
    }

    protected AbstractMatrix(Vector[] vectors, boolean buildAsRows) {
        int count = vectors.length;
        int size = vectors[0].size();
        for (int i = 0; i < count; i++) {
            Asserts.checkVectorAccess(vectors[i]);
            if (vectors[i].size() != size) {
                initError("can not create matrix from vectors with unequal sizes: " +
                        "vector0.size = " + size + ", vector" + i + ".size = " + vectors[i].size());
            }
        }
        height = buildAsRows ? count : size;
        width = buildAsRows ? size : count;
    }

    private static void initError(String message) {
        throw new InitializationException(message);
    }

    private static void illError(String message) {
        throw new IllegalArgumentException(message);
    }

    private void check(int i, int j) {
        if (i < 0 || i >= height) {
            illError("i = " + i + " out of bounds for height = " + height);
        } else if (j < 0 || j >= width) {
            illError("j = " + j + " out of bounds for width = " + width);
        }
    }

    protected abstract double getImpl(int i, int j);
    protected abstract void setImpl(int i, int j, double value);

    @Override
    public double get(int i, int j) {
        check(i, j);
        return getImpl(i, j);
    }

    @Override
    public void set(int i, int j, double value) {
        check(i, j);
        setImpl(i, j, value);
    }

    @Override
    public int height() {
        return height;
    }

    @Override
    public int width() {
        return width;
    }

    @Override
    public void clear() {
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                set(i, j, 0);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < height(); i++) {
            sb.append("[");
            for (int j = 0; j < width(); j++) {
                sb.append(get(i, j)).append(j == width() - 1 ? "]" : ", ");
            }
            sb.append(i == height() - 1 ? "]" : ", ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this.getClass() == object.getClass() &&
        height() == ((Matrix) object).height() &&
        width() == ((Matrix) object).width()) {
            Asserts.checkMatrixAccess((Matrix) object);
            for (int i = 0; i < height(); i++) {
                for (int j = 0; j < width(); j++) {
                    if (get(i, j) != ((Matrix) object).get(i, j)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public @NotNull Iterator<Double> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<Double> {

        private int x;
        private int y;

        private CustomIterator() {
            x = 0;
            y = 0;
        }

        @Override
        public boolean hasNext() {
            return x < height();
        }

        @Override
        public Double next() {
            double result = get(x, y);
            y++;
            if (y == width()) {
                y = 0;
                x++;
            }
            return result;
        }
    }
}