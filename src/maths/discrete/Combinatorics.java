package maths.discrete;

import java.util.ArrayList;
import java.util.Arrays;

public final class Combinatorics {

    private Combinatorics() {}

    private static void iaeError(String message) {
        throw new IllegalArgumentException(message);
    }

    private static void aeError(String message) {
        throw new ArithmeticException(message);
    }

    private static void checkPermutation(int[] permutation) {
        int[] temp = new int[permutation.length];
        for (int i = 0; i < temp.length; i++) {
            int elem = permutation[i];
            if (elem < 0 || elem >= temp.length) {
                throw new IllegalArgumentException("incorrect permutation found: element " + elem + " is out of bounds for permutation.length = " + temp.length);
            } else if (temp[elem] == 1) {
                throw new IllegalArgumentException("incorrect permutation found: element " + elem + " is not unique");
            }
            temp[elem] = 1;
        }
    }

    private static int[] invBySort(int[] q, Number inversions) {
        int len = q.length;
        if (len == 1) {
            return q;
        } else {
            int[] left = new int[len / 2];
            int[] right = new int[len - len / 2];
            System.arraycopy(q, 0, left, 0, len / 2);
            System.arraycopy(q, len / 2, right, 0, len - len / 2);
            int[] l = invBySort(left, inversions);
            int[] r = invBySort(right, inversions);
            return merge(l , r, inversions);
        }
    }

    private static int[] merge(int[] a, int[] b, Number inversions) {
        int n = a.length, m = b.length;
        int i = 0, j = 0;
        int[] c = new int[n + m];
        while (i < n || j < m) {
            if ((j == m) || (i < n && a[i] <= b[j])) {
                c[i + j] = a[i];
                i++;
            } else {
                c[i + j] = b[j];
                j++;
                inversions.value += n - i;
            }
        }
        return c;
    }

    public static int getParityOfPermutation(int[] permutation) {
        checkPermutation(permutation);
        Number inversions = new Number(0);
        invBySort(permutation, inversions);
        return inversions.value % 2 == 0 ? 1 : -1;
    }

    public static int getIndexOfPermutation(int[] permutation) {
        checkPermutation(permutation);
        int size = permutation.length;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        int index = 0;
        for (int i = 0; i < size; i++) {
            int ind = list.indexOf(permutation[i]);
            int fact = IntFunctions.factorial(size - i - 1);
            if (ind > Integer.MAX_VALUE / fact) {
                aeError("index of permutation " + Arrays.toString(permutation) + " overflows int");
            }
            int p = ind * fact;
            if (p > Integer.MAX_VALUE - index) {
                aeError("index of permutation " + Arrays.toString(permutation) + " overflows int");
            }
            index += p;
            list.remove((Integer) permutation[i]);
        }
        return index;
    }

    private final static class Number {
        private int value;
        private Number(int value) {
            this.value = value;
        }
    }


}
