package maths.discrete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class IntFunctions {

    private IntFunctions() {}

    private static void iaeError(String message) {
        throw new IllegalArgumentException(message);
    }

    private static void aeError(String message) {
        throw new ArithmeticException(message);
    }

    public static int factorial(int n) {
        if (n < 0) {
            iaeError("can not get factorial from negative integer: " + n);
        }
        int result = 1;
        for (int i = 2; i <= n; i++) {
            if (result > Integer.MAX_VALUE / i) {
                aeError("factorial from " + n + " overflows int");
            }
            result *= i;
        }
        return result;
    }


    public static int doubleFactorial(int n) {
        if (n < 0) {
            iaeError("can not get double factorial from negative integer: " + n);
        }
        int result = 1;
        for (int i = n; i > 0; i -= 2) {
            if (result > Integer.MAX_VALUE / i) {
                aeError("double factorial from " + n + " overflows int");
            }
            result *= i;
        }
        return result;
    }

    public static int gcd(int a, int b) {
        if (a < 0 || b < 0) {
            iaeError("can not get gcd when one of numbers is negative: a = " + a + ", b = " + b);
        }
        while (b != 0) {
            int c = a % b;
            a = b;
            b = c;
        }
        return a;
    }

    public static int lcm(int a, int b) {
        int gcd = gcd(a, b);
        long prod = (long) a * b;
        if (prod > Integer.MAX_VALUE) {
            aeError("lcm(" + a + ", " + b + ") overflows int");
        }
        return (int) (prod / gcd);
    }

}
