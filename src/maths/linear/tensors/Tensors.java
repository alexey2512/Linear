package maths.linear.tensors;

import maths.linear.tensors.ListTensor;
import maths.linear.tensors.Tensor;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

public final class Tensors {

    private Tensors() {}

    private static int[] getProducts(int[] sizes) {
        int[] products = new int[sizes.length];
        int cur = 1;
        for (int i = sizes.length - 1; i >= 0; i--) {
            products[i] = cur;
            cur *= sizes[i];
        }
        return products;
    }

    private static void setIndexes(int[] products, int[] dest, int from, int to, int value) {
        for (int i = 0; i < to - from; i++) {
            dest[i + from] = value / products[i];
            value %= products[i];
        }
    }

    private static void setIndexes(int[] products, int[] dest, int value) {
        setIndexes(products, dest, 0, dest.length, value);
    }

    public static Tensor applyElementByElement(Tensor a, Tensor b, BinaryOperator<Double> operator) {
        int[] aSizes = a.sizes(), bSizes = b.sizes();
        if (!Arrays.equals(aSizes, bSizes)) {
            throw new IllegalArgumentException("unequal sizes of arguments: " + Arrays.toString(aSizes) + ", " + Arrays.toString(bSizes));
        }
        int[] products = getProducts(aSizes);
        int[] indexes = new int[aSizes.length];
        Tensor result = new ListTensor(aSizes);
        for (int i = 0; i < products[0] * aSizes[0]; i++) {
            setIndexes(products, indexes, i);
            result.set(operator.apply(a.get(indexes), b.get(indexes)), indexes);
        }
        return result;
    }

    public static Tensor add(Tensor a, Tensor b) {
        return applyElementByElement(a, b, Double::sum);
    }

    public static Tensor subtract(Tensor a, Tensor b) {
        return applyElementByElement(a, b, (x, y) -> x - y);
    }

    public static Tensor multiply(Tensor a, Tensor b) {
        return applyElementByElement(a, b, (x, y) -> x * y);
    }

    public static Tensor divide(Tensor a, Tensor b) {
        return applyElementByElement(a, b, (x, y) -> x / y);
    }

    public static Tensor applyForEach(Tensor tensor, UnaryOperator<Double> operator) {
        int[] sizes = tensor.sizes();
        int[] products = getProducts(sizes);
        int[] indexes = new int[sizes.length];
        Tensor result = new ListTensor(sizes);
        for (int i = 0; i < products[0] * sizes[0]; i++) {
            setIndexes(products, indexes, i);
            result.set(operator.apply(tensor.get(indexes)), indexes);
        }
        return result;
    }

    public static Tensor tensorXScalar(Tensor tensor, double scalar) {
        return applyForEach(tensor, (a) -> a * scalar);
    }

    public static Tensor copOf(Tensor tensor) {
        return applyForEach(tensor, (a) -> a);
    }

    public static Tensor tensorMultiply(Tensor a, Tensor b) {
        int aRank = a.rank();
        int bRank = b.rank();
        int rank = aRank + bRank;
        int[] aSizes = a.sizes();
        int[] bSizes = b.sizes();
        int[] aProducts = getProducts(aSizes);
        int[] bProducts = getProducts(bSizes);
        int[] aIndexes = new int[aSizes.length];
        int[] bIndexes = new int[bSizes.length];
        int[] rSizes = new int[rank];
        int[] rIndexes = new int[rank];
        System.arraycopy(aSizes, 0, rSizes, 0, aRank);
        System.arraycopy(bSizes, 0, rSizes, aRank, bRank);
        Tensor result = new ListTensor(rSizes);
        for (int i = 0; i < aProducts[0] * aSizes[0]; i++) {
            setIndexes(aProducts, aIndexes, i);
            System.arraycopy(aIndexes, 0, rIndexes, 0, aRank);
            double at = a.get(aIndexes);
            for (int j = 0; j < bProducts[0] * bSizes[0]; j++) {
                setIndexes(bProducts, bIndexes, j);
                System.arraycopy(bIndexes, 0, rIndexes, aRank, bRank);
                result.set(at * b.get(bIndexes), rIndexes);
            }
        }
        return result;
    }

}
