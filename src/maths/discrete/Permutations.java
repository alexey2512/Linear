package maths.discrete;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;

public class Permutations implements Iterable<int[]> {

    private final int size;

    public Permutations(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("size of permutations must be non negative, but found " + size);
        }
        this.size = size;
    }

    public @NotNull Iterator<int[]> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<int[]> {

        private final int[] current;
        private int index;
        private final int fact;

        private CustomIterator() {
            current = new int[size];
            for (int i = 0; i < size; i++) {
                current[i] = i;
            }
            index = 0;
            fact = IntFunctions.factorial(size);
        }

        @Override
        public boolean hasNext() {
            return index < fact;
        }

        private void swap(int i, int j) {
            int temp = current[i];
            current[i] = current[j];
            current[j] = temp;
        }

        @Override
        public int[] next() {
            int[] result = Arrays.copyOf(current, size);
            index++;
            if (index < fact) {
                int last = size - 2;
                while (current[last] > current[last + 1]) {
                    last--;
                }
                int min = -1;
                for (int i = size - 1; i > last; i--) {
                    if (current[i] > current[last] && (min == -1 || current[i] < current[min])) {
                        min = i;
                    }
                }
                swap(last, min);
                for (int i = last + 1; i < last + 1 + (size - last - 1) / 2; i++) {
                    swap(i, size - i + last);
                }
            }
            return result;
        }
    }
}
