import java.io.InputStream;
import java.util.Scanner;

public class CalculatorApp {

	public static void main(String[] args) {

		char arithmeticoperator;
		int number1, number2, output;
		Scanner input = new Scanner(System.in);
		System.out.println("Enter an operator: +, -, *, or /");
		arithmeticoperator = input.next().charAt(0);
		System.out.println("Enter first number");
		number1 = input.nextInt();
		System.out.println("Enter second number");
		number2 = input.nextInt();

		switch (arithmeticoperator) {

		case '+':
			output = number1 + number2;
			System.out.println(number1 + " + " + number2 + " = " + output);
			break;

		case '-':
			output = number1 - number2;
			System.out.println(number1 + " - " + number2 + " = " + output);
			break;

		case '*':
			output = number1 * number2;
			System.out.println(number1 + " * " + number2 + " = " + output);
			break;

		case '/':
			output = number1 / number2;
			System.out.println(number1 + " / " + number2 + " = " + output);
			break;

		default:
			System.out.println("Invalid operator!");
			break;
		}

		input.close();
	}

}
