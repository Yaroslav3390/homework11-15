package org.example;

import java.util.Scanner;
import java.util.InputMismatchException;

import static com.google.common.math.IntMath.factorial;

public class Factorial {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter  integer number");
            int number = scanner.nextInt();
            int factorNumber = 1;

                factorNumber = factorial(number);
                System.out.println(factorNumber);

            System.out.print("Factorial: " + factorNumber);
        } catch (IllegalArgumentException e) {
            System.out.println("Enter a non-negative number ");
        } catch (InputMismatchException e) {
            System.out.println("That's not a positive integer");
        } finally {
            scanner.close();
        }
    }
}







