package maths.linear.matrices;

import maths.linear.vectors.Vector;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ArrayMatrix extends AbstractMatrix {

    private final double[][] array;

    public ArrayMatrix() {
        this(3, 3);
    }

    public ArrayMatrix(int height, int width) {
        super(height, width);
        this.array = new double[height][width];
    }

    public ArrayMatrix(double[][] srcArray) {
        super(srcArray);
        array = new double[height][width];
        for (int i = 0; i < height; i++) {
            System.arraycopy(srcArray[i], 0, array[i], 0, width);
        }
    }

    public ArrayMatrix(List<List<Double>> srcList) {
        super(srcList);
        array = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                array[i][j] = srcList.get(i).get(j);
            }
        }
    }

    public ArrayMatrix(Vector[] vectors, boolean buildAsRows) {
        super(vectors, buildAsRows);
        array = new double[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                array[i][j] = buildAsRows ? vectors[i].get(j) : vectors[j].get(i);
            }
        }
    }

    @Override
    protected double getImpl(int i, int j) {
        return array[i][j];
    }

    @Override
    protected void setImpl(int i, int j, double value) {
        array[i][j] = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(array), height, width);
    }

}
