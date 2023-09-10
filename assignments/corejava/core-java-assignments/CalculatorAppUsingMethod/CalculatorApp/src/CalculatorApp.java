import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CalculatorApp {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("First number: ");
			int x = Integer.parseInt(br.readLine());
			System.out.print("Second number: ");
			int y = Integer.parseInt(br.readLine());
			System.out.print("Operator: ");
			String op = br.readLine();
			int z = Calculate(op, x, y);
			System.out.print("Result: " + z);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static int Calculate(String op, int x, int y) {
		int result;
		if (op.equals("+")) {
			result = x + y;
		} else if (op.equals("-")) {
			result = x - y;
		} else if (op.equals("*")) {
			result = x * y;
		} else if (op.equals("/")) {
			result = x / y;
		} else {
			throw new java.lang.Error("Operator not recognized");
		}
		return result;
	}
}