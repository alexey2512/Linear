package maths.linear.tensors;

import maths.linear.tensors.AbstractListTensor;

import java.util.List;

public class SensitiveListTensor extends AbstractListTensor {

    public SensitiveListTensor(List<?> list) {
        super(list, false);
    }

}
