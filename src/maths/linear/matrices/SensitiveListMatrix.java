package maths.linear.matrices;

import java.util.List;
import java.util.Objects;

public class SensitiveListMatrix extends AbstractMatrix {

    private final List<List<Double>> list;

    public SensitiveListMatrix(List<List<Double>> srcList) {
        super(srcList);
        list = srcList;
    }

    @Override
    protected double getImpl(int i, int j) {
        return list.get(i).get(j);
    }

    @Override
    protected void setImpl(int i, int j, double value) {
        list.get(i).set(j, value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, height, width);
    }

}
