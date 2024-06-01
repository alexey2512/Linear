package maths.linear.matrices;

import maths.linear.vectors.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListMatrix extends AbstractMatrix {

    private final List<List<Double>> list;

    public ListMatrix() {
        this(3, 3);
    }

    public ListMatrix(int height, int width) {
        super(height, width);
        this.list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                temp.add(0d);
            }
            list.add(temp);
        }
    }

    public ListMatrix(double[][] srcArray) {
        super(srcArray);
        list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                temp.add(srcArray[i][j]);
            }
            list.add(temp);
        }
    }

    public ListMatrix(List<List<Double>> srcList) {
        super(srcList);
        list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                temp.add(srcList.get(i).get(j));
            }
            list.add(temp);
        }
    }

    public ListMatrix(Vector[] vectors, boolean buildAsRows) {
        super(vectors, buildAsRows);
        list = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Double> temp = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                temp.add(buildAsRows ? vectors[i].get(j) : vectors[j].get(i));
            }
            list.add(temp);
        }
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
