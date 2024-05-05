package maths.linear;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArrayMatrix extends AbstractMatrix {

    private final double[][] array;
    private final int height;
    private final int width;

    public ArrayMatrix() {
        this.height = 3;
        this.width = 3;
        this.array = new double[3][3];
    }

    public ArrayMatrix(int height, int width) {
        if (height <= 0 || width <= 0) {
            error("can not create matrix with sizes: (" + height + ", " + width + ")");
        }
        this.height = height;
        this.width = width;
        this.array = new double[height][width];
    }

    public ArrayMatrix(double[][] array) {
        if (array.length == 0) {
            error("can not create matrix from empty array");
        }
        this.height = array.length;
        int width = 0;
        for (double[] x : array) {
            width = Math.max(width, x.length);
        }
        if (width == 0) {
            error("can not create matrix from array where all rows is empty");
        }
        this.width = width;
        this.array = new double[height][width];
        for (int i = 0; i < height; i++) {
            System.arraycopy(array[i], 0, this.array[i], 0, array[i].length);
        }
    }

    public ArrayMatrix(List<List<Double>> list) {
        if (list.isEmpty()) {
            error("can not create matrix from empty list");
        }
        this.height = list.size();
        int width = 0;
        for (List<Double> x : list) {
            width = Math.max(width, x.size());
        }
        if (width == 0) {
            error("can not create matrix from list where all rows is empty");
        }
        this.width = width;
        this.array = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                this.array[i][j] = list.get(i).get(j);
            }
        }
    }

    public ArrayMatrix(Vector[] vectors, boolean buildAsRows) {
        int count = vectors.length;
        int size = vectors[0].size();
        for (int i = 0; i < count; i++) {
            if (vectors[i].size() <= 0) {
                error("got vector" + i + " with non positive size = " + vectors[i].size());
            } else if (vectors[i].size() != size) {
                error("can not create matrix from vectors with unequal sizes: " +
                        "vector0.size = " + size + ", vector" + i + ".size = " + vectors[i].size());
            }
        }
        height = buildAsRows ? count : size;
        width = buildAsRows ? size : count;
        array = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                array[i][j] = buildAsRows ? vectors[i].get(j) : vectors[j].get(i);
            }
        }

    }

    private static void error(String message) {
        throw new IllegalArgumentException(message);
    }

    private void check(int i, int j) {
        if (i < 0 || i >= height) {
            error("i = " + i + " out of bounds for height = " + height);
        } else if (j < 0 || j >= width) {
            error("j = " + j + " out of bounds for width = " + width);
        }
    }

    @Override
    public double get(int i, int j) {
        check(i, j);
        return array[i][j];
    }

    @Override
    public void set(int i, int j, double value) {
        check(i, j);
        array[i][j] = value;
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
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(array), height, width);
    }

}
