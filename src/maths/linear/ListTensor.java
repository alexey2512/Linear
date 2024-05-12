package maths.linear;

import java.util.ArrayList;
import java.util.List;

public class ListTensor extends AbstractListTensor {

    public ListTensor(int... sizes) {
        super(sizes);
    }

    public ListTensor(List<?> list) {
        super(list, true);
    }

    public ListTensor(Object[] array) {
        super(arrayToList(array), false);
    }

    private static List<Object> arrayToList(Object[] array) {
        List<Object> list = new ArrayList<>();
        for (Object obj : array) {
            if (obj.getClass().isArray()) {
                list.add(arrayToList((Object[]) obj));
            } else {
                list.add(obj);
            }
        }
        return list;
    }
}
