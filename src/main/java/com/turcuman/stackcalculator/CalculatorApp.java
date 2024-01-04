package com.turcuman.stackcalculator;

import java.util.Scanner;

public class CalculatorApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String expression;

        while (true) {
            System.out.print("Enter an expression (or type 'exit' to quit): ");
            expression = scanner.nextLine();

            if ("exit".equalsIgnoreCase(expression)) {
                System.out.println("Exiting the calculator.");
                break;
            }

            try {
                double result = StackCalculator.evaluateExpression(expression);
                System.out.println("The result is: " + result);
            } catch (Exception e) {
                System.out.println("Error in expression: ");
                e.printStackTrace();
            }
        }

    }
}
