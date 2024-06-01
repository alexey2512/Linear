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

    public static boolean isPrime(int number) {
        if (number < 0) {
            number = -number;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> primesFromDiapason(int start, int end) {
        if (end <= start) {
            iaeError("can not get primes from diapason: [ " + start + " ; " + end + " )");
        }
        boolean[] flags = new boolean[end];
        Arrays.fill(flags, true);
        for (int i = 2; i < end; i++) {
            if (flags[i]) {
                for (int j = 2 * i; j < end; j += i) {
                    flags[j] = false;
                }
            }
        }
        List<Integer> result = new ArrayList<>();
        for (int i = Math.max(start, 2); i < end; i++) {
            if (flags[i]) {
                result.add(i);
            }
        }
        return result;
    }

    public static List<Integer> toPrimeDivisors(int number) {
        if (number <= 0) {
            iaeError("can not decompose into primes non positive number: " + number);
        }
        List<Integer> primes = primesFromDiapason(0, number);
        if (number == Integer.MAX_VALUE) {
            primes.add(Integer.MAX_VALUE);
        }
        List<Integer> result = new ArrayList<>();
        int index = 0;
        while (number != 1) {
            int divisor = primes.get(index);
            while (number % divisor == 0) {
                number /= divisor;
                result.add(divisor);
            }
            index++;
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
