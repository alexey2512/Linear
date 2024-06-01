package maths.linear.matrices;

import java.util.Arrays;
import java.util.Objects;

public class SensitiveArrayMatrix extends AbstractMatrix {

    private final double[][] array;

    public SensitiveArrayMatrix(double[][] srcArray) {
        super(srcArray);
        array = srcArray;
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
