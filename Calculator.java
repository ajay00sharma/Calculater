import java.util.Stack;

class Calculate {
    Stack<Character> in;
    Stack<Double> post;

    public Calculate() {
        in = new Stack<>();
        post = new Stack<>();
    }

    public int priority(char formula) {
        if (formula == '^')
            return 3;
        else if (formula == '*' || formula == '/')
            return 2;
        else if (formula == '+' || formula == '-')
            return 1;
        else
            return 0;
    }

    public double evaluate(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                post.push((double) (s.charAt(i) - '0')); // subtracting from 0 char gives int
            } else {
                double operand2 = post.pop();
                double operand1 = post.pop();

                switch (s.charAt(i)) {
                    case '+':
                        post.push(operand1 + operand2);
                        break;
                    case '-':
                        post.push(operand1 - operand2);
                        break;
                    case '*':
                        post.push(operand1 * operand2);
                        break;
                    case '/':
                        post.push(operand1 / operand2);
                        break;
                    case '^':
                        post.push(Math.pow(operand1, operand2));
                        break;
                    default:
                        System.out.println("Invalid symbol is being entered");
                        System.out.println("Enter the correct expression");
                        break;
                }
            }
        }
        return post.pop();
    }

    public void intopost(String s) {
        String converted = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                in.push(s.charAt(i));
            } else if (s.charAt(i) == ')') {
                while (in.peek() != '(' && !in.empty()) {
                    converted += in.pop();
                }
                in.pop();
            } else if (s.charAt(i) == '*' || s.charAt(i) == '/' || s.charAt(i) == '+' || s.charAt(i) == '-') {
                while (!in.empty() && priority(s.charAt(i)) <= priority(in.peek())) {
                    converted += in.pop();
                }
                in.push(s.charAt(i));
            } else {
                converted += s.charAt(i);
            }
        }
        while (!in.empty()) {
            converted += in.pop();
        }
        System.out.println();
        System.out.println("The value of the above postfix expression is: " + evaluate(converted));
    }
}

public class Calculator {
    public static void main(String[] args) {
        Calculate obj = new Calculate();
        System.out.println("Enter an expression to calculate its value");
        String expression = System.console().readLine();
        obj.intopost(expression);
    }
}
