package com.turcuman.stackcalculator;

import java.util.Stack;

public class StackCalculator {

    public StackCalculator() {
    }

    public static double evaluateExpression(String expression) {
        String postfix = infixToPostfix(expression);
        return evaluatePostfix(postfix);
    }

    private static String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        StringBuilder numberBuffer = new StringBuilder();
        boolean lastTokenWasOperator = true;

        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                numberBuffer.append(c);
                lastTokenWasOperator = false;
            } else {
                if (numberBuffer.length() > 0) {
                    postfix.append(numberBuffer).append(' ');
                    numberBuffer = new StringBuilder();
                }
                if (c == '(') {
                    stack.push(c);
                    lastTokenWasOperator = true;
                } else if (c == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(') {
                        postfix.append(stack.pop()).append(' ');
                    }
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                    lastTokenWasOperator = false;
                } else if (isOperator(c)) {
                    if (lastTokenWasOperator && c == '-') {
                        numberBuffer.append(c);
                        continue;
                    }
                    while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                        postfix.append(stack.pop()).append(' ');
                    }
                    stack.push(c);
                    lastTokenWasOperator = true;
                }
            }
        }
        if (numberBuffer.length() > 0) {
            postfix.append(numberBuffer).append(' ');
        }
        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(' ');
        }
        return postfix.toString().trim();
    }


    private static boolean isOperator(char c) {
        return (c == '+' || c == '-' || c == '*' || c == '/' || c == '^');
    }

    private static int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            default -> -1;
        };
    }

    private static double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        for (String token : postfix.split("\\s+")) {
            if (token.isEmpty()) {
                continue;
            }
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.valueOf(token));
            } else if (isOperator(token.charAt(0))) {
                double secondOperand = stack.pop();
                double firstOperand = stack.isEmpty() ? 0d : stack.pop();
                switch (token.charAt(0)) {
                    case '+':
                        stack.push(firstOperand + secondOperand);
                        break;
                    case '-':
                        stack.push(firstOperand - secondOperand);
                        break;
                    case '*':
                        stack.push(firstOperand * secondOperand);
                        break;
                    case '/':
                        if (secondOperand == 0) throw new UnsupportedOperationException("Cannot divide by zero");
                        stack.push(firstOperand / secondOperand);
                        break;
                    case '^':
                        stack.push(Math.pow(firstOperand, secondOperand));
                        break;
                }
            }
        }
        return stack.pop();
    }

}
