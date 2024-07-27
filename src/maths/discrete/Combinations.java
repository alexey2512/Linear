package maths.discrete;

import java.util.Arrays;
import java.util.Iterator;

public class Combinations implements Iterable<int[]> {

    private final int n;
    private final int k;

    public Combinations(int n, int k) {
        if (n < 0 || k < 0) {
            throw new IllegalArgumentException("parameters n and k must be non negative, but found: n = " + n + ", k = " + k);
        } else if (k > n) {
            throw new IllegalArgumentException("k must be not bigger than n, but found: n = " + n + ", k = " + k);
        }
        this.n = n;
        this.k = k;
    }

    @Override
    public Iterator<int[]> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<int[]> {

        private final int[] current;
        private int index;
        private final int count;

        private CustomIterator() {
            current = new int[k];
            for (int i = 0; i < k; i++) {
                current[i] = i;
            }
            index = 0;
            count = IntFunctions.factorial(n) / IntFunctions.factorial(k) / IntFunctions.factorial(n - k);
        }

        @Override
        public boolean hasNext() {
            return index < count;
        }

        @Override
        public int[] next() {
            int[] result = Arrays.copyOf(current, k);
            index++;
            if (index < count) {
                int[] b = new int[k + 1];
                System.arraycopy(current, 0, b, 0, k);
                b[k] = n;
                int i = k - 1;
                while (i >= 0 && b[i + 1] - b[i] < 2) {
                    i--;
                }
                b[i]++;
                for (int j = i + 1; j < k; j++) {
                    b[j] = b[j - 1] + 1;
                }
                System.arraycopy(b, 0, current, 0, k);
            }
            return result;
        }
    }
}
