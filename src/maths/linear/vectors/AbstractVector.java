package maths.linear.vectors;

import maths.linear.Asserts;
import org.jetbrains.annotations.NotNull;
import java.util.Iterator;

public abstract class AbstractVector implements MutableVector {

    @Override
    public void clear() {
        for (int i = 0; i < size(); i++) {
            set(i, 0);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size(); i++) {
            sb.append(get(i)).append(i == size() - 1 ? "]" : ", ");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (this.getClass() == object.getClass() &&
        size() == ((Vector) object).size()) {
            Asserts.checkVectorAccess((Vector) object);
            for (int i = 0; i < size(); i++) {
                if (get(i) != ((Vector) object).get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public @NotNull Iterator<Double> iterator() {
        return new CustomIterator();
    }

    private class CustomIterator implements Iterator<Double> {

        private int position;

        private CustomIterator() {
            position = 0;
        }

        @Override
        public boolean hasNext() {
            return position < size();
        }

        @Override
        public Double next() {
            return get(position++);
        }
    }

}
