package pkg;
import java.util.Scanner;
import java.util.Random;


public class BaseClass {
	public static String toPostFix(String infix) {
		String result = "";
		Stack stack = new Stack();
		char[] arr = infix.toCharArray();
		for (int c = 0; c < arr.length; c++) {
			if (arr[c] == '+' || arr[c] == '-' || arr[c] == '*' || arr[c] == '/' || arr[c] == '%') {
				if (stack.isEmpty()) {
					stack.push(arr[c]);
				}
				else {
					if (arr[c] == '+' || arr[c] == '-') {
						while (!stack.isEmpty() && stack.peek() != '(') {
							result += stack.pop();
						}
						stack.push(arr[c]);
					}
					else if (arr[c] == '*' || arr[c] == '/' || arr[c] == '%') {
						while (!stack.isEmpty() && stack.peek() != '+' && stack.peek() != '-' && stack.peek() != '*' && stack.peek() != '/' && stack.peek() != '%' && stack.peek() != '(') {
							result += stack.pop();
						}
						stack.push(arr[c]);
					}
				}
			}
			else if (arr[c] == '(') {
				stack.push(arr[c]);
			}
			else if (arr[c] == ')') {
				while (stack.peek() != '(') {
					result += stack.pop();
				}
				stack.pop();
			}
			else {
				result += arr[c];
			}
		}
		while (!stack.isEmpty()) {
			result += stack.pop();
		}
		return result;
	}
}
