package maths.linear;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractListTensor implements Tensor {

    private final List<?> list;
    private final int[] sizes;
    private final int rank;

    protected AbstractListTensor(int... sizes) {
        this.sizes = sizes;
        rank = sizes.length;
        list = generate(sizes, 0, rank);
    }

    @SuppressWarnings("unchecked")
    protected AbstractListTensor(List<?> list, boolean withCopy) {
        List<Integer> tempSizes = new ArrayList<>();
        rank = getRankAndSizes(list, 1, tempSizes);
        checkList(list, 1, rank, tempSizes);
        sizes = new int[rank];
        for (int i = 0; i < rank; i++) {
            sizes[i] = tempSizes.get(i);
        }
        if (withCopy) {
            List<?> newList = generate(sizes, 0, rank);
            assignAll((List<Object>) list, (List<Object>) newList, 0, sizes);
            list = newList;
        }
        this.list = list;
    }

    private static List<?> generate(int[] sizes, int level, int rank) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < sizes[level]; i++) {
            result.add(level == rank - 1 ? 0d : generate(sizes, level + 1, rank));
        }
        return result;
    }

    private static int getRankAndSizes(List<?> list, int level, List<Integer> sizes) {
        if (list.isEmpty()) {
            error("found empty list at depth = " + (level - 1));
        }
        sizes.add(list.size());
        Class<?> clazz = list.getFirst().getClass();
        if (Double.class.isAssignableFrom(clazz)) {
            return level;
        } else if (List.class.isAssignableFrom(clazz)) {
            return getRankAndSizes((List<?>) list.getFirst(), level + 1, sizes);
        } else {
            error("expected types List and Double, but found instance of " + clazz + " at depth = " + level);
        }
        return 0;
    }

    private static void checkList(List<?> list, int level, int rank, List<Integer> sizes) {
        if (list.isEmpty()) {
            error("found empty list at depth = " + (level - 1));
        } else if (list.size() != sizes.get(level - 1)) {
            error("expected list with size = " + sizes.get(level - 1)
                    + ", but found list with size = " + list.size() + " at depth = " + (level - 1));
        }
        for (Object element : list) {
            Class<?> clazz = level == rank ? Double.class : List.class;
            if (!clazz.isAssignableFrom(element.getClass())) {
                error("expected instance of " + clazz + ", but found instance of "
                        + element.getClass() + " at depth = " + level);
            }
            if (level < rank) {
                checkList((List<?>) element, level + 1, rank, sizes);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void assignAll(List<Object> dest, List<Object> src, int level, int[] sizes) {
        for (int i = 0; i < sizes[level]; i++) {
            if (level == sizes.length - 1) {
                src.set(i, dest.get(i));
            } else {
                assignAll((List<Object>) dest.get(i), (List<Object>) src.get(i), level + 1, sizes);
            }
        }
    }

    private static void error(String message) {
        throw new IllegalArgumentException(message);
    }

    private void check(int[] indexes) {
        if (indexes.length != rank) {
            error("invalid number of indexes: expected " + rank + ", actual " + indexes.length);
        }
        for (int i = 0; i < rank; i++) {
            if (indexes[i] < 0 || indexes[i] >= sizes[i]) {
                error("index = " + indexes[i] + " out of bounds for size = " + sizes[i] + " at depth = " + i);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void set(double value, int... indexes) {
        check(indexes);
        recursiveSet((List<Object>) list, value, indexes, 0);
    }

    @SuppressWarnings("unchecked")
    private void recursiveSet(List<Object> arr, double value, int[] indexes, int level) {
        if (level == rank - 1) {
            arr.set(indexes[rank - 1], value);
            return;
        }
        recursiveSet((List<Object>) arr.get(indexes[level]), value, indexes, level + 1);
    }

    @Override
    public double get(int... indexes) {
        check(indexes);
        return recursiveGet(list, indexes, 0);
    }

    private double recursiveGet(List<?> arr, int[] indexes, int level) {
        if (level == rank - 1) {
            return (double) arr.get(indexes[level]);
        }
        return recursiveGet((List<?>) arr.get(indexes[level]), indexes, level + 1);
    }

    @Override
    public int[] sizes() {
        return Arrays.copyOf(sizes, rank);
    }

    @Override
    public int rank() {
        return rank;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        recursiveClear((List<Object>) list, 0);
    }

    @SuppressWarnings("unchecked")
    private void recursiveClear(List<Object> arr, int level) {
        if (level == rank - 1) {
            for (int i = 0; i < sizes[level]; i++) {
                arr.set(i, 0d);
            }
            return;
        }
        for (int i = 0; i < sizes[level]; i++) {
            recursiveClear((List<Object>) arr.get(i), level + 1);
        }
    }

    @Override
    public String toString() {
        return list.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, Arrays.hashCode(sizes), rank);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj instanceof AbstractListTensor tensor) {
            return rank == tensor.rank && Arrays.equals(sizes, tensor.sizes)
                    && recursiveEquals((List<Object>) list, (List<Object>) tensor.list, 0);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean recursiveEquals(List<Object> thisList, List<Object> otherList, int level) {
        boolean result = true;
        for (int i = 0; i < sizes[level]; i++) {
            result &= level == rank - 1 ? thisList.get(i).equals(otherList.get((i))) :
                    recursiveEquals((List<Object>) thisList.get(i), (List<Object>) otherList.get(i), level + 1);
        }
        return result;
    }
}
