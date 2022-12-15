package pkg;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class BaseClass {
	public static void randomize(int[] arr) {
		Random rand = new Random();
		for (int l = 0; l < arr.length; l++) {
			arr[l] = rand.nextInt(200000);
		}
	}
	public static void printArr(int[] arr) {
		for (int num: arr) {
			System.out.print(num + " ");
		}
		System.out.println();
	}
}
