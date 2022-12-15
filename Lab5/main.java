import pkg.*;
import java.util.*;
import java.time.*;
import java.lang.*;

class main {
	public static void main(String args[]) {
		/*
			Create an ArrayList of 100 Nodes
			Store random integers in each of them
			Print out all of the values
		*/
		ArrayList<Node> arr = new ArrayList<Node>();
		int[] temp = new int[100];
		BaseClass.randomize(temp);
		for (int i:temp) {
			arr.add(new Node(i));
		}
		for (Node n: arr) {
			System.out.print(n.getData() + " ");
		}
		System.out.println();
	}
}
