package pkg;
import java.util.Scanner;
import java.util.Random;


public class BaseClass {
	public static void randomize(int[] arr) {
		Random rand = new Random();
		for (int l = 0; l < arr.length; l++) {
			arr[l] = rand.nextInt(200000);
		}
	}
}
