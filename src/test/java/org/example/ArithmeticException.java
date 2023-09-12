package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ArithmeticException {
    private int divideTwoNumbers(int divident, int divisor) {
        return divident / divisor;
    }
    @Test
    public void testDivideBuZeroThrowException() {
        Assertions.assertThrows(java.lang.ArithmeticException.class, () -> {
            divideTwoNumbers(10, 0);
        });
    }
    @Test
    public void testDivideTwoNumbersReturnsCorrectResult(){
        Assertions.assertEquals(4, divideTwoNumbers(18, 2));
    }

    @Test
    public void testArrayPositive() {
        int[] array = {1, 2, 3, 4, 5};
        int arrayElement = array[4];
        Assertions.assertEquals(5, arrayElement);
    }

    @Test
    public void testNegativeArray() {
        int[] array = {1, 2, 3, 4, 5};
        Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            int arrayElement = array[9];
        });
        System.out.println("Error: Array index out of bounds");
    }
}
