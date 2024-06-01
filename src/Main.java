import maths.linear.tensors.ListTensor;
import maths.linear.tensors.Tensor;
import maths.linear.tensors.Tensors;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Tensor a = new ListTensor(List.of(1d, 2d));
        Tensor b = new ListTensor(List.of(1d, 2d, 1d));
        System.out.println(Tensors.tensorMultiply(a, b));
    }
}
