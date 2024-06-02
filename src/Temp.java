import maths.linear.matrices.ArrayMatrix;
import maths.linear.matrices.Matrices;
import maths.linear.matrices.Matrix;

public class Temp {
    public static void main(String[] args) {
        Matrix T = new ArrayMatrix(new double[][]{
                {1, 3, 1},
                {1, 0, -3},
                {0, 1, 1}
        });
        Matrix A = new ArrayMatrix(new double[][]{
                {Math.sin(1), 0, 0},
                {0, Math.sin(1), 0},
                {0, 0, Math.sin(2)}
        });
        System.out.println(Matrices.matrixXMatrix(Matrices.matrixXMatrix(T, A), Matrices.inverseMatrix(T)));
    }
}
