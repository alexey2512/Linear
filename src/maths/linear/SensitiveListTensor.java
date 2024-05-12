package maths.linear;

import java.util.List;

public class SensitiveListTensor extends AbstractListTensor {

    public SensitiveListTensor(List<?> list) {
        super(list, false);
    }
}
