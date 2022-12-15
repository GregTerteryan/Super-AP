import pkg.*;
import java.util.*;
import java.time.*;
import java.lang.*;

class main {
	public static void main(String args[]) {
		int[] vals = new int[20];
		BaseClass.randomize(vals);
		SinglyLinkedList list = new SinglyLinkedList();
		for (int c = 0; c < vals.length; c++) {
			list.insert(0, vals[c]);
		}
		Random rand = new Random();
		for (int lmao = 0; lmao < 20; lmao++) {
			list.insert(rand.nextInt(20), -1);
		}
		System.out.println("--------------------------Normal List--------------------------");
		list.printList();
		for (int i = 0; i < 20; i++) {
			int j = 39 - i;
			list.swap(i, j);
		}
		System.out.println("--------------------------Swapped List--------------------------");
		list.printList();
	}
}
