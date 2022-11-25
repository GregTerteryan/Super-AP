package pkg;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class BaseClass {
	public static void mergeSort(int[] arr) {
		if (arr.length == 1) {
			return;
		}
		if (arr.length == 2) {
			if (arr[0] > arr[1]) {
				int temp = arr[0];
				arr[0] = arr[1];
				arr[1] = temp;
			}
		}
		else {
			int[] half1 = new int[arr.length/2];
			int[] half2 = new int[arr.length - half1.length];
			for (int i = 0; i < arr.length; i++) {
				if (i < half1.length) {
					half1[i] = arr[i];
				}
				else {
					half2[i-half1.length] = arr[i];
				}
			}
			mergeSort(half1);
			mergeSort(half2);
			
			merge(half1, half2, arr);
		}
	}
	//precondition: A.length + B.length = C.length
	private static void merge(int[] A, int[] B, int[] C){
		int aCount = A.length;
		int bCount = B.length;
		ArrayList<Integer> tempA = new ArrayList<Integer>();
		ArrayList<Integer> tempB = new ArrayList<Integer>();
		for (int a:A) {
			tempA.add(a);
		}
		for (int b:B) {
			tempB.add(b);
		}
		int min = Integer.MAX_VALUE;
		for (int c = 0; c < C.length; c++) {
			if (aCount == 0) {
				C[c] = tempB.remove(0);
				bCount--;
			}
			else if (bCount == 0) {
				C[c] = tempA.remove(0);
				aCount--;
			}
			else {
				if (tempA.get(0) <= tempB.get(0)) {
					C[c] = tempA.remove(0);
					aCount--;
				}
				else {
					C[c] = tempB.remove(0);
					bCount--;
				}
			}
		}
		/*ArrayList<Integer> minVals = new ArrayList<Integer>();
		for (int c = 0; c < C.length; c++) {
			int minVal = 0;
			int min = Integer.MAX_VALUE;
			//0 is neither, 1 is A, 2 is B
			int in = 0;
			for (int d = 0; d < C.length; d++) {
				if (!minVals.contains(d)) {
					if (d < A.length && A[d] < min) {
						min = A[d];
						minVal = d;
					}
					else if (d >= A.length && B[d - A.length] < min) {
						min = B[d-A.length];
						minVal = d;
					}
				}
			}
			if (in == 1) {
				aCount--;
			}
			else if (in == 2) {
				bCount--;
			}
			minVals.add(minVal);
			C[c] = min;
		}
		*/
	}
	public static void mergeSortLab(ArrayList<DayBirth> arr) {
		if (arr.size() == 1) {
			return;
		}
		else if (arr.size() == 2) {
			if (arr.get(0).getBirths() > arr.get(1).getBirths()) {
				DayBirth temp = arr.get(0);
				arr.set(0, arr.get(1));
				arr.set(1, temp);
			}
		}
		else {
			ArrayList<DayBirth> half1 = new ArrayList<DayBirth>();
			ArrayList<DayBirth> half2 = new ArrayList<DayBirth>();
			for (int i = 0; i < arr.size(); i++) {
				if (i < arr.size()/2) {
					half1.add(arr.get(i));
				}
				else {
					half2.add(arr.get(i));
				}
			}
			mergeSortLab(half1);
			mergeSortLab(half2);
			
			mergeLab(half1, half2, arr);
		}
	}
	//precondition: A.size() + B.size() = C.size()
	private static void mergeLab(ArrayList<DayBirth> A, ArrayList<DayBirth> B, ArrayList<DayBirth> C){
		ArrayList<Integer> tempA = new ArrayList<Integer>();
		ArrayList<Integer> tempB = new ArrayList<Integer>();
		int min = Integer.MAX_VALUE;
		for (int c = 0; c < C.size(); c++) {
			if (A.size() == 0) {
				C.set(c, B.remove(0));
			}
			else if (B.size() == 0) {
				C.set(c, A.remove(0));
			}
			else {
				if (A.get(0).getBirths() <= B.get(0).getBirths()) {
					C.set(c, A.remove(0));
				}
				else {
					C.set(c, B.remove(0));
				}
			}
		}
	}
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
