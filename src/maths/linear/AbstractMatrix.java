package maths.linear;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public abstract class AbstractMatrix implements MutableMatrix {

    public @NotNull Iterator<Double> iterator() {
        return new CustomIterator();
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

    @Override
    public void clear() {
        for (int i = 0; i < height(); i++) {
            for (int j = 0; j < width(); j++) {
                set(i, j, 0);
            }
        }
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
